package com.caselab.testtask.mapper;

import com.caselab.testtask.dto.CreateFileRequestDto;
import com.caselab.testtask.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {
    public FileEntity createFileToFileEntityMapper(CreateFileRequestDto createFileRequestDto) {
        return FileEntity
                .builder()
                .title(createFileRequestDto.getTitle())
                .description(createFileRequestDto.getDescription())
                .base64(createFileRequestDto.getBase64File())
                .build();
    }
}
