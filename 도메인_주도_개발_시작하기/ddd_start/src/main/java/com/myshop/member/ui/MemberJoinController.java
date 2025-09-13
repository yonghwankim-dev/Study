package com.myshop.member.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.member.application.JoinService;
import com.myshop.member.domain.MemberId;
import com.myshop.member.query.dto.JoinRequest;

@RestController
public class MemberJoinController {

	private final JoinService joinService;

	public MemberJoinController(JoinService joinService) {
		this.joinService = joinService;
	}

	@PostMapping("/member/join")
	public ResponseEntity<MemberId> join(@RequestBody JoinRequest joinRequest) {
		MemberId memberId = joinService.join(joinRequest);
		return ResponseEntity.ok(memberId);
	}
}
