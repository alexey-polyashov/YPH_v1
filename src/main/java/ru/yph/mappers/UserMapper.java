package ru.yph.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yph.dtos.user.*;
import ru.yph.entities.user.User;
import ru.yph.services.DivisionService;
import ru.yph.services.PositionService;

import java.util.stream.Collectors;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class UserMapper {

    private final PositionService positionService;
    private final DivisionService divisionService;
    private final ContactsMapper contactsMapper;
    private final ModelMapper userToDtoMapper;
    private final ModelMapper userToDtoWithContactsMapper;
    private final ModelMapper dtoToUserMapper;
    private final ModelMapper dtoWithContactsToUserMapper;

    @Autowired
    public UserMapper(ContactsMapper contactsMapper, PositionService positionService, DivisionService divisionService){

        this.contactsMapper = contactsMapper;
        this.positionService = positionService;
        this.divisionService = divisionService;

        this.userToDtoMapper = new ModelMapper();
        userToDtoMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        //userToDtoMapper.createTypeMap(User.class, UserDTO.class);

        this.userToDtoWithContactsMapper = new ModelMapper();
        userToDtoWithContactsMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        userToDtoWithContactsMapper.createTypeMap(User.class, UserDTOWithContacts.class)
                .addMappings(m->m.skip(UserDTOWithContacts::setUserContacts));

        this.dtoToUserMapper = new ModelMapper();
        dtoToUserMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        //dtoToUserMapper.createTypeMap(NewUserDTO.class, User.class);

        this.dtoWithContactsToUserMapper = new ModelMapper();
        dtoWithContactsToUserMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        dtoWithContactsToUserMapper.createTypeMap(NewUserDTOWithContacts.class, User.class)
                //.addMappings(mapper -> mapper.when().skip(NewUserDTO::getId, User::setId))
                //.addMapping((u)->positionService.findById(u.getPosition()), User::setPosition)
                //.addMapping((u)->divisionService.findById(u.getDivision()), User::setDivision)
                .addMappings(m->m.skip(NewUserDTOWithContacts::getUserContacts, User::setUserContacts))
        //.addMappings(m->m.skip(User::setCreateTime))
        //.addMappings(m->m.skip(User::setUpdateTime))
        ;
    }

    public UserDTO userToDto(User user){
        UserDTO userDto = userToDtoMapper.map(user, UserDTO.class);
        return userDto;
    }
    public UserDTOWithContacts userToDtoWithContacts(User user){
        UserDTOWithContacts userDTO = userToDtoMapper.map(user, UserDTOWithContacts.class);
        userDTO.setUserContacts(
                user.getUserContacts().stream()
                        .map(uc->contactsMapper.contactToDto(uc))
                        .collect(Collectors.toList())
        );
        return userDTO;
    }

    public User dtoToUser(NewUserDTO newUserDTO){
        User user = dtoToUserMapper.map(newUserDTO, User.class);
        return user;
    }

    public User dtoWithContactsToUser(NewUserDTOWithContacts newUserDTO){
        User user = dtoToUserMapper.map(newUserDTO, User.class);
        user.setUserContacts(
                newUserDTO.getUserContacts().stream()
                        .map(uc->contactsMapper.dtoToContact(uc, user))
                        .collect(Collectors.toList())
        );
        return user;
    }

}
