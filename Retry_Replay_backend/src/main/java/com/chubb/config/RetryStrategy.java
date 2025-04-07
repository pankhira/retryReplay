package com.chubb.config;

public enum RetryStrategy {

	FIXED,
	EXPONENTIAL,
	JITTER,
	CIRCUIT_BREAKER
}
