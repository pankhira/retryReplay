package com.chubb.config;

import java.time.LocalDateTime;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chubb.Entity.RetryMetadata;
import com.chubb.Service.RetryService;
import com.chubb.ServiceImpl.EmailService;

@Component
public class RetryJob implements Job {

    @Autowired
    private RetryService retryService;
    
    @Autowired
    private EmailService emailService;
    
    private static final Logger logger = LoggerFactory.getLogger(RetryJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Retry job started at {}", LocalDateTime.now());

        List<RetryMetadata> retries = retryService.getPendingRetries();

        for (RetryMetadata retry : retries) {
            logger.info("Evaluating transaction ID: {}", retry.getTransactionId());

            if (retry.getAttemptCount() >= retry.getMaxAttempts()) {
                logger.warn("Max attempts reached for transaction ID: {}. Marking as FAILED.", retry.getTransactionId());
                retryService.markFailed(retry.getTransactionId());
                emailService.sendEmail("priyanka812025@gmail.com", 
                        "Retry Failed", 
                        "Transaction " + retry.getTransactionId() + " failed after max attempts.");
                continue;
            }

            if (retry.getNextRetryTime() != null && retry.getNextRetryTime().isAfter(LocalDateTime.now())) {
                logger.info("Not retrying transaction ID: {} yet. Next retry time: {}", retry.getTransactionId(), retry.getNextRetryTime());
                continue;
            }

            logger.info("Attempting retry for transaction ID: {}", retry.getTransactionId());
            boolean retrySuccess = mockRetryHandler(retry.getPayload());

            if (retrySuccess) {
                logger.info("Retry successful for transaction ID: {}", retry.getTransactionId());
                retryService.markSuccess(retry.getTransactionId());
                emailService.sendEmail("priyanka812025@gmail.com", 
                        "Retry Success", 
                        "Transaction " + retry.getTransactionId() + " retried successfully.");
                
            } else {
                logger.warn("Retry failed for transaction ID: {}. Scheduling next retry.", retry.getTransactionId());
                retryService.scheduleNextRetry(retry);
            }
        }

        logger.info("Retry job completed at {}", LocalDateTime.now());
    }
    // Mock retry — replace with real call to external service
    private boolean mockRetryHandler(String payload) {
        return Math.random() > 0.3; // 70% chance of success
    }
}
