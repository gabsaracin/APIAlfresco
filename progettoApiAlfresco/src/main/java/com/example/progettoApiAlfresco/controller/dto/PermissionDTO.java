package com.example.progettoApiAlfresco.controller.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO {

    private boolean isInheritanceEnabled;

    private Set<PermissionElementDTO> locallySet;
}
