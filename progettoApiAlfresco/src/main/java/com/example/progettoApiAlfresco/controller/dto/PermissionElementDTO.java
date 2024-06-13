package com.example.progettoApiAlfresco.controller.dto;

import com.example.progettoApiAlfresco.enums.AccessStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionElementDTO {

    private String authorityId;

    private String name;

    private AccessStatusEnum accessStatus;
}
