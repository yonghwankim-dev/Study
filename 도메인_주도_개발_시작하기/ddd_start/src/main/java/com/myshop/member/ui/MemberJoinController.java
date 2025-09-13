package com.myshop.member.ui;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.member.EmptyPropertyException;
import com.myshop.member.application.JoinService;
import com.myshop.member.domain.MemberId;
import com.myshop.member.query.dto.JoinErrorResponse;
import com.myshop.member.query.dto.JoinRequest;

@RestController
public class MemberJoinController {

	private final JoinService joinService;

	public MemberJoinController(JoinService joinService) {
		this.joinService = joinService;
	}

	@PostMapping("/member/join")
	public ResponseEntity<?> join(@RequestBody JoinRequest joinRequest, Errors errors) {
		MemberId memberId;
		try {
			memberId = joinService.join(joinRequest);
		} catch (EmptyPropertyException e) {
			errors.rejectValue(e.getPropertyName(), "empty");
			List<ErrorResponse> errorResponses = errors.getFieldErrors().stream()
				.map(err -> new JoinErrorResponse(err.getField(), err.getDefaultMessage(), err.getCode()))
				.map(ErrorResponse.class::cast)
				.toList();
			return ResponseEntity.badRequest().body(errorResponses);
		}
		return ResponseEntity.ok(memberId);
	}
}
