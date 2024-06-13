package com.example.progettoApiAlfresco.controller.dto;


import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeDTO {


    @NotBlank
    @Pattern(regexp = "^(?!(.*[\"*\\\\><?/:|]+.*)|(.*[.]?.*[.]+$)|(.*[ ]+$))", message = "Invalid name format.")
    private String name;
    
    @NotBlank
    private String nodeType;
    private JsonNode properties;
    private Set<String> aspectNames;
    private PermissionDTO permissions;
    private DefinitionDTO definition;
    private String relativePath;
    private AssociationDTO association;
    private Set<ChildAssociationBodyDTO> secondaryChildren;
    private Set<AssociationBodyDTO> targets;
}
