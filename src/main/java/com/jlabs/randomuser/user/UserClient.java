package com.jlabs.randomuser.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
class UserClient {

    private final RestTemplate restTemplate;
    private final UserMapper userMapper;
    private final IRandomUserProxy randomUserProxy;

    @Value("${randomuser.url}")
    private String url;

    @Autowired
    UserClient(RestTemplate restTemplate, UserMapper userMapper, IRandomUserProxy randomUserProxy) {
        this.restTemplate = restTemplate;
        this.userMapper = userMapper;
        this.randomUserProxy = randomUserProxy;
    }

    List<UserDTO> users() {
        return randomUserProxy.getResults(restTemplate, url)
                .map(results -> results.getResults()
                        .stream()
                        .map(userMapper::resultToUserDTO)
                        .toList())
                .orElse(new ArrayList<>(0));
    }

}
