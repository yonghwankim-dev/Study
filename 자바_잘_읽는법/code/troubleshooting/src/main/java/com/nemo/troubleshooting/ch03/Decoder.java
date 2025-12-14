package com.nemo.troubleshooting.ch03;

import java.util.ArrayList;
import java.util.List;

public class Decoder {
	public Integer decode(List<String> input) {
		try {
			int total = 0;
			for (String s : input) {
				var digits = new StringDigitExtractor(s).extractDigits();
				var sum = digits.stream().mapToInt(Integer::intValue).sum();
				total += sum;
			}
			return total;
		} catch (Exception e) {
			return -1;
		}
	}

	private static class StringDigitExtractor {
		private final String input;

		public StringDigitExtractor(String input) {
			this.input = input;
		}

		public List<Integer> extractDigits() {
			List<Integer> list = new ArrayList<>();
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
					list.add(Integer.parseInt(String.valueOf(input.charAt(i))));
				}
			}
			return list;
		}
	}
}
