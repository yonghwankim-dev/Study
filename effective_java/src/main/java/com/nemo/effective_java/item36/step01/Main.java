package com.nemo.effective_java.item36.step01;

import static com.nemo.effective_java.item36.step01.Text.*;

public class Main {
	public static void main(String[] args) {
		Text text = new Text();

		// BOLD와 UNDERLINE 스타일을 적용
		int styles = STYLE_BOLD | STYLE_UNDERLINE;
		text.applyStyles(styles);

		// ITALIC과 STRIKETHROUGH 스타일을 적용
		int styles2 = STYLE_ITALIC | STYLE_STRIKETHROUGH;
		text.applyStyles(styles2);
	}
}
