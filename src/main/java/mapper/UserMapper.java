package mapper;

import model.User;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;
import java.util.*;
import java.util.stream.Collectors;


public class UserMapper {
    public static UserDTO convertUserToUserDTO(User user){
        return new UserDTOBuilder().setUsername(user.getUsername()).build();
    }

    public static List<UserDTO> conertUserListToUserDTOList (List<User> users){
        return users.parallelStream().map(UserMapper::convertUserToUserDTO).collect(Collectors.toList());
    }

}
