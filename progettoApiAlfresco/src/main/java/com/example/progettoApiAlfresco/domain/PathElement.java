package com.example.progettoApiAlfresco.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PathElement {

    private String id;
    private String name;
    private String nodeType;
    private Set<String> aspectName;
}
