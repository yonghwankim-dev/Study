package com.nemo.effective_java.item51.step01;

public class User {
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String phoneNumber;

	private User(UserBuilder builder) {
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.age = builder.age;
		this.email = builder.email;
		this.phoneNumber = builder.phoneNumber;
	}

	public static class UserBuilder {
		private String firstName;
		private String lastName;
		private int age;
		private String email;
		private String phoneNumber;

		public UserBuilder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public UserBuilder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public UserBuilder withAge(int age) {
			this.age = age;
			return this;
		}

		public UserBuilder withEmail(String email) {
			this.email = email;
			return this;
		}

		public UserBuilder withPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}
}
