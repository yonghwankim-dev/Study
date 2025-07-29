package com.nemo.effective_java.item20.step01;

public class SingerSongwriterImpl implements SingerSongwriter {
	@Override
	public String sing(String song) {
		return "sing the " + song;
	}

	@Override
	public String strum() {
		return "strum";
	}

	@Override
	public void actSensitive() {
		System.out.println("call actSensitive");
	}

	@Override
	public String compose(int chartPosition) {
		return "compose " + chartPosition;
	}

	public static void main(String[] args) {
		SingerSongwriter singerSongwriter = new SingerSongwriterImpl();
		System.out.println(singerSongwriter.sing("hello"));
		System.out.println(singerSongwriter.strum());
		singerSongwriter.actSensitive();
		System.out.println(singerSongwriter.compose(48));
	}
}
