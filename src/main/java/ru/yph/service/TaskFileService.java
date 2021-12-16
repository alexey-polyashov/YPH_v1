package ru.yph.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yph.entities.task.TaskFile;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.repositories.TaskFilesRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskFileService {

    private final TaskFilesRepository filesRepository;

    public Optional<TaskFile> findById(Long id){
        return filesRepository.findById(id);
    }

}
