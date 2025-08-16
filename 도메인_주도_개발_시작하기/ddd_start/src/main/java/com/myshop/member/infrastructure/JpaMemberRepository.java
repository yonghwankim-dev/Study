package com.myshop.member.infrastructure;

import javax.swing.*;

import org.springframework.stereotype.Repository;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;

@Repository
public class JpaMemberRepository implements MemberRepository {

	private final SpringDataJpaMemberRepository repository;

	public JpaMemberRepository(SpringDataJpaMemberRepository repository) {
		this.repository = repository;
	}

	@Override
	public Member findById(MemberId id) {
		return repository.findById(id)
			.orElseThrow();
	}

	@Override
	public void save(Member member) {
		repository.save(member);
	}

	@Override
	public void delete(Member member) {
		repository.delete(member);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
}
