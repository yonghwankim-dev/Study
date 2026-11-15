package com.nemo.unit_test.ch06.ex05;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
	}

	static class ArticleAssert extends AbstractAssert<ArticleAssert, Article>{

		protected ArticleAssert(Article actual) {
			super(actual, ArticleAssert.class);
		}

		public static ArticleAssert assertThat(Article actual){
			return new ArticleAssert(actual);
		}

		public ArticleAssert shouldContainNumberOfComments(int expectedSize){
			isNotNull();
			if(actual.comments.size() != expectedSize){
				failWithMessage("Expected number of comments to be <%s> but was <%s>",
					expectedSize, actual.comments.size());
			}
			return this;
		}

		public ArticleAssert withComment(String text, String author, LocalDateTime dateCreated){
			isNotNull();
			boolean exists = actual.comments.stream()
				.anyMatch(c->c.text.equals(text)
					&& c.author.equals(author)
					&& c.dateCreated.equals(dateCreated));
			if(!exists){
				failWithMessage("Expected article to contain comment with text <%s>, author <%s>, date <%s>, but found none",
					text, author, dateCreated);
			}
			return this;
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
		// 검증문에 헬퍼 메서드 사용
		ArticleAssert.assertThat(sut)
				.shouldContainNumberOfComments(1)
				.withComment(text, author, dateCreated);
	}
}
