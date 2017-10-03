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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
	public List<ServicePortSpec> getPorts() {
		return ports;
	}
	public void setPorts(List<ServicePortSpec> ports) {
		this.ports = ports;
	}
	public Map<String, String> getSelector() {
		return selector;
	}
	public void setSelector(Map<String, String> selector) {
		this.selector = selector;
	}
	public String getSessionAffinity() {
		return sessionAffinity;
	}
	public void setSessionAffinity(String sessionAffinity) {
		this.sessionAffinity = sessionAffinity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ServiceSpec [clusterIP=" + clusterIP + ", ports=" + ports + ", selector=" + selector
				+ ", sessionAffinity=" + sessionAffinity + ", type=" + type + "]";
	}
}

