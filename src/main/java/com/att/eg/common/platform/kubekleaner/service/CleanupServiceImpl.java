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
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
THE SOFTWARE.

*/

package com.att.eg.common.platform.kubekleaner.service;

import com.att.eg.common.platform.kubekleaner.KubernetesConfig;

import com.att.eg.common.platform.kubekleaner.model.DeploymentResource;
import com.att.eg.common.platform.kubekleaner.model.PodList;
import com.att.eg.common.platform.kubekleaner.model.PodResource;
import com.att.eg.common.platform.kubekleaner.model.ResourceList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
@ConfigurationProperties(prefix="kubernetes")
public class CleanupServiceImpl implements CleanupService {
	@Autowired
	KubernetesConfig kubernetesConfig;

	@Autowired
	private DeploymentResourceService deploymentResourceService;

	@Autowired
	private KubernetesService kubernetesService;

	@Autowired
	private ReplicasetResourceService replicasetResourceService;

	@Autowired
	private PodResourceService podResourceService;

	@Autowired
	private PodService podService;

	@Autowired
	private HipchatService hipchatService;

	@Autowired
	private ServiceResourceService serviceResourceService;

	@Autowired
	private IngressResourceService ingressResourceService;

	@Autowired
	private AutoscalerResourceService autoscalerResourceService;

	@Autowired
	ObjectMapper mapper;

	private String crashLoopDetectionTimeLiteral = "crashLoopDetectionTime";
	private String namespaceLiteral = "NAMESPACE";
	private String clusterLiteral = "CLUSTER";
	private Boolean alreadyRemovedLabelFromDeployment = false;
	
	public CleanupServiceImpl() {
		// needed for instantiation
	}

	public void addLabel(PodResource pod, DeploymentResource deployment, String cluster, String namespace,
						 String name) {
		if (pod.getStatus().getContainerStatuses() != null && !"default".equals(namespace)
				&& !"kube-system".equals(namespace) && pod.getStatus().getContainerStatuses()[0]
				.getState().getWaiting() != null) {
			if (deployment.getMetadata().getLabels().get(crashLoopDetectionTimeLiteral) == null) {	// NOSONAR need 4 conditionals
				deploymentResourceService.addLabel(cluster, namespace, name);
			}
		}
	}

	public void removeLabel(PodResource pod, DeploymentResource deployment, String cluster, String namespace,
									 String name) {
		if (pod.getStatus().getContainerStatuses() != null && pod.getStatus().getContainerStatuses()[0].getState()
				.getRunning() != null && deployment.getMetadata().getLabels()
				.get(crashLoopDetectionTimeLiteral) != null) {
			deploymentResourceService.removeLabel(cluster, namespace, name);
			alreadyRemovedLabelFromDeployment = true;
		}
	}

	public void removeResources(PodResource pod, DeploymentResource deployment, String cluster, String namespace,
								String name) {
		if (pod.getStatus().getContainerStatuses() != null && pod.getStatus().getContainerStatuses()[0]
				.getState().getWaiting() != null && deployment.getMetadata().getLabels()
				.get(crashLoopDetectionTimeLiteral) != null) {
			DateTime currentTime = new DateTime();
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH.mm.ss'Z'");
			DateTime timeCutoff = currentTime.minusWeeks(kubernetesConfig.getNumWeeks());
			DateTime crashLoopTime = format.parseDateTime(deployment.getMetadata().getLabels()
					.get(crashLoopDetectionTimeLiteral)).toDateTime();
			if (timeCutoff.isAfter(crashLoopTime)) {
				deploymentResourceService.deleteDeploymentResource(cluster, namespace, name);
				replicasetResourceService.deleteReplicasetResource(cluster, namespace, name);
				podResourceService.deletePodResource(cluster, namespace, name);
				serviceResourceService.deleteService(cluster, namespace, name);
				ingressResourceService.deleteIngressResource(cluster, namespace, name);
				autoscalerResourceService.deleteAutoscaler(cluster, namespace, name);
				hipchatService.notifyDelete(cluster, namespace, name);
			}
		}
	}

	public void iteratePods(PodList podList, DeploymentResource deployment, String cluster, String namespace, String name) {
		for (PodResource pod : podList.getItems()) {
			// If a pod has the label but is now running, remove the label
			if (!alreadyRemovedLabelFromDeployment) {
				removeLabel(pod, deployment, cluster, namespace, name);
			}

			// Add a label to a waiting pod that does not already have the label
			addLabel(pod, deployment, cluster, namespace, name);

			//The pod has a label and is older than the threshold, so remove the Kubernetes resources
			removeResources(pod, deployment, cluster, namespace, name);
		}
		alreadyRemovedLabelFromDeployment = false;
	}

	@Scheduled(cron = "0 0 17 * * ?")	// second, minute, hour, day of month, month, day(s) of week (UTC on K8s)
	public void cleanDeployments() {
		List<String> clusters = new ArrayList<>();

		// Add clusters that need to be cleaned
		clusters.add("sandbox-cluster");
		clusters.add("dev-int-cluster");

		for (int i = 0; i < clusters.size(); i++) {
			String cluster = clusters.get(i);

			ResourceList<DeploymentResource> deploymentResourceList = deploymentResourceService.getAllDeployments(cluster);

			List<DeploymentResource> deployments = mapper.convertValue(deploymentResourceList.getItems(),
					new TypeReference<List<DeploymentResource>>() {});

			for (DeploymentResource deployment : deployments) {
				Map<String, String> matchLabels = deployment.getSpec().getSelector().getMatchLabels();
				MultivaluedMap<String, String> selector = new MultivaluedHashMap<>();

				String namespace = deployment.getMetadata().getNamespace();
				String name = deployment.getMetadata().getName();

				for (Entry<String, String> entry : matchLabels.entrySet()) {
					selector.add(entry.getKey(), entry.getValue());
				}

				PodList podList = podService.getPodsByLabels(cluster, namespace, selector);

				iteratePods(podList, deployment, cluster, namespace, name);
			}
		}
	}
}
