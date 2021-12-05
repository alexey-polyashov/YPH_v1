package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yph.dto.event.EventDTO;
import ru.yph.dto.event.NewEventDTO;
import ru.yph.entities.event.Event;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.service.EventsService;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v21/events")
@Slf4j
public class EventsControler {

    private final ModelMapper myModelMapper;
    private final EventsService eventsService;

    @GetMapping(value = "/{id}")
    public EventDTO getByID(@PathVariable long id){
        Event event = eventsService.findById(id).orElseThrow(()->new ResourceNotFoundException("Event with id '" + id + "' not found!"));
        EventDTO eventDTO= myModelMapper.map(
                event,
                EventDTO.class
        );
        return eventDTO;
    }

    @GetMapping(value = "/interval")
    public List<EventDTO> getByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        List<Event> events = eventsService.findByDateBetween(startDate, endDate);
        List<EventDTO> eventsDTO = events.stream().map(
                (e)->myModelMapper.map(e, EventDTO.class)
        ).collect(Collectors.toList());
        return eventsDTO;
    }

    @GetMapping(value = "/interval/common")
    public List<EventDTO> getCommonByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        List<Event> events = eventsService.findByDateBetweenAndOwnerIsNull(startDate, endDate);
        List<EventDTO> eventsDTO = events.stream().map(
                (e)->myModelMapper.map(e, EventDTO.class)
        ).collect(Collectors.toList());
        return eventsDTO;
    }

    @GetMapping(value = "/interval/personally")
    public List<EventDTO> getPersonallyByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        List<Event> events = eventsService.findByDateBetweenAndOwnerIsNotNull(startDate, endDate);
        List<EventDTO> eventsDTO = events.stream().map(
                (e)->myModelMapper.map(e, EventDTO.class)
        ).collect(Collectors.toList());
        return eventsDTO;
    }

    @PostMapping(value = "/")
    public void addEvent(@RequestBody NewEventDTO newEvent){
        eventsService.addEvent(newEvent);
    }

    @DeleteMapping(value = "/{id}")
    public void delEvent(@PathVariable long id){
        eventsService.delEvent(id);
    }

}
