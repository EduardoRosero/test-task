package com.caselab.testtask.controller;

import com.caselab.testtask.dto.CreateFileRequestDto;
import com.caselab.testtask.dto.PaginatedRequestDto;
import com.caselab.testtask.entity.FileEntity;
import com.caselab.testtask.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<Long> createFile(@RequestBody CreateFileRequestDto createFileRequestDto) {
        Long id = fileService.createFile(createFileRequestDto);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileEntity> getFileById(@PathVariable Long id) {
        FileEntity fileEntity = fileService.findFirstById(id);
        return ResponseEntity.ok(fileEntity);
    }

    @PostMapping("/all")
    public Page<FileEntity> obtenerArchivosPaginados(@Validated @RequestBody PaginatedRequestDto paginatedRequestDto) {
        return fileService.getAllFiles(paginatedRequestDto);
    }
}
