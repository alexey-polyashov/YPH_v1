package ru.yph.entities.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.yph.dtos.user.NewUserContactDTO;
import ru.yph.dtos.user.NewUserDTO;
import ru.yph.dtos.user.NewUserDTOWithContacts;
import ru.yph.entities.Division;
import ru.yph.entities.Position;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.services.AddressTypeService;
import ru.yph.services.DivisionService;
import ru.yph.services.PositionService;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "shortname")
    private String shortname;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "male")
    private String male;

    @ManyToOne
    @JoinColumn(name = "position")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "division")
    private Division division;

    @Column(name = "image")
    private String image;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role"))
    private Collection<Role> roles;


    @OneToMany
    @JoinTable (name="contacts_owners",
            joinColumns=@JoinColumn (name="user_id"),
            inverseJoinColumns=@JoinColumn(name="contact_id"))
    private Collection<UserContact> userContacts;


    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updateTime;

    public void toUser(NewUserDTO newUserDTO, PositionService ps, DivisionService ds, AddressTypeService atr){

        this.login = newUserDTO.getLogin();
        this.email = newUserDTO.getEmail();
        this.shortname = newUserDTO.getShortname();
        this.token = newUserDTO.getPassword();
        this.fullname = newUserDTO.getFullname();
        this.birthday = newUserDTO.getBirthday();
        this.male = newUserDTO.getMale();
        this.image = newUserDTO.getImage();
        if(newUserDTO.getPosition()!=null) {
            Long id = newUserDTO.getPosition();
            this.position = ps.findById(newUserDTO.getPosition())
                    .orElseThrow(()->new ResourceNotFoundException("Position with '" + id + "' not found"));
        }
        if(newUserDTO.getDivision()!=null) {
            Long id = newUserDTO.getPosition();
            this.division = ds.findById(newUserDTO.getDivision())
                    .orElseThrow(()->new ResourceNotFoundException("Division with '" + id + "' not found"));
        }

    }

    public void toUserWithContacts(NewUserDTOWithContacts newUserDTO, PositionService ps, DivisionService ds, AddressTypeService atr){

        this.login = newUserDTO.getLogin();
        this.email = newUserDTO.getEmail();
        this.shortname = newUserDTO.getShortname();
        this.token = newUserDTO.getPassword();
        this.fullname = newUserDTO.getFullname();
        this.birthday = newUserDTO.getBirthday();
        this.male = newUserDTO.getMale();
        this.image = newUserDTO.getImage();
        if(newUserDTO.getPosition()!=null) {
            Long id = newUserDTO.getPosition();
            this.position = ps.findById(newUserDTO.getPosition())
                    .orElseThrow(()->new ResourceNotFoundException("Position with '" + id + "' not found"));
        }
        if(newUserDTO.getDivision()!=null) {
            Long id = newUserDTO.getPosition();
            this.division = ds.findById(newUserDTO.getDivision())
                    .orElseThrow(()->new ResourceNotFoundException("Division with '" + id + "' not found"));
        }

        if(newUserDTO.getUserContacts()!=null) {
            for (NewUserContactDTO contctDTO : newUserDTO.getUserContacts()) {
                UserContact uc = new UserContact();
                AddressType at = atr.findById(contctDTO.getAddressType())
                        .orElseThrow(()->new ResourceNotFoundException("Address type with '" + contctDTO.getAddressType() + "' not found"));
                uc.setAddressType(at);
                uc.setUser(this);
                uc.setRepresentation(contctDTO.getRepresentation());
                uc.setComment(contctDTO.getComment());
                this.userContacts.add(uc);
            }
        }

    }


}