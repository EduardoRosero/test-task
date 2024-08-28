package com.caselab.testtask.specification;

import com.caselab.testtask.dto.QueryFilter;
import com.caselab.testtask.entity.FileEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileSpecification {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Specification<FileEntity> getFilesByCriteria(QueryFilter filters) {
        return (Root<FileEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if(!ObjectUtils.isEmpty(filters)) {
                if(!ObjectUtils.isEmpty(filters.getFrom())){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), ZonedDateTime.parse(filters.getFrom(), formatter.withZone(ZoneId.of("UTC")))));
                }
                if(!ObjectUtils.isEmpty(filters.getTo())){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), ZonedDateTime.parse(filters.getTo(), formatter.withZone(ZoneId.of("UTC")))));
                }
                if(!ObjectUtils.isEmpty(filters.getTitle())){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + filters.getTitle() + "%"));
                }
                if(!ObjectUtils.isEmpty(filters.getDescription())){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + filters.getDescription() + "%"));
                }
            }
            return predicate;
        };
    }
}
