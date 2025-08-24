package com.myshop.member.query.dto;

public record ChangePasswordRequest(String memberId, String oldPassword, String newPassword) {
}
