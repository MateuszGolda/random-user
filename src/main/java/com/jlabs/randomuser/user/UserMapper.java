package com.jlabs.randomuser.user;

import com.jlabs.randomuser.user.response.Result;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
interface UserMapper {

    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "name.first", target = "firstName")
    @Mapping(source = "name.last", target = "lastName")
    @Mapping(source = "location.city", target = "city")
    @Mapping(source = "login.uuid", target = "loginUuid")
    @Mapping(source = "registered.date", target = "registrationDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    UserDTO resultToUserDTO(Result result);

}
