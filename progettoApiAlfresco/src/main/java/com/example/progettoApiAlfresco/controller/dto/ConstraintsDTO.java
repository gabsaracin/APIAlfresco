package com.example.progettoApiAlfresco.controller.dto;

import com.jayway.jsonpath.internal.filter.ValueNodes.JsonNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstraintsDTO {

    private String id;

    private String type;

    private String title;

    private String description;

    private JsonNode parameters;
}
