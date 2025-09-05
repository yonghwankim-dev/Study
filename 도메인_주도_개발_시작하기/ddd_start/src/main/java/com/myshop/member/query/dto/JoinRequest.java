package com.myshop.member.query.dto;

public class JoinRequest {
	private String name;
	private String address1;
	private String address2;
	private String zipCode;
	private String email;
	private String password;

	public JoinRequest() {
	}

	public JoinRequest(String name, String address1, String address2, String zipCode, String email, String password) {
		this.name = name;
		this.address1 = address1;
		this.address2 = address2;
		this.zipCode = zipCode;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getPassword() {
		return password;
	}
}
