package com.myshop.member.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, MemberId> {

}
