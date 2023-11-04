package com.softwaremill.realworld.domain.user;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;

import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowId implements Serializable {
    private UUID fromId;
    private UUID toId;
}
