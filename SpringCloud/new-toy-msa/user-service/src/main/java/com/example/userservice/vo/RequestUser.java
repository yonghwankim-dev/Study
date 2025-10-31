package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUser {
	@NotNull(message = "Email cannot be null")
	@Size(min = 2, message = "Email length must be at least 2 characters")
	@Email
	private String email;
	@NotNull(message = "Password cannot be null")
	@Size(min = 8, message = "Password length must be at least 8 characters")
	private String pwd;
	@NotNull(message = "Name cannot be null")
	@Size(min = 2, message = "Name length must be at least 2 characters")
	private String name;
}
