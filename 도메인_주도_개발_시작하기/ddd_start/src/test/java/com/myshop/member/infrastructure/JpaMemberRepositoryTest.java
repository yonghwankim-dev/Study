package com.myshop.member.infrastructure;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.transaction.support.TransactionTemplate;

import com.myshop.FixedDomainFactory;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaMemberRepositoryTest {

	@Autowired
	private MemberRepository repository;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@BeforeEach
	void setUp() {
		Member member = FixedDomainFactory.createMember("member-1");
		repository.save(member);
	}

	@DisplayName("두 스레드가 한 회원 애그리거트에 동시에 접근할때 Lock이 걸려 순차적으로 접근한다.")
	@Test
	void findByIdForUpdate() throws InterruptedException {
		CountDownLatch thread1CountDownLatch = new CountDownLatch(1);

		AtomicLong thread1EndTime = new AtomicLong();
		AtomicLong thread2EndTime = new AtomicLong();

		Thread thread1 = new Thread(() -> {
			transactionTemplate.executeWithoutResult(status -> {
				Member member = repository.findByIdForUpdate(new MemberId("member-1")).orElseThrow();
				System.out.println("Thread 1 acquired lock on member: " + member.getId().getId());
				thread1CountDownLatch.countDown(); // thread1이 락 잡음 알림
				try {
					// 트랜잭션을 오래 유지하여 thread2가 기다리도록 만듦
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				thread1EndTime.set(System.currentTimeMillis());
			});
		});

		Thread thread2 = new Thread(() -> {
			try {
				thread1CountDownLatch.await(); // thread1이 락 잡을 때까지 대기
				transactionTemplate.executeWithoutResult(status -> {
					Member member = repository.findByIdForUpdate(new MemberId("member-1")).orElseThrow();
					System.out.println("Thread 2 acquired lock on member: " + member.getId().getId());
					thread2EndTime.set(System.currentTimeMillis());
				});
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		// 검증: thread1이 끝난 후에 thread2가 끝났는지 확인
		Assertions.assertThat(thread2EndTime.get())
			.isGreaterThan(thread1EndTime.get());
	}

	@DisplayName("회원 애그리거트 점유를 대기하다가 타임아웃 발생함")
	@Test
	void shouldThrowException_whenLockWaitTimeoutExceeded() throws InterruptedException {
		CountDownLatch thread1CountDownLatch = new CountDownLatch(1);

		Thread thread1 = new Thread(() -> {
			transactionTemplate.executeWithoutResult(status -> {
				Member member = repository.findByIdForUpdate(new MemberId("member-1")).orElseThrow();
				System.out.println("Thread 1 acquired lock on member: " + member.getId().getId());
				thread1CountDownLatch.countDown(); // thread1이 락 잡음 알림
				// 트랜잭션을 오래 유지하여 thread2가 기다리도록 만듦
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});
		});

		Thread thread2 = new Thread(() -> {
			try {
				thread1CountDownLatch.await(); // thread1이 락 잡을 때까지 대기
				transactionTemplate.executeWithoutResult(status -> {
					Throwable throwable = Assertions.catchThrowable(() -> {
						Member member = repository.findByIdForUpdate(new MemberId("member-1")).orElseThrow();
						System.out.println("Thread 2 acquired lock on member: " + member.getId().getId());
					});
					Assertions.assertThat(throwable)
						.isInstanceOf(PessimisticLockingFailureException.class);
				});
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();
	}
}
