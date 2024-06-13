package com.example.progettoApiAlfresco.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class Property {

private String id;            

private String title;

private String description;

private String defaultValue;

private String dataType;

private boolean isMultiValued;

private boolean isMandatory;

private boolean isMandatoryEnforced;

private boolean isProtected;

private Set<Constraint> constraints;
}
