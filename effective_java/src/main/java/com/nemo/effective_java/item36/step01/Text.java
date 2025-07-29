package com.nemo.effective_java.item36.step01;

public class Text {
	public static final int STYLE_BOLD = 1 << 0; // 1
	public static final int STYLE_ITALIC = 1 << 1; // 2
	public static final int STYLE_UNDERLINE = 1 << 2; // 4
	public static final int STYLE_STRIKETHROUGH = 1 << 3; // 8

	// 매개변수 styles는 0개 이상의 STYLE_ 상수를 비트별 OR한 값이다.
	public void applyStyles(int styles) {
		if ((styles & STYLE_BOLD) != 0) {
			System.out.println("Applying bold style.");
		}
		if ((styles & STYLE_ITALIC) != 0) {
			System.out.println("Applying italic style.");
		}
		if ((styles & STYLE_UNDERLINE) != 0) {
			System.out.println("Applying underline style.");
		}
		if ((styles & STYLE_STRIKETHROUGH) != 0) {
			System.out.println("Applying strikethrough style.");
		}
	}
}
