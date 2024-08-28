package com.caselab.testtask;

import com.caselab.testtask.dto.CreateFileRequestDto;
import com.caselab.testtask.dto.PaginatedRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createFile_MustReturnId() throws Exception {
        CreateFileRequestDto createFileRequestDto =
                CreateFileRequestDto
                        .builder()
                        .title("My test document")
                        .description("My test description for my test document")
                        .base64File("VGhpcyBpcyBhbiBleGFtcGxlIGZpbGUu")
                        .build();

        mockMvc.perform(post("/api/files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createFileRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
        ;
    }

    @Test
    public void getPagedFiles_MustReturnPage() throws Exception {
        PaginatedRequestDto paginatedRequestDto =
                PaginatedRequestDto
                        .builder()
                        .page(0)
                        .size(10)
                        .build();
        mockMvc.perform(post("/api/files/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paginatedRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
