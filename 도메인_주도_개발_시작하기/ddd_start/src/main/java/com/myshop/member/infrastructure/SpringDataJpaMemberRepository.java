package com.myshop.member.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, MemberId> {

	@Query("SELECT m FROM Member m WHERE m.id.id IN :blockingIds")
	List<Member> findByIdIn(@Param("blockingIds") String[] blockingIds);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints(@QueryHint(name = "javax.persistence.lock.timeout", value = "2000"))
	@Query("SELECT m FROM Member m WHERE m.id = :id")
	Optional<Member> findByIdForUpdate(@Param("id") MemberId id);

	@Query("SELECT m FROM Member m WHERE m.emails = :email")
	Optional<Member> findByEmail(String email);

	@Query("SELECT COUNT(m) FROM Member m WHERE m.id = :memberId")
	int countById(@Param("memberId") MemberId memberId);
}
