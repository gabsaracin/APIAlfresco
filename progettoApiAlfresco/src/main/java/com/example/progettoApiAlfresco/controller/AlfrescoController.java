package com.example.progettoApiAlfresco.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.progettoApiAlfresco.controller.dto.NodeBodyMoveDTO;
import com.example.progettoApiAlfresco.controller.dto.NodeDTO;
import com.example.progettoApiAlfresco.domain.NodeEntry;
import com.example.progettoApiAlfresco.service.AlfrescoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api")
public class AlfrescoController {

        @Autowired
        private AlfrescoService alfrescoService;

        @GetMapping("/getfiles/{nodeId}")
        public List<NodeEntry> getFiles(
                        @Parameter(name = "nodeId", description = "The identifier of a node. You can also use one of these well-known aliases:\n\n"
                                        +
                                        "- **-my-**\n" +
                                        "- **-shared-**\n" +
                                        "- **-root-**") String nodeId)
                        throws JsonMappingException, JsonProcessingException {
                return alfrescoService.getFiles(nodeId);
        }

        @GetMapping("/get/{nodeId}")
        public NodeEntry getFile(
                        @Parameter(name = "nodeId", description = "The identifier of a node. You can also use one of these well-known aliases:\n\n"
                                        +
                                        "- **-my-**\n" +
                                        "- **-shared-**\n" +
                                        "- **-root-**") String nodeId,
                        @RequestParam(required = false) @Parameter(name = "relativePath", description = "A path relative to the nodeId. If you set this,\n\n"
                                        +
                                        "information is returned on the node resolved by this path.") String relativePath)
                        throws IOException {
                return alfrescoService.getFile(nodeId, relativePath);
        }

        @PostMapping(value = "/create")
        public NodeEntry CreateDirectory(
                        @RequestParam(name = "nodeId", required = true) @Parameter(name = "nodeId", description = "The identifier of a node. You can also use one of these well-known aliases:\n\n"
                                        +
                                        "- **-my-**\n" +
                                        "- **-shared-**\n" +
                                        "- **-root-**") String nodeId,
                        @RequestParam(name = "autoRename", required = false) @Parameter(name = "autoRename", description = "If true, then a name clash will cause an attempt to auto rename by finding a unique name using an integer suffix.") Boolean autoRename,
                        @RequestParam(name = "majorVersion", required = false) @Parameter(name = "majorVersion", description = "If true, then created node will be version 1.0 MAJOR. If false, then created node will be version 0.1 MINOR.") boolean majorVersion,
                        @RequestParam(name = "versioningEnabled", required = false) @Parameter(name = "versioningEnabled", description = "If true, then created node will be versioned. If false, then created node will be unversioned and auto-versioning disabled.") boolean versioningEnabled,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "{\n  \"name\":\"My Folder\", \"nodeType\":\"cm:folder\" \n}") @RequestBody NodeDTO dto)
                        throws JsonMappingException, JsonProcessingException {
                return alfrescoService.createDirectory(nodeId, autoRename, majorVersion, versioningEnabled, dto);
        }

        @PostMapping(value = "/move")
        public NodeEntry move(
                        @Parameter(name = "nodeId", description = "The identifier of a node.") String nodeId,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "{\n" +
                                        "  \"targetParentId\":\"string\",\n" +
                                        "  \"name\":\"name\"\n" +
                                        "}") @RequestBody NodeBodyMoveDTO body)
                        throws JsonProcessingException {
                return alfrescoService.move(nodeId, body);
        }

        @DeleteMapping(value = "delete")
        public ResponseEntity<Void> deleteFile(
                        @Parameter(name = "nodeId", description = "The identifier of a node.") String nodeId,
                        @RequestParam(required = false) @Parameter(name = "permanent", description = "If true then the node is deleted permanently, without moving to the trashcan.\r\n"
                                        + //
                                        "Only the owner of the node or an admin can permanently delete the node ") Boolean permanent) {
                return alfrescoService.deleteFile(nodeId, permanent);
        }

        @PutMapping(value = "update")
        public NodeEntry update(@Parameter(name = "nodeId", description = "The identifier of a node.") String nodeId,
                        @RequestParam(required = false) @Parameter(name = "majorVersion", description = "If true, then created node will be version 1.0 MAJOR. If false, then created node will be version 0.1 MINOR.") boolean majorVersion,
                        @RequestParam(required = false) @Parameter(name = "comment", description = "Add a version comment which will appear in version history.\r\n"
                                        + //
                                        "Setting this parameter also enables versioning of this node, if it is not already versioned.") String comment,
                        @RequestParam(required = false) @Parameter(name = "name", description = "Optional new name. This should include the file extension.\r\n"
                                        +
                                        "The name must not contain spaces or the following special characters: * \" < > \\ / ? : and |.\r\n"
                                        +
                                        "The character . must not be used at the end of the name.") String name,
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The binary content") @RequestBody String content)
                        throws JsonProcessingException {
                return alfrescoService.update(nodeId, majorVersion, comment, name, content);
        }
}
