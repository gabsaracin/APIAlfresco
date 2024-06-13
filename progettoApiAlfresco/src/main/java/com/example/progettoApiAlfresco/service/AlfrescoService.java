package com.example.progettoApiAlfresco.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.progettoApiAlfresco.controller.dto.NodeBodyMoveDTO;
import com.example.progettoApiAlfresco.controller.dto.NodeDTO;
import com.example.progettoApiAlfresco.domain.NodeEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class AlfrescoService {

        @Value("${alfresco.api.url}")
        private String alfrescoApiUrl;

        @Value("${alfresco.username}")
        private String username;

        @Value("${alfresco.password}")
        private String password;

        private RestTemplate restTemplate = new RestTemplate();

        public void AlfrescoService(RestTemplate restTemplate) {
                this.restTemplate = restTemplate;
        }

        public List<NodeEntry> getFiles(String nodeId) throws JsonMappingException, JsonProcessingException {
                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId, "children")
                                .toUriString();
            
                HttpHeaders headers = new HttpHeaders();
                headers.setBasicAuth(username, password);
                HttpEntity<String> entity = new HttpEntity<>(headers);
            
                ResponseEntity<ObjectNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, ObjectNode.class);
            
                JsonNode body = response.getBody();
               
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode filteredBody = new ObjectMapper().createObjectNode();
                filteredBody.putObject("list");
            
                JsonNode listNode = body.get("list");
                JsonNode entries = listNode.get("entries");

                String entry = mapper.writeValueAsString(entries);
                NodeEntry[] allNodes = new ObjectMapper().readValue(entry, NodeEntry[].class);
                List<NodeEntry> fileNodes = new ArrayList<>();
                for(NodeEntry node:allNodes){
                        if (node != null && "cm:content".equals(node.getEntry().getNodeType())) {
                                ((ObjectNode) filteredBody.get("list")).withArray("entries");
                                fileNodes.add(node);
                            }
                }
                
            
                return fileNodes;
            }


        public NodeEntry getFile(String nodeId, String relativePath) throws IOException {
                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId)
                                .queryParam("relativePath", relativePath)
                                .buildAndExpand(relativePath)
                                .toUriString();

                HttpHeaders headers = new HttpHeaders();

                // Map<String, String> params = new HashMap<String, String>();
                // params.put("relativePath", relativePath);
                // params.put("nodeId", nodeId);
                headers.setBasicAuth(username, password);
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<NodeEntry> response = restTemplate.exchange(url, HttpMethod.GET, entity, NodeEntry.class);
            
                return response.getBody();
        }
 
        public NodeEntry createDirectory(String nodeId, Boolean autoRename, boolean majorVersion,
                        boolean versioningEnabled, NodeDTO dto)
                        throws JsonMappingException, JsonProcessingException {
                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId, "children")
                                .queryParam("autoRename", autoRename)
                                .queryParam("majorVersion", majorVersion)
                                .queryParam("versioningEnabled", versioningEnabled)
                                .toUriString();
                ObjectMapper mapper = new ObjectMapper();
                HttpHeaders headers = new HttpHeaders();
                Map<String, String> params = new HashMap<String, String>();
                params.put("nodeId", nodeId);
                String jsonString = mapper.writeValueAsString(dto);
                headers.setBasicAuth(username, password);
                HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

                ResponseEntity<NodeEntry> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                NodeEntry.class,
                                params);

                return response.getBody();
        }

        public ResponseEntity<Void> deleteFile(String nodeId, boolean permanent) {

                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId)
                                .queryParam("permanent", permanent)
                                .toUriString();

                HttpHeaders headers = new HttpHeaders();

                Map<String, String> params = new HashMap<String, String>();
                params.put("nodeId", nodeId);
                headers.setBasicAuth(username, password);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<NodeEntry> response = restTemplate.exchange(url, HttpMethod.DELETE, entity,
                NodeEntry.class,
                                params);

                // response.getBody();
                return new ResponseEntity<Void>(response.getStatusCode());
        }

        public NodeEntry update(String nodeId, boolean majorVersion, String comment, String name, String content)
                        throws JsonProcessingException {
                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId, "content")
                                .queryParam("majorVersion", majorVersion)
                                .queryParam("comment", comment)
                                .queryParamIfPresent("name", Optional.ofNullable(name))
                                .buildAndExpand(name)
                                .toUriString();
               
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/octet-stream");
                Map<String, String> params = new HashMap<String, String>();
                byte[] bytesString = Base64.getDecoder().decode(content);
                params.put("nodeId", nodeId);
                headers.setBasicAuth(username, password);
                HttpEntity<byte[]> entity = new HttpEntity<>(bytesString,headers);

                ResponseEntity<NodeEntry> response = restTemplate.exchange(url, HttpMethod.PUT, entity,
                NodeEntry.class,
                                params);
                // List<DocFile> docFiles = mapper.readValue(response.getBody().toString(),
                // mapper.getTypeFactory().constructCollectionType(List.class, DocFile.class));
                return response.getBody();
        }

        public NodeEntry move(String nodeId, NodeBodyMoveDTO body) throws JsonProcessingException {

                String url = UriComponentsBuilder.fromHttpUrl(alfrescoApiUrl)
                                .pathSegment("nodes", nodeId, "move")
                                .toUriString();

                HttpHeaders headers = new HttpHeaders();

                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(body);
                Map<String, String> params = new HashMap<String, String>();
                
                params.put("nodeId", nodeId);
                headers.setBasicAuth(username, password);
                HttpEntity<String> entity = new HttpEntity<>(jsonString,headers);

                ResponseEntity<NodeEntry> response = restTemplate.exchange(url, HttpMethod.POST, entity,
                NodeEntry.class,
                                params);

                return response.getBody();

        }

}
