package com.nemo.effective_java.item01.step01;

import java.math.BigInteger;
import java.util.Random;

public class BigIntegerClient {
	public static void main(String[] args) {
		int bitLength = 4;
		int certainty = 100;
		Random random = new Random();
		BigInteger primeCandidate = new BigInteger(bitLength, certainty, random);
		System.out.println("Generated BigInteger : " + primeCandidate);
		System.out.println("Is Probably prime? " + primeCandidate.isProbablePrime(certainty));
	}
}
