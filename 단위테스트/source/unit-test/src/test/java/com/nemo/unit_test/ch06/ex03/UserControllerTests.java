package com.nemo.unit_test.ch06.ex03;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

class UserControllerTests {

	interface EmailGateway{

		void sendGreetingsEmail(String email);
	}

	static class UserController{
		private final EmailGateway emailGateway;

		public UserController(EmailGateway emailGateway) {
			this.emailGateway = emailGateway;
		}

		public void greetUser(String email) {
			this.emailGateway.sendGreetingsEmail(email);
		}
	}

	/**
	 * 통신 기반 테스트
	 * <pre>
	 *     단위 테스트의 고전파는 통신 기반 스타일보다 상태 기반 스타일을 선호함
	 *     런던파는 반대로 상태기반 스타일보다 통신 기반 스타일을 선호함
	 * </pre>
	 */
	@Test
	void sending_a_greetings_email(){
		EmailGateway emailGatewayMock = BDDMockito.mock(EmailGateway.class);
		UserController sut = new UserController(emailGatewayMock);

		sut.greetUser("user@email.com");

		BDDMockito.verify(emailGatewayMock, Mockito.times(1))
			.sendGreetingsEmail("user@email.com");
	}
}
