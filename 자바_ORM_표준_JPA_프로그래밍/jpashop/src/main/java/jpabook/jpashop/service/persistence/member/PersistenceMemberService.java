package jpabook.jpashop.service.persistence.member;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.repository.persistence.member.PersistenceMemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PersistenceMemberService {

	private final PersistenceMemberRepository persistenceMemberRepository;

	public Long join(Member member) {
		validateDuplicateMember(member);
		persistenceMemberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = persistenceMemberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다");
		}
	}

	public List<Member> findMembers() {
		return persistenceMemberRepository.findAll();
	}

	public Member findOne(Long memberId) {
		return persistenceMemberRepository.fineOne(memberId);
	}
}

