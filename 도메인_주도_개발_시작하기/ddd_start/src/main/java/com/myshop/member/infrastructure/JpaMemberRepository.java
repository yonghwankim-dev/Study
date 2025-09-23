package com.myshop.member.infrastructure;

import java.util.List;
import java.util.Optional;

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
	public List<Member> findByIdIn(String[] blockingIds) {
		return repository.findByIdIn(blockingIds);
	}

	@Override
	public Optional<Member> findByIdForUpdate(MemberId id) {
		return repository.findByIdForUpdate(id);
	}

	@Override
	public Optional<Member> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public int countsById(MemberId memberId) {
		return repository.countById(memberId);
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
