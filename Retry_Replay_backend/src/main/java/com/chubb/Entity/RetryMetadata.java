package com.chubb.Entity;

import java.time.LocalDateTime;

import com.chubb.config.RetryStrategy;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "retry_metadata")
public class RetryMetadata {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId;   // Unique ID for the transaction
    private String payload;         // Request body or data to retry
    private int attemptCount;       // How many times retried
    private String status;          // PENDING, SUCCESS, FAILED
    
    @Enumerated(EnumType.STRING)
    private RetryStrategy strategy;        // FIXED, BACKOFF, JITTER, CIRCUIT_BREAKER
    private LocalDateTime nextRetryTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int maxAttempts=3;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public int getAttemptCount() {
		return attemptCount;
	}
	public void setAttemptCount(int attemptCount) {
		this.attemptCount = attemptCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	 
	public RetryStrategy getStrategy() {
		return strategy;
	}
	public void setStrategy(RetryStrategy strategy) {
		this.strategy = strategy;
	}
	public LocalDateTime getNextRetryTime() {
		return nextRetryTime;
	}
	public void setNextRetryTime(LocalDateTime nextRetryTime) {
		this.nextRetryTime = nextRetryTime;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public int getMaxAttempts() {
		return maxAttempts;
	}
	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}
}