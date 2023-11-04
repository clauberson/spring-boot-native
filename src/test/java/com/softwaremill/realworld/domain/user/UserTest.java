package com.softwaremill.realworld.domain.user;

import com.softwaremill.realworld.domain.article.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("The User")
class UserTest {
    @Test
    @DisplayName("users can favorite articles.")
    void favorite() {
        // given
        User james = User.builder()
                .id(UUID.randomUUID())
                .email("james@example.com")
                .username("james")
                .password("password")
                .build();
        Article article = Article.builder()
                .id(1)
                .author(james)
                .title("Article 1")
                .description("This is article 1")
                .content("This is the content of article 1.")
                .build();

        // when
        james.favorite(article);

        // then
        assertThat(james.isAlreadyFavorite(article)).isTrue();
    }

    @Test
    @DisplayName("users can unfavorite articles.")
    void unfavorite() {
        // given
        User james = User.builder()
                .id(UUID.randomUUID())
                .email("james@example.com")
                .username("james")
                .password("password")
                .build();
        Article article = Article.builder()
                .id(1)
                .author(james)
                .title("Article 1")
                .description("This is article 1")
                .content("This is the content of article 1.")
                .build();

        james.favorite(article);

        // when
        james.unfavorite(article);

        // then
        assertThat(james.isAlreadyFavorite(article)).isFalse();
        assertThat(article.numberOfLikes()).isZero();
    }
}
