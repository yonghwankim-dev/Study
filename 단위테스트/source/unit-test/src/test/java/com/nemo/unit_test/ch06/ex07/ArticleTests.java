package com.nemo.unit_test.ch06.ex07;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ArticleTests {

	static class Article{

		private final List<Comment> comments = new ArrayList<>();

		public Comment addComment(String text, String author, LocalDateTime dateCreated) {
			Comment newComment = new Comment(text, author, dateCreated);
			comments.add(newComment); // 사이드 이펙트
			return newComment;
		}
	}

	static class Comment{
		private final String text;
		private final String author;
		private final LocalDateTime dateCreated;

		public Comment(String text, String author, LocalDateTime dateCreated) {
			this.text = text;
			this.author = author;
			this.dateCreated = dateCreated;
		}
	}

	/**
	 * addComment 메서드는 수학적인 함수가 아니다.
	 * <p>
	 * - 내부적으로 comments 리스트에 Comment 인스턴스를 추가하고 있다.
	 */
	@Test
	void adding_a_comment_to_an_article() {
		// given
		Article sut = new Article();
		String text = "Comment Text";
		String author = "John Doe";
		LocalDateTime dateCreated = LocalDate.of(2019,4,1).atStartOfDay();
		// when
		Comment comment = sut.addComment(text, author, dateCreated);
		// then
		Assertions.assertThat(comment).isNotNull();
	}
}
