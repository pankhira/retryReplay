package com.chubb.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.Entity.ReplayLog;
import com.chubb.Entity.RetryMetadata;
import com.chubb.Repository.ReplayLogRepository;
import com.chubb.Repository.RetryMetadataRepository;
import com.chubb.Service.ReplayService;
import com.chubb.config.RetryJob;

@Service
public class ReplayServiceImpl implements ReplayService {

    @Autowired
    private ReplayLogRepository replayLogRepository;

    @Autowired
    private RetryMetadataRepository retryMetadataRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(ReplayServiceImpl.class);

    @Override
    public String replayTransaction(String transactionId, String replayedBy, String notes) {
        logger.info("Starting replay for transactionId: {}", transactionId);

        Optional<RetryMetadata> optionalRetry = retryMetadataRepository.findByTransactionId(transactionId);

        ReplayLog log = new ReplayLog();
        log.setTransactionId(transactionId);
        log.setReplayedBy(replayedBy);
        log.setReplayedAt(LocalDateTime.now());
        log.setNotes(notes);

        if (optionalRetry.isPresent()) {
            RetryMetadata retry = optionalRetry.get();

            boolean replaySuccess = mockReplayHandler(retry.getPayload());

            log.setReplayStatus(replaySuccess ? "SUCCESS" : "FAILURE");

            if (replaySuccess) {
                retry.setStatus("SUCCESS");
                retry.setUpdatedAt(LocalDateTime.now());
                retryMetadataRepository.save(retry);
            }

            logger.info("Replay {} for transactionId: {}", replaySuccess ? "succeeded" : "failed", transactionId);

        } else {
            log.setReplayStatus("NOT_FOUND");
            logger.warn("Replay failed: transactionId {} not found", transactionId);
        }

        replayLogRepository.save(log);
        return log.getReplayStatus();
    }

    @Override
    public List<ReplayLog> getReplayLogs(String transactionId) {
        return replayLogRepository.findByTransactionId(transactionId);
    }

    // Mock method - replace with actual HTTP retry call logic
    private boolean mockReplayHandler(String payload) {
        // Imagine this is making an HTTP call and returning true if 200 OK
        return Math.random() > 0.2; // 80% chance of success
    }
}
