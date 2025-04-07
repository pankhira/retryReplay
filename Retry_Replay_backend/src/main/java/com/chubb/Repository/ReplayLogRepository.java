package com.chubb.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chubb.Entity.ReplayLog;

@Repository
public interface ReplayLogRepository extends JpaRepository<ReplayLog, Long>{
     
	List<ReplayLog> findByTransactionId(String transactionId);
}
