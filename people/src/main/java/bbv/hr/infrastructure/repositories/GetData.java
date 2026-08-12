package bbv.hr.infrastructure.repositories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Utility component to load JSON mock data exclusively from infrastructure/data directory.
 */
@Component
public class GetData {

    public static final String EMPLOYEE_DIRECTORY_FILE = "employee_directory_mockdata.json";
    public static final String ORGANIZATION_FILE = "organization_mockdata.json";
    public static final String REQUEST_MANAGEMENT_FILE = "request_management_mockdata.json";

    private final ObjectMapper objectMapper;

    public GetData() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Load JSON root node from infrastructure/data directory.
     */
    public JsonNode loadJsonNode(String fileName) {
        try {
            String classPathLocation = "bbv/hr/infrastructure/data/" + fileName;
            ClassPathResource resource = new ClassPathResource(classPathLocation);
            if (resource.exists()) {
                try (InputStream is = resource.getInputStream()) {
                    return objectMapper.readTree(is);
                }
            }

            Path filePath = Paths.get("src/main/java/bbv/hr/infrastructure/data/" + fileName);
            if (Files.exists(filePath)) {
                try (InputStream is = Files.newInputStream(filePath)) {
                    return objectMapper.readTree(is);
                }
            }

            throw new IllegalStateException("File not found in infrastructure/data: " + fileName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON mock data file: " + fileName, e);
        }
    }

    /**
     * Parse specific array node key from JSON file into List of target type.
     */
    public <T> List<T> getModuleData(String fileName, String keyNode, Class<T> valueType) {
        try {
            JsonNode root = loadJsonNode(fileName);
            JsonNode arrayNode = root.get(keyNode);
            if (arrayNode == null || !arrayNode.isArray()) {
                return Collections.emptyList();
            }
            return objectMapper.convertValue(
                    arrayNode,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, valueType)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse key [" + keyNode + "] from file [" + fileName + "]", e);
        }
    }

    /**
     * Get Employee Directory JSON mock node.
     */
    public JsonNode getEmployeeDirectoryJson() {
        return loadJsonNode(EMPLOYEE_DIRECTORY_FILE);
    }

    /**
     * Get Employee Directory entity list by key node name.
     */
    public <T> List<T> getEmployeeDirectoryEntities(String keyNode, Class<T> valueType) {
        return getModuleData(EMPLOYEE_DIRECTORY_FILE, keyNode, valueType);
    }

    /**
     * Get Organization JSON mock node.
     */
    public JsonNode getOrganizationJson() {
        return loadJsonNode(ORGANIZATION_FILE);
    }

    /**
     * Get Organization entity list by key node name.
     */
    public <T> List<T> getOrganizationEntities(String keyNode, Class<T> valueType) {
        return getModuleData(ORGANIZATION_FILE, keyNode, valueType);
    }

    /**
     * Get Request Management JSON mock node.
     */
    public JsonNode getRequestManagementJson() {
        return loadJsonNode(REQUEST_MANAGEMENT_FILE);
    }

    /**
     * Get Request Management entity list by key node name.
     */
    public <T> List<T> getRequestManagementEntities(String keyNode, Class<T> valueType) {
        return getModuleData(REQUEST_MANAGEMENT_FILE, keyNode, valueType);
    }
}
