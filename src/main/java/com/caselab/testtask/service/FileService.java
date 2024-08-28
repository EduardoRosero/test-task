package com.caselab.testtask.service;

import com.caselab.testtask.dto.CreateFileRequestDto;
import com.caselab.testtask.dto.PaginatedRequestDto;
import com.caselab.testtask.dto.QuerySort;
import com.caselab.testtask.entity.FileEntity;
import com.caselab.testtask.exception.HttpResponseException;
import com.caselab.testtask.mapper.FileMapper;
import com.caselab.testtask.repository.FileRepository;
import com.caselab.testtask.specification.FileSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@CommonsLog
public class FileService {
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final FileSpecification fileSpecification;

    @Transactional
    public Long createFile(CreateFileRequestDto createFileRequestDto) {
        FileEntity savedFile = fileRepository.save(fileMapper.createFileToFileEntityMapper(createFileRequestDto));
        return savedFile.getId();
    }

    public FileEntity findFirstById(Long id) {
        try {
            FileEntity fileEntity = fileRepository.findFirstById(id);
            if (!ObjectUtils.isEmpty(fileEntity)) {
                return fileEntity;
            }
            throw new HttpResponseException(
                    HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(),
                    "The file with id " + id + " does not exist"
            );
        } catch (Exception e) {
            log.error("An error occurred while trying to find the file with id " + id, e);
            throw new HttpResponseException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while trying to find the file with id " + id
            );
        }
    }

    public Page<FileEntity> getAllFiles(PaginatedRequestDto paginatedRequestDto) {
        Sort sort;
        QuerySort sortValue = paginatedRequestDto.getSort();
        if (!ObjectUtils.isEmpty(sortValue)) {
            if (sortValue.getOrder().toLowerCase(Locale.ROOT).equals("asc")) {
                sort = Sort.by(Sort.Direction.ASC, sortValue.getField());
            } else {
                sort = Sort.by(Sort.Direction.DESC, sortValue.getField());
            }
        } else {
            sort = Sort.by(Sort.Direction.DESC, "creationDate");
        }
        int page = !ObjectUtils.isEmpty(paginatedRequestDto.getPage()) ? paginatedRequestDto.getPage() : 0;
        int size = !ObjectUtils.isEmpty(paginatedRequestDto.getSize()) ? paginatedRequestDto.getSize() : 10;
        Pageable pageable = PageRequest.of(page, size, sort);
        return fileRepository.findAll(fileSpecification.getFilesByCriteria(paginatedRequestDto.getFilters()), pageable);
    }
}
