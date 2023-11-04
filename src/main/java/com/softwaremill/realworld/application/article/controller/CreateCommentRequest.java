package com.softwaremill.realworld.application.article.controller;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("comment")
public record CreateCommentRequest(String body) {}
