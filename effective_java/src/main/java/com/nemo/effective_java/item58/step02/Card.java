package com.nemo.effective_java.item58.step02;

public class Card {
	private final Suit suit;
	private final Rank rank;

	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	@Override
	public String toString() {
		return String.format("%s-%s", suit, rank);
	}
}
