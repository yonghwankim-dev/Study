package com.nemo.effective_java.item36.step02;

import java.util.EnumSet;

public class Main {
	public static void main(String[] args) {
		Text text = new Text();
		text.applyStyles(EnumSet.of(Text.Style.BOLD, Text.Style.UNDERLINE));
	}
}
