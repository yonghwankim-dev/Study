package com.ch15.class04.step06;

public interface Visitor {
	void visit(Book book);
	void visit(Album album);
	void visit(Movie movie);
}
