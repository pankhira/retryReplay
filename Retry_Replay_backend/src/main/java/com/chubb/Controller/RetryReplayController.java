package com.chubb.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.Entity.ReplayLog;
import com.chubb.Entity.RetryMetadata;
import com.chubb.Service.ReplayService;
import com.chubb.Service.RetryService;
import com.chubb.ServiceImpl.EmailService;

@RestController
@RequestMapping("/api")
public class RetryReplayController {

    @Autowired
    private RetryService retryService;

    @Autowired
    private ReplayService replayService;
    
    @Autowired
    private EmailService emailService;
    
    private static final Logger logger = LoggerFactory.getLogger(RetryReplayController.class);

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        logger.info("Test endpoint hit");
        emailService.sendEmail("abhinavnagpure4@gmail.com", "djava1519@gmail.com", "This is a test email from Retry & Replay.");
        return ResponseEntity.ok("Email Sent Successfully");
    }

    
    // Replay manually
    @PostMapping("/replay/{transactionId}")
    public ResponseEntity<String> replay(@PathVariable String transactionId,
                                         @RequestParam String replayedBy,
                                         @RequestParam(required = false) String notes) {
        String status = replayService.replayTransaction(transactionId, replayedBy, notes);
        return ResponseEntity.ok("Replay Status: " + status);
    }

    // View all pending retry metadata
    @GetMapping("/retries")
    public ResponseEntity<List<RetryMetadata>> getRetries() {
        return ResponseEntity.ok(retryService.getPendingRetries());
    }

    // View replay logs for a transaction
    @GetMapping("/replay/logs/{transactionId}")
    public ResponseEntity<List<ReplayLog>> getReplayLogs(@PathVariable String transactionId) {
        return ResponseEntity.ok(replayService.getReplayLogs(transactionId));
    }
}
