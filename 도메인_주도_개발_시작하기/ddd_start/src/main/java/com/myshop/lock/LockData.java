package com.myshop.lock;

public class LockData {
	private String type;
	private String id;
	private String lockId;
	private long expirationTime;

	public LockData(String type, String id, String lockId, long expirationTime) {
		this.type = type;
		this.id = id;
		this.lockId = lockId;
		this.expirationTime = expirationTime;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getLockId() {
		return lockId;
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public boolean isExpired() {
		return expirationTime < System.currentTimeMillis();
	}
}
