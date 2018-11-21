package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ServiceSpec {
	private String clusterIP;
	private List<ServicePortSpec> ports;
	private Map<String, String> selector;
	private String sessionAffinity;
	private String type;
	
	public ServiceSpec(){
		this.ports = new ArrayList<>();
		this.selector = new HashMap<>();
	}

	public String getClusterIP() {
		return clusterIP;
	}
	public void setClusterIP(String clusterIP) {
		this.clusterIP = clusterIP;
	}
	
	public ServiceSpec export(){
		if(this.getClusterIP() != null){
			this.setClusterIP(null);
		}
		for(ServicePortSpec portItem : ports){
			portItem.export();
		}
		return this;
	}
}
