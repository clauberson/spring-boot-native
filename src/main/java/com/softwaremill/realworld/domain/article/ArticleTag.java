package com.softwaremill.realworld.domain.article;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "article_tag")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleTag {
    @EmbeddedId
    private ArticleTagId id;

    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    @CreatedDate
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public ArticleTag(Article article, Tag tag) {
        this.id = new ArticleTagId(article.getId(), tag.getId());
        this.article = article;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ArticleTag other
                && Objects.equals(this.id, other.id)
                && Objects.equals(this.article, other.article)
                && Objects.equals(this.tag, other.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.article, this.tag);
    }
}
