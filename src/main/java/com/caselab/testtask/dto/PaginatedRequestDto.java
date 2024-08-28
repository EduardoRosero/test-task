package com.caselab.testtask.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PaginatedRequestDto implements Serializable {
    private Integer page;
    private Integer size;
    private QuerySort sort;
    private QueryFilter filters;
}
