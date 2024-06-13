package com.example.progettoApiAlfresco.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentInfo {

    @NotBlank
    private String mimeType;
    private String mimeTypeName;
    private Long sizeInBytes;
    private String encoding;
}
