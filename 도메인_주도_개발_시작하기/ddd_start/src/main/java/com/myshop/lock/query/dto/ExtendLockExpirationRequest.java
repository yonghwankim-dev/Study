package com.myshop.lock.query.dto;

public class ExtendLockExpirationRequest {
	private String lockId;

	public ExtendLockExpirationRequest() {
	}

	public ExtendLockExpirationRequest(String lockId) {
		this.lockId = lockId;
	}

	public String getLockId() {
		return lockId;
	}

	public void setLockId(String lockId) {
		this.lockId = lockId;
	}
}
