package com.example.progettoApiAlfresco.controller.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyDTO {

    private String id;

    private String title;

    private String description;

    private String defaultValue;

    private String dataType;

    private boolean isMultiValued;

    private boolean isMandatory;

    private Boolean isMandatoryEnforced;

    private boolean isProtected;

    private Set<ConstraintsDTO> constraintsDTO;
}
