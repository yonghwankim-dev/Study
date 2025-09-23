package com.myshop.lock;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.lock.domain.LockId;
import com.myshop.lock.error.AlreadyLockedException;
import com.myshop.lock.error.LockException;
import com.myshop.lock.error.LockingFailException;
import com.myshop.lock.error.NoLockException;

@Component
public class SpringLockManager implements LockManager {

	private final int lockTimeout;
	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<LockData> lockDataRowMapper = (rs, rowNum) -> new LockData(
		rs.getString("type"),
		rs.getString("id"),
		rs.getString("lockid"),
		rs.getTimestamp("expiration_time").getTime()
	);

	public SpringLockManager(JdbcTemplate jdbcTemplate) {
		this.lockTimeout = 5 * 60 * 1000; // 5 minutes
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public LockId tryLock(String type, String id) throws LockException {
		checkAlreadyLocked(type, id);
		LockId lockId = new LockId(UUID.randomUUID().toString());
		locking(type, id, lockId);
		return lockId;
	}

	private void checkAlreadyLocked(String type, String id) {
		List<LockData> locks = jdbcTemplate.query(
			"SELECT * FROM locks WHERE type = ? AND id = ?",
			lockDataRowMapper,
			type, id
		);
		Optional<LockData> lockData = handleExpiration(locks);
		if (lockData.isPresent()) {
			throw new AlreadyLockedException();
		}
	}

	private Optional<LockData> handleExpiration(List<LockData> locks) {
		if (locks.isEmpty()) {
			return Optional.empty();
		}
		LockData lockData = locks.get(0);
		if (lockData.isExpired()) {
			jdbcTemplate.update(
				"DELETE FROM locks WHERE type = ? AND id = ?",
				lockData.getType(), lockData.getId()
			);
			return Optional.empty();
		}
		return Optional.of(lockData);
	}

	private void locking(String type, String id, LockId lockId) {
		try {
			int updateCount = jdbcTemplate.update(
				"INSERT INTO locks(type, id, lockid, expiration_time) VALUES(?, ?, ?, ?)",
				type, id, lockId.getValue(), new Timestamp(getExpirationTime())
			);
			if (updateCount == 0) {
				throw new LockingFailException();
			}
		} catch (DuplicateKeyException exception) {
			throw new LockingFailException();
		}
	}

	private long getExpirationTime() {
		return System.currentTimeMillis() + lockTimeout;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void checkLock(LockId lockId) throws LockException {
		Optional<LockData> lockData = getLockData(lockId);
		if (lockData.isEmpty()) {
			throw new NoLockException();
		}
	}

	private Optional<LockData> getLockData(LockId lockId) {
		List<LockData> locks = jdbcTemplate.query(
			"SELECT * FROM locks WHERE lockid = ?",
			lockDataRowMapper,
			lockId.getValue()
		);
		return handleExpiration(locks);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void releaseLock(LockId lockId) throws LockException {
		jdbcTemplate.update(
			"DELETE FROM locks WHERE lockid = ?",
			lockId.getValue()
		);
	}

	@Override
	public void extendLockExpiration(LockId lockId, long inc) throws LockException {
		Optional<LockData> lockDataOpt = getLockData(lockId);
		LockData lockData = lockDataOpt.orElseThrow(NoLockException::new);
		jdbcTemplate.update(
			"UPDATE locks SET expiration_time = ? WHERE lockid = ? AND id = ?",
			new Timestamp(lockData.getExpirationTime() + inc),
			lockId.getValue(),
			lockData.getId()
		);
	}
}
