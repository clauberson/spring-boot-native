package com.softwaremill.realworld.domain.user;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "user_follow")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {
    @EmbeddedId
    private FollowId id;

    @MapsId("fromId")
    @JoinColumn(name = "from_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User from;

    @MapsId("toId")
    @JoinColumn(name = "to_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User to;

    @CreatedDate
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Follow(User from, User to) {
        this.id = new FollowId(from.getId(), to.getId());
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Follow other
                && Objects.equals(this.id, other.id)
                && Objects.equals(this.from, other.from)
                && Objects.equals(this.to, other.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.from, this.to);
    }
}
