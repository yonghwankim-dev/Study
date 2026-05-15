package com.example.userservice.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserDto {
	private String email;
	private String pwd;
	private String name;
	private String userId;
	private Date createAt;

	private String encryptedPwd;

	private List<ResponseOrder> orders;
}
