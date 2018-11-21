package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DeleteOptions {
	private String apiVersion;
	private String kind;
	private Integer gracePeriodSeconds;
	private Boolean orphanDependents;
	private String propagationPolicy;
	private Preconditions preconditions;
	
	public DeleteOptions(){
		this.apiVersion = "v1";
		this.kind = "DeleteOptions";
		this.propagationPolicy = "Foreground";
	}
}
