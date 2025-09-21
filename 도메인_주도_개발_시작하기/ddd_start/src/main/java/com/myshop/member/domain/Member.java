package com.myshop.member.domain;

import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.myshop.common.jpa.EmailSetConverter;
import com.myshop.common.model.EmailSet;
import com.myshop.member.PasswordNotMatchException;
import com.myshop.order.domain.model.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

	@EmbeddedId
	private MemberId id;

	@Column(name = "name")
	private String name;

	@Embedded
	private Address address;

	@Embedded
	private Password password;

	@Column(name = "emails")
	@Convert(converter = EmailSetConverter.class)
	private EmailSet emails;

	@Embedded
	private MemberGrade grade;

	private boolean blocked;

	protected Member() {
		// Default constructor for JPA
	}

	public Member(MemberId id, String name, Address address, Password password, MemberGrade grade) {
		this.id = id;
		this.name = name;
		this.address = address;
		setPassword(password);
		this.grade = grade;
		this.blocked = false;
	}

	public void addEmail(String email) {
		if (emails == null) {
			emails = new EmailSet(new HashSet<>());
		}
		emails.add(email);
	}

	public void changeAddress(Address address) {
		if (address == null) {
			throw new IllegalArgumentException("Address cannot be null");
		}
		this.address = address;
	}

	public void changePassword(String currentPassword, String newPassword, PasswordEncoder passwordEncoder) {
		if (!password.match(currentPassword, passwordEncoder)) {
			throw new PasswordNotMatchException();
		}
		setPassword(new Password(passwordEncoder.encode(newPassword)));
	}

	private void setPassword(Password newPassword) {
		this.password = newPassword;
	}

	public void block() {
		this.blocked = true;
	}

	public MemberId getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public Password getPassword() {
		return password;
	}

	public MemberGrade getGrade() {
		return grade;
	}
}
