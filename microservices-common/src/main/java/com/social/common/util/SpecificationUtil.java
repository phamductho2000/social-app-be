package com.social.common.util;

import com.social.common.dto.FilterCriteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Objects;

public class SpecificationUtil {

//    public static List<FilterCriteria> genFilter(Class<?> clazz) {
//
//    }

    public static Query createSpecification(List<FilterCriteria> filters) {
        if (null == filters || filters.isEmpty()) {
            return new Query();
        }
        Criteria criteria = new Criteria();
        Query query = new Query();
        for (FilterCriteria filter : filters) {
            if (Objects.nonNull(filter.getValue()) || Objects.nonNull(filter.getValues())) {
                buildQuery(filter, criteria);
            }
        }
        query.addCriteria(criteria);
        return query;
    }

    private static void buildQuery(FilterCriteria filter, Criteria criteria) {
        switch (filter.getOperation()) {
            case "lk" -> criteria.and(filter.getKey()).regex((String) filter.getValue());
            case "lt" -> criteria.and(filter.getKey()).lt(filter.getValue());
            case "gt" -> criteria.and(filter.getKey()).gt(filter.getValue());
            case "lte" -> criteria.and(filter.getKey()).lte(filter.getValue());
            case "gte" -> criteria.and(filter.getKey()).gte(filter.getValue());
            case "ne" -> criteria.and(filter.getKey()).ne(null);
            case "in" -> criteria.and(filter.getKey()).in(filter.getValues());
            case "nin" -> criteria.and(filter.getKey()).nin(filter.getValues());
            default -> criteria.and(filter.getKey()).is(filter.getValue());
        }
    }
}
