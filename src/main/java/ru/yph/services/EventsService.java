package ru.yph.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yph.dtos.event.NewEventDTO;
import ru.yph.entities.event.Event;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.EventRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventsService {

    private final EventRepository eventRepository;
    private final ModelMapper myModelMapper;

    public Optional<Event> findById(long id){
        return eventRepository.findById(id);
    }

    @Transactional
    public List<Event> findByDateBetween(Date minDate, Date maxDate) {
        return eventRepository.findByInitionDateBetween(minDate, maxDate);
    }

    @Transactional
    public List<Event> findByDateBetweenAndOwnerIsNull(Date minDate, Date maxDate) {
        return eventRepository.findByInitionDateBetweenAndOwnerIsNull(minDate, maxDate);
    }

    @Transactional
    public List<Event> findByDateBetweenAndOwnerIsNotNull(Date minDate, Date maxDate) {
        return eventRepository.findByInitionDateBetweenAndOwnerIsNotNull(minDate, maxDate);
    }

    public void addEvent(NewEventDTO newEvent) {
        Event event = myModelMapper.map(newEvent, Event.class);
        eventRepository.save(event);
    }

    public void delEvent(long id) {
        Event event = findById(id).orElseThrow(()->new ResourceNotFoundException("Event with id '" + id + "' not found!"));
        eventRepository.delete(event);
    }

}
