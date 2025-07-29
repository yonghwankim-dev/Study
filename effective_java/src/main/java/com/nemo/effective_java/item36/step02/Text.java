package com.nemo.effective_java.item36.step02;

import java.util.Set;

public class Text {

	public enum Style {BOLD, ITALIC, UNDERLINE, STRIKETHROUGH;}

	public void applyStyles(Set<Style> styles) {
		for (Style style : styles) {
			System.out.println("apply " + style + " style");
		}
	}
}
