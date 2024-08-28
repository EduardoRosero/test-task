package com.caselab.testtask.dto;

import lombok.Builder;
import lombok.Data;
import java.io.Serializable;


@Data
@Builder
public class CreateFileRequestDto implements Serializable {
    private String title;
    private String description;
    private String base64File;
}
