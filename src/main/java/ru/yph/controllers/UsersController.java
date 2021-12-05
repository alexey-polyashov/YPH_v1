package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yph.dto.user.NewUserDTO;
import ru.yph.dto.user.UserDTO;
import ru.yph.entities.user.User;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.mappers.UserMapper;
import ru.yph.service.UserService;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v21/users")
@Slf4j
public class UsersController {

    private final ModelMapper myModelMapper;
    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping(value="/findbyid/{id}")
    @ResponseBody
    public UserDTO userDataById(@PathVariable long id){
        return userMapper.userToDto(
                userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id '" + id + "' not found!")));
    }

    @GetMapping(value="/userdata")
    @ResponseBody
    public UserDTO userData(Principal principal){
        return myModelMapper.map(
                userService.getUserData(principal).orElseThrow(() -> new ResourceNotFoundException("User not found in data base!"))
                ,UserDTO.class);
    }

    @GetMapping(value="/findbylogin/{login}")
    @ResponseBody
    public UserDTO userData(@PathVariable String login){
        return myModelMapper.map(
                userService.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User with login '" + login + "' not found!"))
                ,UserDTO.class);
    }

    @GetMapping(value="/userlist")
    @ResponseBody
    public Page<UserDTO> userList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int recordsOnPage){
        Page<User> pageUser = userService.findAll(page, recordsOnPage);
        Page<UserDTO> userDtoPage = pageUser.map(p->myModelMapper.map(p, UserDTO.class));
        return userDtoPage;
    }

    @PostMapping(value="/")
    @ResponseBody
    public void addUser(@Valid @RequestBody NewUserDTO userData){
        userService.addUser(userData);
    }

    @DeleteMapping(value="/{id}")
    @ResponseBody
    public void delUser(@PathVariable Long id){
        userService.delUser(id);
    }
}
