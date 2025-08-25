package com.myshop.member.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, MemberId> {

	@Query("SELECT m FROM Member m WHERE m.id.id IN :blockingIds")
	List<Member> findByIdIn(@Param("blockingIds") String[] blockingIds);
}
