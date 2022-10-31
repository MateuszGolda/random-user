package com.jlabs.randomuser.user;

import com.jlabs.randomuser.config.RestTemplateConfig;
import com.jlabs.randomuser.user.response.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class RandomuserApplicationTests {

    @Test
    void testUserClient() {
        IRandomUserProxy randomUserProxy = (restTemplate, url) -> {
            Results results = new Results();
            Result result = new Result();
            result.setGender("male");
            result.setName(new Name("title", "first", "last"));
            Location location = new Location();
            location.setCity("London");
            result.setLocation(location);
            Login login = new Login();
            login.setUuid("e706e3c2-2984-4dee-a176-56f957ac9b85");
            result.setLogin(login);
            result.setRegistered(new Registered("2021-04-25T00:15:26.685Z", 1));
            results.setResults(List.of(result));
            return results;
        };
        List<UserDTO> expected = new ArrayList<>(1);
        expected.add(new UserDTO("male",
                "first",
                "last",
                "London",
                UUID.fromString("e706e3c2-2984-4dee-a176-56f957ac9b85"),
                LocalDateTime.parse("2021-04-25T00:15:26.685")));

        UserClient userClient = new UserClient(new RestTemplateConfig().restTemplate(), new UserMapperImpl(), randomUserProxy);
        List<UserDTO> actual = userClient.users();
        Assertions.assertIterableEquals(expected, actual);
    }

}
