package jpabook.jpashop.controller.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.controller.member.request.MemberCreateRequest;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.service.persistence.member.PersistenceMemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberRestController {
	private final PersistenceMemberService persistenceMemberService;

	@PostMapping
	public Member createMember(@RequestBody MemberCreateRequest request) {
		Long memberId = persistenceMemberService.join(request.toEntity());
		return persistenceMemberService.findOne(memberId);
	}
}
