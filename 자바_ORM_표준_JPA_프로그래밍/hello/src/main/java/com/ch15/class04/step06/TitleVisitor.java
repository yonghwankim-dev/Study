package com.ch15.class04.step06;

import lombok.Getter;

@Getter
public class TitleVisitor implements Visitor{

	private String title;

	@Override
	public void visit(Book book) {
		title = String.format("[제목:%s 저자:%s]", book.getName(), book.getAuthor());
	}

	@Override
	public void visit(Album album) {
		title = String.format("[제목:%s]", album.getName());
	}

	@Override
	public void visit(Movie movie) {
		title = String.format("[제목:%s]", movie.getName());
	}
}
