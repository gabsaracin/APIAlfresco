package com.example.progettoApiAlfresco.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

    private String id;
    private String displayName;

    // Getters e Setters

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
