package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeploymentResource {
    private String apiVersion;
    private String kind;
    private Metadata metadata;
    private DeploymentSpec spec;
    private DeploymentResourceStatus status;

    public DeploymentResource export(){
        if(this.getMetadata() != null){
            this.setMetadata(metadata.export());
        }
        return this;
    }
}


