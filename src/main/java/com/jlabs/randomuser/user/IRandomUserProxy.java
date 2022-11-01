package com.jlabs.randomuser.user;

import com.jlabs.randomuser.user.response.Results;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

interface IRandomUserProxy {

    Optional<Results> getResults(RestTemplate restTemplate, String url);

}
