package com.nemo.effective_java.item58.step04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CardClient {
	public static void main(String[] args) {
		List<Suit> suits = Arrays.asList(
			Suit.values());
		List<Rank> ranks = Arrays.asList(
			Rank.values());

		List<Card> deck = new ArrayList<>();
		for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
			Suit suit = i.next();
			for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); ) {
				Rank rank = j.next();
				deck.add(new Card(suit, rank));
			}
		}
		deck.forEach(System.out::println);
	}
}
