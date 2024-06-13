package com.example.progettoApiAlfresco.domain;

import java.sql.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter; 


@Getter
@Setter
public class Node {

    @NonNull
    private String id;

    @NotBlank
    @Pattern(regexp = "^(?!(.*[\"*\\\\><?/:|]+.*)|(.*[.]?.*[.]+$)|(.*[ ]+$))", message = "Invalid name format.")
    private String name;

    @NotBlank
    private String nodeType;

    @JsonProperty("isFolder")
    private boolean isFolder;

    @JsonProperty("isFile")
    private boolean isFile;

    private boolean isLocked = false;

    private Date modifiedAt;

    private UserInfo modifiedByUser;
    
    private Date createdAt;
    
    private UserInfo createdByUser;

    private String parentId;
    private boolean isLink;
    private boolean isFavorite;
    private boolean isDirectLinkEnabled;
    private ContentInfo content;

    private Set<String> aspectNames;
    private JsonNode properties;
    private Set<String> allowableOperations;

    private PathInfo path;

    private PermissionsInfo permissions;

    private Definition definition;
       
}
