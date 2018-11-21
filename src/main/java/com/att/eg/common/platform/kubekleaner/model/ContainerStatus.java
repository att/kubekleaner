package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ContainerStatus {
	String name;
	ContainerState state;
	ContainerState lastState;
	Boolean ready;
	Integer restartCount;
	String image;
	String imageID;
	String containerID;
}
