package com.myshop.board.domain;

import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;

@Entity
@Table(name = "article")
@SecondaryTable(
	name = "article_content",
	pkJoinColumns = @PrimaryKeyJoinColumn(name = "id")
)
public class Article {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Embedded
	@AttributeOverride(name = "content", column = @Column(table = "article_content", name = "content"))
	@AttributeOverride(name = "contentType", column = @Column(table = "article_content", name = "content_type"))
	private ArticleContent content;

	private String memberId;

	private boolean deleted;

	protected Article() {
	}

	public Article(String title, ArticleContent content, String memberId) {
		this.title = title;
		this.content = content;
		this.memberId = memberId;
		this.deleted = false;
	}

	public void markDeleted() {
		this.deleted = true;
	}

	public void changeTitle(String title) {
		this.title = title;
	}

	public void changeContent(String content) {
		this.content = new ArticleContent(content, this.content.getContentType());
	}

	public boolean isDeleted() {
		return deleted;
	}

	public Long getId() {
		return id;
	}

	public String getAuthorId() {
		return memberId;
	}

	public String getTitle() {
		return title;
	}

	public ArticleContent getContent() {
		return content;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Article article = (Article)object;
		return Objects.equals(id, article.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
