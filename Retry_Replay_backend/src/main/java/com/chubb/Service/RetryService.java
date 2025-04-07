package com.chubb.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.chubb.Entity.RetryMetadata;

public interface RetryService {
    void saveRetry(RetryMetadata retryMetadata);
    void markSuccess(String transactionId);
    void markFailed(String transactionId);
    void scheduleNextRetry(RetryMetadata retryMetadata);
    List<RetryMetadata> getPendingRetries();
    boolean shouldRetryNow(RetryMetadata metadata);
    LocalDateTime calculateNextAttemptTime(RetryMetadata metadata);
}