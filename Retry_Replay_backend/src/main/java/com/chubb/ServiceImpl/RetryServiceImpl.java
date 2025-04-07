package com.chubb.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.Entity.RetryMetadata;
import com.chubb.Repository.RetryMetadataRepository;
import com.chubb.Service.RetryService;

@Service
public class RetryServiceImpl implements RetryService {

    @Autowired
    private RetryMetadataRepository retryRepo;

    @Override
    public void saveRetry(RetryMetadata retryMetadata) {
        retryMetadata.setCreatedAt(LocalDateTime.now());
        retryMetadata.setUpdatedAt(LocalDateTime.now());
        retryMetadata.setStatus("PENDING");
        retryRepo.save(retryMetadata);
    }

    @Override
    public void markSuccess(String transactionId) {
        retryRepo.findByTransactionId(transactionId).ifPresent(retry -> {
            retry.setStatus("SUCCESS");
            retry.setUpdatedAt(LocalDateTime.now());
            retryRepo.save(retry);
        });
    }

    @Override
    public void markFailed(String transactionId) {
        retryRepo.findByTransactionId(transactionId).ifPresent(retry -> {
            retry.setStatus("FAILED");
            retry.setUpdatedAt(LocalDateTime.now());
            retryRepo.save(retry);
        });
    }

    @Override
    public List<RetryMetadata> getPendingRetries() {
        return retryRepo.findByStatus("PENDING");
    }

    @Override
    public void scheduleNextRetry(RetryMetadata retryMetadata) {
        int attempt = retryMetadata.getAttemptCount() + 1;
        retryMetadata.setAttemptCount(attempt);

        // Fixed interval strategy (default)
        LocalDateTime next = LocalDateTime.now().plusSeconds(60); // retry after 1 min

        if (retryMetadata.getStrategy().name().equalsIgnoreCase("EXPONENTIAL_BACKOFF")) {
            next = LocalDateTime.now().plusSeconds((long) Math.pow(2, attempt) * 10); // backoff
        } else if ( (retryMetadata.getStrategy().name().equalsIgnoreCase("JITTER"))) {
            long jitter = (long) (Math.random() * 30); // add 0–30 sec randomly
            next = LocalDateTime.now().plusSeconds(60 + jitter);
        }

        retryMetadata.setNextRetryTime(next);
        retryMetadata.setUpdatedAt(LocalDateTime.now());

        retryRepo.save(retryMetadata);
    }
    
    @Override
    public boolean shouldRetryNow(RetryMetadata metadata) {
        return metadata.getNextRetryTime() == null || LocalDateTime.now().isAfter(metadata.getNextRetryTime());
    }

    @Override
    public LocalDateTime calculateNextAttemptTime(RetryMetadata retryMetadata) {
        int attempt = retryMetadata.getAttemptCount() + 1;

        if (retryMetadata.getStrategy().name().equalsIgnoreCase("EXPONENTIAL_BACKOFF")) {
            return LocalDateTime.now().plusSeconds((long) Math.pow(2, attempt) * 10);
        } else if ((retryMetadata.getStrategy().name().equalsIgnoreCase("JITTER"))) {
            long jitter = (long) (Math.random() * 30);
            return LocalDateTime.now().plusSeconds(60 + jitter);
        } else {
            return LocalDateTime.now().plusSeconds(60); // Fixed Interval by default
        }
    }
}