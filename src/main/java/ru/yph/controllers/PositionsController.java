package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yph.dto.DivisionDTO;
import ru.yph.dto.PositionDTO;
import ru.yph.entities.Division;
import ru.yph.entities.Position;
import ru.yph.service.DivisionService;
import ru.yph.service.PositionService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v21/positions")
@Slf4j
public class PositionsController {

    private final ModelMapper myModelMapper;
    private final PositionService positionService;

    //get all division with children
    @GetMapping(value="/")
    @ResponseBody
    public List<PositionDTO> getPositions(){
        List<Position> positions= positionService.findAll();
        return positions.stream().map((m)->myModelMapper.map(m, PositionDTO.class)).collect(Collectors.toList());
    }

    //get by ID
    @GetMapping(value="/{id}")
    @ResponseBody
    public PositionDTO getPosition(@PathVariable long id){
        return myModelMapper.map(positionService.findById(id), PositionDTO.class);
    }

    //add division
    @PostMapping(value="/")
    @ResponseBody
    public void addPosition(@RequestParam DivisionDTO divisionDTO){

    }

    //dell division
    @DeleteMapping(value="/{id}")
    @ResponseBody
    public void delPosition(@PathVariable long id){

    }


}
