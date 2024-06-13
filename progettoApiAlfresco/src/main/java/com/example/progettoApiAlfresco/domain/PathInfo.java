package com.example.progettoApiAlfresco.domain;


import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PathInfo {
  
    private PathElement element;

    private String name;

    private Boolean isComplete;
}
