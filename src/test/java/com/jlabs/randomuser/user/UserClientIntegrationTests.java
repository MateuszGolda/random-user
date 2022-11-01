package com.jlabs.randomuser.user;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WireMockTest(httpPort = 8080)
class UserClientIntegrationTests {

    @Autowired
    private UserClient userClient;

    private static final String response = "{\"results\":[{\"gender\":\"female\",\"name\":{\"title\":\"Mrs\",\"first\":\"Eve\",\"last\":\"Gonzalez\"},\"location\":{\"street\":{\"number\":7856,\"name\":\"Avenue du Fort-Caire\"},\"city\":\"Clermont-Ferrand\",\"state\":\"Haute-Saône\",\"country\":\"France\",\"postcode\":57403,\"coordinates\":{\"latitude\":\"-14.4805\",\"longitude\":\"20.1783\"},\"timezone\":{\"offset\":\"-6:00\",\"description\":\"Central Time (US & Canada), Mexico City\"}},\"email\":\"eve.gonzalez@example.com\",\"login\":{\"uuid\":\"6663c136-807c-4fbe-97d0-ed11efe18d05\",\"username\":\"angrytiger850\",\"password\":\"smeller\",\"salt\":\"bDV9S5fC\",\"md5\":\"9607ad8c007d453b9fd2fcea03badedc\",\"sha1\":\"dc1906924a523771745a2220497c84ac0b41b0e8\",\"sha256\":\"d9d2d73ba833ff2eeec76ab362937e8773e267d62745719ef8d71e2142b8da34\"},\"dob\":{\"date\":\"1948-08-31T11:16:58.068Z\",\"age\":74},\"registered\":{\"date\":\"2004-03-01T09:35:45.694Z\",\"age\":18},\"phone\":\"05-90-58-03-05\",\"cell\":\"06-35-99-58-16\",\"id\":{\"name\":\"INSEE\",\"value\":\"2480759768453 25\"},\"picture\":{\"large\":\"https://randomuser.me/api/portraits/women/86.jpg\",\"medium\":\"https://randomuser.me/api/portraits/med/women/86.jpg\",\"thumbnail\":\"https://randomuser.me/api/portraits/thumb/women/86.jpg\"},\"nat\":\"FR\"}],\"info\":{\"seed\":\"80996284c9588744\",\"results\":1,\"page\":1,\"version\":\"1.4\"}}\n";

    @Test
    void testUserClient() {
        List<UserDTO> expected = new ArrayList<>(1);
        expected.add(new UserDTO("female",
                "Eve",
                "Gonzalez",
                "Clermont-Ferrand",
                UUID.fromString("6663c136-807c-4fbe-97d0-ed11efe18d05"),
                LocalDateTime.parse("2004-03-01T09:35:45.694")));
        stubFor(WireMock.get("/randomuser")
                .willReturn(ok()
                        .withHeader("content-type", "application/json")
                        .withBody(response)));

        List<UserDTO> actual = userClient.users();
        assertIterableEquals(expected, actual);
    }

    @Test
    void testUserClient_whenTimeoutExceeded_returnEmptyList() {
        List<UserDTO> expected = new ArrayList<>(0);
        stubFor(WireMock.get("/randomuser")
                .willReturn(ok()
                        .withHeader("content-type", "application/json")
                        .withBody(response)
                        .withFixedDelay(3009)));

        List<UserDTO> actual = userClient.users();
        assertIterableEquals(expected, actual);
    }

    @Test
    void testUserClient_whenFailure_returnEmptyList() {
        List<UserDTO> expected = new ArrayList<>(0);
        stubFor(WireMock.get("/randomuser")
                .willReturn(badRequest()
                        .withFault(Fault.EMPTY_RESPONSE)));

        List<UserDTO> actual = userClient.users();
        assertIterableEquals(expected, actual);
    }
}
