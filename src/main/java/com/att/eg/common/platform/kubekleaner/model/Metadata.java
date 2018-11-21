package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Metadata {
    private String name;
    private String namespace;
    private Map<String, String> labels;
    private Map<String, String> annotations;
    private String resourceVersion;
    private String creationTimestamp;
    private List<OwnerReference> ownerReferences;
    private String generateName;

    public Metadata export(){
        this.setResourceVersion(null);
        return this;
    }
}
