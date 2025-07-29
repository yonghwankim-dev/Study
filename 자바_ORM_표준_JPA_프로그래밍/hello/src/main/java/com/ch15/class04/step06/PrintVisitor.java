package com.ch15.class04.step06;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintVisitor implements Visitor{
	@Override
	public void visit(Book book) {
		// 넘어오는 book은 Proxy가 아닌 원본 엔티티이다
		log.info("book.class = {}", book.getClass());
		log.info("[PrintVisitor][제목:{} 저자:{}]", book.getName(), book.getAuthor());
	}

	@Override
	public void visit(Album album) {
		log.info("album.class = {}", album.getClass());
		log.info("[PrintVisitor][제목:{}]", album.getName());
	}

	@Override
	public void visit(Movie movie) {
		log.info("movie.class = {}", movie.getClass());
		log.info("[PrintVisitor][제목:{}]", movie.getName());
	}
}
