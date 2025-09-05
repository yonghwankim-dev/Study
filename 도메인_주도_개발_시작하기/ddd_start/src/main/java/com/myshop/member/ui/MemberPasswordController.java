package com.myshop.member.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.member.NoMemberException;
import com.myshop.member.application.ChangePasswordService;
import com.myshop.member.query.dto.ChangePasswordRequest;
import com.myshop.member.query.dto.MemberAuthentication;

@RestController
@RequestMapping("/member/changePassword")
public class MemberPasswordController {

	private final ChangePasswordService service;

	public MemberPasswordController(ChangePasswordService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> submit(@RequestBody ChangePasswordRequest request) {
		setMemberId(request);
		try {
			service.changePassword(request);
		} catch (NoMemberException e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().build();
	}

	private void setMemberId(ChangePasswordRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuthentication = (MemberAuthentication)authentication.getPrincipal();
		request.setMemberId(memberAuthentication.getMemberId());
	}
}
