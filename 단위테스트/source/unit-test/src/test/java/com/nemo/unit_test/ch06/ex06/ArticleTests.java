package com.nemo.unit_test.ch06.ex06;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;

class ArticleTests {

	static class Article{

		private final List<Comment> comments = new ArrayList<>();

		public void addComment(String text, String author, LocalDateTime dateCreated) {
			comments.add(new Comment(text, author, dateCreated));
		}

		public ListAssert<Comment> shouldContainNumberOfComments(int size) {
			return Assertions.assertThat(comments).hasSize(size);
		}

		public void withComment(String text, String author, LocalDateTime dateCreated) {
			Assertions.assertThat(this.comments.get(0).text).isEqualTo(text);
			Assertions.assertThat(this.comments.get(0).author).isEqualTo(author);
			Assertions.assertThat(this.comments.get(0).dateCreated).isEqualTo(dateCreated);
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

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (!(o instanceof Comment comment))
				return false;
			return Objects.equals(text, comment.text) && Objects.equals(author, comment.author)
				&& Objects.equals(dateCreated, comment.dateCreated);
		}

		@Override
		public int hashCode() {
			return Objects.hash(text, author, dateCreated);
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
		// 값 객체로 변환해서 동등성(Equality) 검증
		Comment expected = new Comment(text, author, dateCreated);
		Assertions.assertThat(sut.comments).containsExactly(expected);
	}
}
