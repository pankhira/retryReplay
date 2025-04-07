package com.chubb.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chubb.Entity.RetryMetadata;
import java.util.List;

@Repository
public interface RetryMetadataRepository extends JpaRepository<RetryMetadata, Long>{

	List<RetryMetadata> findByStatus(String status);
	Optional<RetryMetadata> findByTransactionId(String transactionId);
}
