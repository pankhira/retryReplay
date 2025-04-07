package com.chubb.Service;

import java.util.List;

import com.chubb.Entity.ReplayLog;

public interface ReplayService {
    String replayTransaction(String transactionId, String replayedBy, String notes);
    List<ReplayLog> getReplayLogs(String transactionId);
}