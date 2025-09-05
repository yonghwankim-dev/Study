package com.myshop.member.query.dto;

public class ChangePasswordRequest {
	private String memberId;
	private String oldPassword;
	private String newPassword;

	public ChangePasswordRequest() {
	}

	public ChangePasswordRequest(String memberId, String oldPassword, String newPassword) {
		this.memberId = memberId;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}
}
