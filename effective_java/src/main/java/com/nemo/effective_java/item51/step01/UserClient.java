package com.nemo.effective_java.item51.step01;

public class UserClient {
	public static void main(String[] args) {
		User user = new User.UserBuilder()
			.withFirstName("John")
			.withLastName("Doe")
			.withAge(30)
			.withEmail("john.doe@example.com")
			.withPhoneNumber("123-456-7890")
			.build();
		System.out.println(user);
	}
}
