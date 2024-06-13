package com.example.progettoApiAlfresco.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PermissionsInfo {

    private boolean isInheritanceEnabled;
    private Set<PermissionElement> inherited;
    private Set<PermissionElement> locallySet;
    private Set<String> settable;

}
