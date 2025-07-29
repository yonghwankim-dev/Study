package com.nemo.effective_java.item59.step02;

public class Client {
	public static void main(String[] args) {
		// int 타입은 32비트로 표현되며, 값의 범위는 -2^31(-2147483648) ~ 2^31 - 1(2147483647)입니다.
		// Integer.MIN_VALUE(-2147483648)의 양수값인 2147483648은 양수의 최댓값(2147483647)을 초과하여
		// 처리할 수 없습니다. 오버플로우 되어서 다시 음수쪽으로 래핑됩니다.
		System.out.println(Math.abs(Integer.MIN_VALUE)); // -2147483648

		// 예를 들어 Integer.MIN_VAULE(-2147483648)의 이진수는 10000000 00000000 00000000 00000000이다.
		// 양수로 변환하기 위해서 2의 보수 변환해야 하는데 이 경우에 01111111 11111111 11111111 11111111인 상태에서 + 1합니다.
		// +1 했을때 오버플로우가 발생하므로 음수로 래핑됩니다.
	}
}
