package com.jlabs.randomuser.user;

import com.jlabs.randomuser.user.response.Results;
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
        Results results = randomUserProxy.getResults(restTemplate, url);
        if (results == null) {
            return new ArrayList<>(0);
        }
        return results.getResults()
                .stream()
                .map(userMapper::resultToUserDTO)
                .toList();
    }

}
