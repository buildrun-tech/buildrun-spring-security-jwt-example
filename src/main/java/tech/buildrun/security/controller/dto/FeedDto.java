package tech.buildrun.security.controller.dto;

import java.time.Instant;

public record FeedDto(Long tweetId, String username, String content, Instant creationTimestamp) {
}
