package com.nemo.unit_test.ch06.ex04;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ArticleTests {

	static class Article{

		private final List<Comment> comments = new ArrayList<>();

		public void addComment(String text, String author, LocalDateTime dateCreated) {
			comments.add(new Comment(text, author, dateCreated));
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

	@Test
	void adding_a_comment_to_an_article() {
		// given
		Article sut = new Article();
		String text = "Comment Text";
		String author = "John Doe";
		LocalDateTime dateCreated = LocalDate.of(2019,4,1).atStartOfDay();
		// when
		sut.addComment(text, author, dateCreated);
		// then
		Assertions.assertThat(sut.comments).hasSize(1);
		Assertions.assertThat(sut.comments.get(0).text).isEqualTo(text);
		Assertions.assertThat(sut.comments.get(0).author).isEqualTo(author);
		Assertions.assertThat(sut.comments.get(0).dateCreated).isEqualTo(dateCreated);
	}
}
