package com.softwaremill.realworld.domain.article;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleTagId implements Serializable {
    private Integer articleId;
    private Integer tagId;
}
