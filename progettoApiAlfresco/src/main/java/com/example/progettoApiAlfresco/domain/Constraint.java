package com.example.progettoApiAlfresco.domain;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class Constraint {

    private String id;
    private String type;
    private String title;
    private String description;
    
    @JsonProperty("parameters")
    private Map<String, Parameter> parameters;

}
