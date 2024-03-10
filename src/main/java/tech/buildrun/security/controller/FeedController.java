package tech.buildrun.security.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.buildrun.security.controller.dto.FeedDto;
import tech.buildrun.security.controller.dto.PageDto;
import tech.buildrun.security.domain.Tweet;
import tech.buildrun.security.repository.TweetRepository;

@RestController
public class FeedController {

    private final TweetRepository tweetRepository;

    public FeedController(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }


    @GetMapping("/public-feed")
    public ResponseEntity<PageDto<FeedDto>> feed(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        var result = tweetRepository.findAll(
                PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(Tweet::toFeed);

        return ResponseEntity.ok(new PageDto<FeedDto>(
                result.getContent(),
                page,
                pageSize,
                result.getTotalPages(),
                result.getTotalElements())
        );
    }

}
