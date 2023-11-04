package com.softwaremill.realworld.domain.article;

import com.softwaremill.realworld.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("The Comment")
class CommentTest {
    @Test
    @DisplayName("returns true if the isAuthoredBy method matches the given User.")
    void isAuthoredBy_returnsTrue_whenAuthorMatches() {
        // given
        User author = User.builder()
                .id(UUID.randomUUID())
                .email("james@example.com")
                .username("james")
                .password("password")
                .build();
        Comment comment = Comment.builder()
                .id(1)
                .article(Article.builder()
                        .title("How to write unit tests")
                        .description("Unit testing is an essential part of software development.")
                        .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                        .author(author)
                        .build())
                .author(author)
                .content("This is a comment.")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // when
        boolean result = comment.isWritten(author);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("returns false if the isAuthoredBy method does not match the given User.")
    void isAuthoredBy_returnsFalse_whenAuthorDoesNotMatch() {
        // given
        User author = User.builder()
                .id(UUID.randomUUID())
                .email("james@example.com")
                .username("james")
                .password("password")
                .build();
        User otherUser = User.builder()
                .id(UUID.randomUUID())
                .email("adam@example.com")
                .username("adam")
                .password("password")
                .build();
        Comment comment = Comment.builder()
                .id(1)
                .article(Article.builder()
                        .title("How to write unit tests")
                        .description("Unit testing is an essential part of software development.")
                        .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                        .author(author)
                        .build())
                .author(author)
                .content("This is a comment.")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // when
        boolean result = comment.isWritten(otherUser);

        // then
        assertThat(result).isFalse();
    }
}
