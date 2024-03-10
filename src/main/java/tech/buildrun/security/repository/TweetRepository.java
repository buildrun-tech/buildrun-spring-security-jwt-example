package tech.buildrun.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.buildrun.security.domain.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
