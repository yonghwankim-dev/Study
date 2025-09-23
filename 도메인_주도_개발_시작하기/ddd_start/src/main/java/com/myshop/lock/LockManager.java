package com.myshop.lock;

import com.myshop.lock.domain.LockId;
import com.myshop.lock.error.LockException;

public interface LockManager {
	LockId tryLock(String type, String id) throws LockException;

	void checkLock(LockId lockId) throws LockException;

	void releaseLock(LockId lockId) throws LockException;

	void extendLockExpiration(LockId lockId, long inc) throws LockException;
}
