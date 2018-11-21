package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OwnerReference {
	private String apiVersion;
	private String kind;
	private String name;
	private String uid;
	private Boolean controller;
	private Boolean blockOwnerDeletion;
}
