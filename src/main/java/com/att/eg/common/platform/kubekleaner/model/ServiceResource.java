package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ServiceResource {
	private String kind;
	private String apiVersion;
	private Metadata metadata;
	private ServiceSpec spec;
	
	public ServiceResource(){
		this.metadata = new Metadata();
		this.spec = new ServiceSpec();
	}
	
	public ServiceResource export(){
		if(this.getMetadata() != null){
			this.setMetadata(metadata.export());
		}
		if(this.getSpec() != null){
			this.setSpec(spec.export());
		}
		return this;
	}
}
