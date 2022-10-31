package com.jlabs.randomuser.user;

import com.jlabs.randomuser.user.response.Results;
import org.springframework.web.client.RestTemplate;

interface IRandomUserProxy {

    Results getResults(RestTemplate restTemplate, String url);

}
