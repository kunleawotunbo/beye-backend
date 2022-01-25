package com.tunbor.beye.job;

import com.tunbor.beye.service.TokenBlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Olakunle Awotunbo
 * This job prune the TokenBlock table.
 */

@Component
@Slf4j
public class JwtBlockJob {

    @Autowired
    private final TokenBlockService tokenBlockService;

    public JwtBlockJob(TokenBlockService tokenBlockService) {
        this.tokenBlockService = tokenBlockService;
    }

    @Scheduled(cron = "0 0 0 * * ?")  // Run daily at 00:00 hour i.e 12am
    public void pruneTokenBlock() {
        log.info("JwtBlockJob start");
        LocalDateTime previousDay = LocalDateTime.now().minusDays(1);
        tokenBlockService.deleteTokenBlockByBlockDateBefore(previousDay);
        log.info("JwtBlockJob completed");
    }
}
