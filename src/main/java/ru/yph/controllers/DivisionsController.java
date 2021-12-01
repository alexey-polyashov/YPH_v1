package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.yph.dto.DivisionDTO;
import ru.yph.entities.Division;
import ru.yph.service.DivisionService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v21/divisions")
@Slf4j
public class DivisionsController {

    private final ModelMapper myModelMapper;

    private final DivisionService divisionService;

    @GetMapping(value="/getonparrent/{parrentid}")
    @ResponseBody
    public List<DivisionDTO> getDivisionOnParrent(@PathVariable long parrentid){
        List<Division> dl = divisionService.findOnParrent(parrentid);
        List<DivisionDTO> dtoList= dl.stream().map((p)->myModelMapper.map(p, DivisionDTO.class)).collect(Collectors.toList());
        return dtoList;
    }

    @GetMapping(value="/{id}")
    @ResponseBody
    public DivisionDTO getDivision(@PathVariable long id){
        return myModelMapper.map(divisionService.findById(id), DivisionDTO.class);
    }
}
