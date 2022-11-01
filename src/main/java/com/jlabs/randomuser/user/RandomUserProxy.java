package com.jlabs.randomuser.user;

import com.jlabs.randomuser.user.response.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
class RandomUserProxy implements IRandomUserProxy {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public Optional<Results> getResults(RestTemplate restTemplate, String url) {
        try {
            return Optional.of(restTemplate.getForObject(url, Results.class));
        } catch (Exception e) {
            log.warn(e.toString());
            return Optional.empty();
        }
    }

}
