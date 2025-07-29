package com.nemo.effective_java.item37.step03;

public class PhaseClient {
	public static void main(String[] args) {
		System.out.println(Phase.Transition.from(Phase.SOLID, Phase.LIQUID)); // MELT
	}
}
