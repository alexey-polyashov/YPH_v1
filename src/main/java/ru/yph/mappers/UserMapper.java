package ru.yph.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yph.dto.user.NewUserDTO;
import ru.yph.dto.user.UserDTO;
import ru.yph.entities.user.User;

import java.util.stream.Collectors;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class UserMapper {

    private final ContactsMapper contactsMapper;
    private final ModelMapper userToDtoMapper;
    private final ModelMapper dtoToUserMapper;

    @Autowired
    public UserMapper(ContactsMapper contactsMapper){

        this.contactsMapper = contactsMapper;

        this.userToDtoMapper = new ModelMapper();
        userToDtoMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        userToDtoMapper.createTypeMap(User.class, UserDTO.class)
                .addMappings(m->m.skip(UserDTO::setUserContacts));

        this.dtoToUserMapper = new ModelMapper();
        dtoToUserMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
//        dtoToUserMapper.createTypeMap(NewUserDTO.class, User.class)
//                .addMappings(m->m.skip(User::setUserContacts));
    }

    public UserDTO userToDto(User user){
        UserDTO userDto = userToDtoMapper.map(user, UserDTO.class);
        userDto.setUserContacts(
                user.getUserContacts().stream()
                        .map(uc->contactsMapper.contactToDto(uc))
                        .collect(Collectors.toList())
        );
        return userDto;
    }

    public User dtoToUser(NewUserDTO newUserDTO){
        User user = dtoToUserMapper.map(newUserDTO, User.class);
        user.setUserContacts(
                newUserDTO.getUserContacts().stream()
                        .map(uc->contactsMapper.dtoToContact(uc, user))
                        .collect(Collectors.toList())
        );
        return user;
    }

}
