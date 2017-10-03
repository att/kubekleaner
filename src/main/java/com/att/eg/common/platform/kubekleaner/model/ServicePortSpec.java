/*

MIT License (MIT)

Copyright (c) 2017 AT&T Intellectual Property. All other rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a 
copy of this software and associated documentation files (the "Software"), 
to deal in the Software without restriction, including without limitation 
the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the 
Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included 
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
THE SOFTWARE.

*/

package com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public Integer getTargetPort() {
		return targetPort;
	}
	public void setTargetPort(Integer targetPort) {
		this.targetPort = targetPort;
	}

	@Override
	public String toString() {
		return "ServicePortSpec{" +
				"name='" + name + '\'' +
				", port=" + port +
				", protocol='" + protocol + '\'' +
				", targetPort=" + targetPort +
				", nodePort=" + nodePort +
				'}';
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
		result = 37 * result + name.hashCode();	
		result = 37 * result + (int) port;		
		return port;
	}

	public Integer getNodePort() {
		return nodePort;
	}
	public void setNodePort(Integer nodePort) {
		this.nodePort = nodePort;
	}
}

