package tech.buildrun.security.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import tech.buildrun.security.controller.dto.FeedDto;

import java.time.Instant;

@Entity
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tweetId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content;

    @CreationTimestamp
    private Instant creationTimestamp;

    public Tweet() {
    }

    public Tweet(Long tweetId, User user, String content, Instant creationTimestamp) {
        this.tweetId = tweetId;
        this.user = user;
        this.content = content;
        this.creationTimestamp = creationTimestamp;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public boolean isFromUser(User user) {
        return this.user.getUserId().equals(user.getUserId());
    }

    public FeedDto toFeed() {
        return new FeedDto(
                tweetId,
                user.getUsername(),
                content,
                creationTimestamp
        );
    }
}
