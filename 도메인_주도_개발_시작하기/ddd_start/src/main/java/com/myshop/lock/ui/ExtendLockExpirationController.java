package com.myshop.lock.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.lock.LockManager;
import com.myshop.lock.domain.LockId;
import com.myshop.lock.query.dto.ExtendLockExpirationRequest;

@RestController
public class ExtendLockExpirationController {

	private final LockManager lockManager;

	public ExtendLockExpirationController(LockManager lockManager) {
		this.lockManager = lockManager;
	}

	@PostMapping("/locks/extend")
	public ResponseEntity<Void> extendLockExpiration(@RequestBody ExtendLockExpirationRequest lockId) {
		System.out.println("Extend lock expiration for lockId: " + lockId.getLockId());
		lockManager.extendLockExpiration(new LockId(lockId.getLockId()), 60000); // 1분 연장
		return ResponseEntity.ok().build();
	}
}
