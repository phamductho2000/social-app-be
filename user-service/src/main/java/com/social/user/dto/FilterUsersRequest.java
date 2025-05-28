package com.social.user.dto;

import com.social.common.dto.FilterCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterUsersRequest {
    private int page;
    private int size;
    private List<FilterCriteria> queries;
}
