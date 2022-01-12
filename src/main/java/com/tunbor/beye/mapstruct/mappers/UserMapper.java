package com.tunbor.beye.mapstruct.mappers;

import com.tunbor.beye.entity.User;
import com.tunbor.beye.mapstruct.dto.UserGetDTO;
import com.tunbor.beye.mapstruct.dto.UserPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Olakunle Awotunbo
 */

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserGetDTO userToUserGetDTO(User user);

    User userPostDTOToUser(UserPostDTO userPOSTDTO);

}
