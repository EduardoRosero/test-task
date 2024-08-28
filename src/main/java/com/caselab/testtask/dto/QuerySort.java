package com.caselab.testtask.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuerySort implements Serializable {
    private String field;
    private String order;
}
