package ru.yph.repositories.specifications;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import ru.yph.dtos.SearchCriteria;
import ru.yph.entities.task.Task;
import ru.yph.entities.user.User;
import ru.yph.exceptions.NotValidFields;
import ru.yph.exceptions.ResourceNotFoundException;
import ru.yph.exceptions.Violation;
import ru.yph.services.UserService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Data
public class TaskSpecification  implements Specification<Task> {

    private SearchCriteria criteria;
    private UserService userService;

    public TaskSpecification(SearchCriteria criteria, UserService userService) {
        this.criteria = criteria;
        this.userService = userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Predicate toPredicate
            (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        Class valueClass = root.get(criteria.getKey()).getJavaType();
        Object value = criteria.getValue();
        if(valueClass == java.sql.Date.class){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                value = new java.util.Date(sdf.parse(value.toString()).getTime());
            } catch (ParseException e) {
                throw new NotValidFields(Arrays.asList(new Violation(criteria.getKey(), "Не корректный формат даты")));
            }
        }else if(valueClass == ru.yph.entities.user.User.class){
            value = userService.findById(Long.valueOf(value.toString()))
                    .orElseThrow(()->new ResourceNotFoundException("Пользователь с id '" + criteria.getValue().toString() + "' не найден"));
        }


        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if(value instanceof Date) {
                return builder.greaterThanOrEqualTo(
                        root.<Date>get(criteria.getKey()), (Date) value);
            }else{
                return builder.greaterThanOrEqualTo(
                        root.<String>get(criteria.getKey()), value.toString());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if(value instanceof Date) {
                return builder.lessThanOrEqualTo(
                        root.<Date>get(criteria.getKey()), (Date) value);
            }else{
                return builder.lessThanOrEqualTo(
                        root.<String>get(criteria.getKey()), value.toString());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.<String>get(criteria.getKey()), "%" + value + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), value);
            }
        }

        return null;
    }
}
