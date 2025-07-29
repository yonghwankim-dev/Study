package com.nemo.effective_java.item35.step01;

public enum Ensemble {
	SONG, SOLO, DUET, TRIO, QUARTET, QUINTET,
	SEXTET, SEPTET, OCTET, NONET, DECTET;

	public int numberOfMusicians() {
		return ordinal() + 1;
	}
}
