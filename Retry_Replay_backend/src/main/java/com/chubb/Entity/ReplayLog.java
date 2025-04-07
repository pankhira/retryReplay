package com.chubb.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "replay_log")
public class ReplayLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private String replayedBy;        // Admin/User who triggered it
    private String replayStatus;      // SUCCESS / FAILURE
    private String notes;             // Optional message or notes
    private LocalDateTime replayedAt;
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
	public String getReplayedBy() {
		return replayedBy;
	}
	public void setReplayedBy(String replayedBy) {
		this.replayedBy = replayedBy;
	}
	public String getReplayStatus() {
		return replayStatus;
	}
	public void setReplayStatus(String replayStatus) {
		this.replayStatus = replayStatus;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public LocalDateTime getReplayedAt() {
		return replayedAt;
	}
	public void setReplayedAt(LocalDateTime replayedAt) {
		this.replayedAt = replayedAt;
	}
}