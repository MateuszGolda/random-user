package com.jlabs.randomuser.user;

import com.jlabs.randomuser.user.response.Results;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class RandomUserProxy implements IRandomUserProxy {

    public Results getResults(RestTemplate restTemplate, String url) {
        return restTemplate.getForObject(url, Results.class);
    }

}
