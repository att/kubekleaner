package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ServicePortSpec {
	private String name;
	private Integer port;
	private String protocol;
	private Integer targetPort;
	private Integer nodePort;
	
	public ServicePortSpec(){
		/* needed for implementation */
	}
	
	public ServicePortSpec(String name, Integer port, String protocol, Integer targetPort, Integer nodePort){
		this.name = name;
		this.port = port;
		this.protocol = protocol;
		this.targetPort = targetPort;
		this.nodePort = nodePort;
	}
	
	public ServicePortSpec export(){
		this.setNodePort(null);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ServicePortSpec that = (ServicePortSpec) o;

		return name != null ? name.equals(that.name) : port == that.port;
	}

	@Override
	public int hashCode() {
		int result = 8;
		result = 37 * result + name.hashCode();	// NOSONAR needed for hash
		result = 37 * result + (int) port;		// NOSONAR needed for hash
		return port;
	}
}
