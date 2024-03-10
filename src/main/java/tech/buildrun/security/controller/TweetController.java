package tech.buildrun.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.buildrun.security.controller.dto.CreateTweetDto;
import tech.buildrun.security.domain.Tweet;
import tech.buildrun.security.repository.TweetRepository;
import tech.buildrun.security.repository.UserRepository;

import java.time.Instant;

@RestController
@RequestMapping("/tweets")
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetController(TweetRepository tweetRepository,
                           UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto, JwtAuthenticationToken authentication) {
        var user = userRepository.findByUsername(authentication.getName());

        tweetRepository.save(new Tweet(null, user.get(), dto.content(), Instant.now()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId,
                                            JwtAuthenticationToken authentication) {

        var user = userRepository.findByUsername(authentication.getName());

        var tweet = tweetRepository.findById(tweetId);

        if (tweet.get().isFromUser(user.get()) || user.get().isAdmin()) {
            tweetRepository.deleteById(tweet.get().getTweetId());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok().build();
    }
}
