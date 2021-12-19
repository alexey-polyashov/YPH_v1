package ru.yph.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.yph.dtos.user.NewUserContactDTO;
import ru.yph.dtos.user.UserContactDTO;
import ru.yph.entities.user.User;
import ru.yph.entities.user.UserContact;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class ContactsMapper {

    private final ModelMapper contactToDtoMapper;
    private final ModelMapper dtoToContactMapper;

    public ContactsMapper(){

        this.contactToDtoMapper = new ModelMapper();
        contactToDtoMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        contactToDtoMapper.createTypeMap(UserContact.class, UserContactDTO.class)
                .addMapping((u)->u.getUser().getId(), UserContactDTO::setContactOwnerId);

        this.dtoToContactMapper = new ModelMapper();
        contactToDtoMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STANDARD)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        contactToDtoMapper.createTypeMap(NewUserContactDTO.class, UserContact.class)
                .addMappings(m->m.skip(UserContact::setUser));

    }

    public UserContactDTO contactToDto(UserContact userContact){
        return contactToDtoMapper.map(userContact, UserContactDTO.class);
    }

    public UserContact dtoToContact(NewUserContactDTO newUserContactDTO, User user){
        UserContact userContact =  dtoToContactMapper.map(newUserContactDTO, UserContact.class);
        userContact.setUser(user);
        return userContact;
    }
}
