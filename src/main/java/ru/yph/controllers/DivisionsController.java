package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    //get by parrent
    @GetMapping(value="/getonparrent/{parrentid}")
    @ResponseBody
    public List<DivisionDTO> getDivisionOnParrent(@PathVariable Long parrentid){
        List<Division> dl = divisionService.findOnParrent(parrentid);
        List<DivisionDTO> dtoList= dl.stream().map((p)->myModelMapper.map(p, DivisionDTO.class)).collect(Collectors.toList());
        return dtoList;
    }

    //get by ID
    @GetMapping(value="/{id}")
    @ResponseBody
    public DivisionDTO getDivision(@PathVariable long id){
        return myModelMapper.map(divisionService.findById(id), DivisionDTO.class);
    }

    //add division
    @PostMapping(value="/")
    @ResponseBody
    public void addDivision(@RequestParam DivisionDTO divisionDTO){

    }

    //dell division
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public void delDivision(@PathVariable Long id){
        divisionService.delDivision(id);
    }

    //get all division
    @GetMapping(value="/")
    @ResponseBody
    public List<DivisionDTO> getDivision(){
        List<Division> dl = divisionService.findAllOrderList();
        List<DivisionDTO> dtoList= dl.stream().map((p)->myModelMapper.map(p, DivisionDTO.class)).collect(Collectors.toList());
        return dtoList;
    }

}
