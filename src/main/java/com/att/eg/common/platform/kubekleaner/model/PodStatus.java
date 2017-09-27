package com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public PodCondition[] getConditions() {
		return conditions;
	}

	public void setConditions(PodCondition[] conditions) {
		this.conditions = conditions;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public String getPodIP() {
		return podIP;
	}

	public void setPodIP(String podIP) {
		this.podIP = podIP;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public ContainerStatus[] getInitContainerStatuses() {
		return initContainerStatuses;
	}

	public void setInitContainerStatuses(ContainerStatus[] initContainerStatuses) {
		this.initContainerStatuses = initContainerStatuses;
	}

	public ContainerStatus[] getContainerStatuses() {
		return containerStatuses;
	}

	public void setContainerStatuses(ContainerStatus[] containerStatuses) {
		this.containerStatuses = containerStatuses;
	}

	public String getQosClass() {
		return qosClass;
	}

	public void setQosClass(String qosClass) {
		this.qosClass = qosClass;
	}
}
