package com.softwaremill.realworld.application.article.controller;

import com.softwaremill.realworld.domain.article.CommentVO;

import java.util.List;

public record MultipleCommentsResponse(CommentVO[] comments) {
    public MultipleCommentsResponse(List<CommentVO> comments) {
        this(comments.toArray(CommentVO[]::new));
    }
}
