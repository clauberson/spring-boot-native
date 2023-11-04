package com.softwaremill.realworld.application.article.controller;

import static java.util.stream.Collectors.toSet;

import com.softwaremill.realworld.domain.article.Tag;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("article")
public record CreateArticleRequest(String title, String description, String body, List<String> tagList) {
    public Set<Tag> tags() {
        return tagList.stream().map(Tag::new).collect(toSet());
    }
}
