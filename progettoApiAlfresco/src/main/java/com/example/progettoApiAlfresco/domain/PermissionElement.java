package com.example.progettoApiAlfresco.domain;

import com.example.progettoApiAlfresco.enums.AccessStatusEnum;

import lombok.Getter;
import lombok.Setter;


@Getter 
@Setter
public class PermissionElement {

    private String authorityId;
    private String name;
    private AccessStatusEnum accessStatus = AccessStatusEnum.ALLOWED;

}
