package ru.yph.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.yph.dtos.SearchCriteria;
import ru.yph.entities.task.Task;
import ru.yph.entities.user.User;
import ru.yph.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TaskSpecificationBuilder {

    private final List<SearchCriteria> params;

    private final UserService userService;

    public TaskSpecificationBuilder(UserService userService) {
        params = new ArrayList<SearchCriteria>();
        this.userService = userService;
    }

    public TaskSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Task> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map((p)->new TaskSpecification(p, userService))
                .collect(Collectors.toList());

        Specification<Task> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }

}
