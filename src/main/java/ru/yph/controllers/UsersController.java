package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yph.dto.DivisionDTO;
import ru.yph.dto.UserDTO;
import ru.yph.entities.User;
import ru.yph.service.DivisionService;
import ru.yph.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v21/users")
@Slf4j
public class UsersController {

    private final ModelMapper myModelMapper;
    private final UserService userService;


    @GetMapping(value="/userdata")
    @ResponseBody
    public UserDTO userData(Principal principal){
        return myModelMapper.map(userService.getUserData(principal), UserDTO.class);
    }

    @GetMapping(value="/findbylogin/{login}")
    @ResponseBody
    public UserDTO userData(@PathVariable String login){
        return myModelMapper.map(userService.findByLogin(login), UserDTO.class);
    }

    @GetMapping(value="/userlist")
    @ResponseBody
    public Page<UserDTO> userList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int recordsOnPage){
        Page<User> pageUser = userService.findAll(page, recordsOnPage);
        Page<UserDTO> userDtoPage = pageUser.map(p->myModelMapper.map(p, UserDTO.class));
        return userDtoPage;
    }

}
