package com.nemo.effective_java.item01.step02;

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerClient {
	public static void main(String[] args) {
		int bitLength = 4;
		int certainty = 100;
		Random random = new Random();
		BigInteger primeCandidate = BigInteger.probablePrime(bitLength, random);
		System.out.println("Generated BigInteger : " + primeCandidate);
		System.out.println("Is Probably prime? " + primeCandidate.isProbablePrime(certainty));
	}
}
