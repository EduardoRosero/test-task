package com.caselab.testtask.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Builder.Default
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate = ZonedDateTime.now();
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
//    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String base64;

}
