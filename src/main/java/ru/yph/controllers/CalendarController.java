package ru.yph.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yph.dto.event.EventDTO;
import ru.yph.service.EventsService;

import java.sql.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "api/v21/calendar")
@Slf4j
public class CalendarController {

    private final ModelMapper myModelMapper;
    private final EventsService eventsService;

    @GetMapping(value = "/interval")
    public EventDTO getByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        return null;
    }

    @GetMapping(value = "/interval/common")
    public EventDTO getCommonByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        return null;
    }

    @GetMapping(value = "/interval/personally")
    public EventDTO getPersonallyByInterval(@RequestParam Date startDate, @RequestParam Date endDate){
        return null;
    }

}
