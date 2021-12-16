package ru.yph.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class NotValidFields extends RuntimeException{
    private final List<Violation> violations;
}
