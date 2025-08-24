package com.myshop.member.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.member.application.ChangePasswordService;
import com.myshop.member.query.dto.ChangePasswordRequest;

@RestController
@RequestMapping("/member/changePassword")
public class MemberPasswordController {

	private final ChangePasswordService service;

	public MemberPasswordController(ChangePasswordService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<Void> submit(ChangePasswordRequest request) {
		service.changePassword(request);
		return ResponseEntity.ok().build();
	}
}
