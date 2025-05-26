package com.social.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

public class QueryBuilder {

    private final List<Criteria> criteriaList = new ArrayList<>();

    private QueryBuilder() {
    }

    public static QueryBuilder builder() {
        return new QueryBuilder();
    }

    public QueryBuilder eq(String field, Object value) {
        if (isValid(value)) {
            criteriaList.add(Criteria.where(field).is(value));
        }
        return this;
    }

    public QueryBuilder notEq(String field, Object value) {
        if (isValid(value)) {
            criteriaList.add(Criteria.where(field).ne(value));
        }
        return this;
    }

    public QueryBuilder in(String field, Collection<?> values) {
        if (values != null && !values.isEmpty()) {
            criteriaList.add(Criteria.where(field).in(values));
        }
        return this;
    }

    public QueryBuilder gt(String field, Object value) {
        if (isValid(value)) {
            criteriaList.add(Criteria.where(field).gt(value));
        }
        return this;
    }

    public QueryBuilder gte(String field, Object value) {
        if (isValid(value)) {
            criteriaList.add(Criteria.where(field).gte(value));
        }
        return this;
    }

    public QueryBuilder lt(String field, Object value) {
        if (isValid(value)) {
            criteriaList.add(Criteria.where(field).lt(value));
        }
        return this;
    }

    public QueryBuilder lte(String field, Object value) {
        if (isValid(value)) {
            criteriaList.add(Criteria.where(field).lte(value));
        }
        return this;
    }

    public QueryBuilder between(String field, Object from, Object to) {
        if (from != null && to != null) {
            criteriaList.add(Criteria.where(field).gte(from).lte(to));
        } else if (from != null) {
            criteriaList.add(Criteria.where(field).gte(from));
        } else if (to != null) {
            criteriaList.add(Criteria.where(field).lte(to));
        }
        return this;
    }

    public QueryBuilder exists(String field, boolean exists) {
        criteriaList.add(Criteria.where(field).exists(exists));
        return this;
    }

    public QueryBuilder regex(String field, String value) {
        if (isValid(value)) {
            criteriaList.add(Criteria.where(field).regex(".*" + value + ".*", "i"));
        }
        return this;
    }

    public QueryBuilder or(QueryBuilder... builders) {
        List<Criteria> orCriteria = new ArrayList<>();
        for (QueryBuilder builder : builders) {
            orCriteria.add(builder.toCriteria());
        }
        criteriaList.add(new Criteria().orOperator(orCriteria.toArray(new Criteria[0])));
        return this;
    }

    public QueryBuilder and(QueryBuilder... builders) {
        for (QueryBuilder builder : builders) {
            criteriaList.add(builder.toCriteria());
        }
        return this;
    }

    private boolean isValid(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof String str) {
            return StringUtils.hasText(str);
        }
        return true;
    }

    private Criteria toCriteria() {
        if (criteriaList.isEmpty()) {
            return new Criteria();
        }
        if (criteriaList.size() == 1) {
            return criteriaList.getFirst();
        }
        return new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
    }

    public Query build() {
        return new Query(toCriteria());
    }
}