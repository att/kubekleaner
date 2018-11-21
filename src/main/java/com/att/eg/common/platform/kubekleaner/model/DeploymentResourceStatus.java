package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeploymentResourceStatus {
	private Integer observedGeneration;
	private Integer replicas;
	private Integer updatedReplicas;
	private Integer readyReplicas;
	private Integer availableReplicas;
	private Integer unavailableReplicas;
}
