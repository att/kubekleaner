package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PodStatus {
	String phase;
	PodCondition[] conditions;
	String message;
	String reason;
	String hostIP;
	String podIP;
	String startTime;
	ContainerStatus[] initContainerStatuses;
	ContainerStatus[] containerStatuses;
	String qosClass;
}