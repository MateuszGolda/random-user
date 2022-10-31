package com.jlabs.randomuser.user;

import java.time.LocalDateTime;
import java.util.UUID;

record UserDTO(String gender,
               String firstName,
               String lastName,
               String city,
               UUID loginUuid,
               LocalDateTime registrationDate) {
}
