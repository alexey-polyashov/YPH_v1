package ru.yph.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TaskSearchCriteriaDTO {
    List<SearchCriteriaDTO> filter;
    private Integer page = 0;
    private Integer recordsOnPage = 10;
}
