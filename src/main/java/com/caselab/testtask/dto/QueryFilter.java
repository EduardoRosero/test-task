package com.caselab.testtask.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryFilter implements Serializable {
    private String from;
    private String to;
    private String title;
    private String description;
}
