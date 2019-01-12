/*

MIT License (MIT)

Copyright (c) 2017-2019 AT&T Intellectual Property. All other rights reserved.

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


package com.att.eg.common.platform.kubekleaner.service;

import com.att.eg.common.platform.kubekleaner.model.DeploymentResource;
import com.att.eg.common.platform.kubekleaner.model.LabelPatch;
import com.att.eg.common.platform.kubekleaner.model.PodResource;
import com.att.eg.common.platform.kubekleaner.model.ResourceList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeploymentResourceServiceImpl implements DeploymentResourceService {
    @Autowired
    KubernetesService kubernetesService;

    String v1beta1namespacesPath = "/apis/extensions/v1beta1/namespaces/";
    String deploymentsPath = "/deployments/";

    @Override
    public boolean deploymentExists(String targetCluster, String namespace, String serviceName) {
        return getDeploymentResource(targetCluster, namespace, serviceName) != null;
    }

    @Override
    public ResourceList<DeploymentResource> getAllDeployments(String targetCluster) {
        if (targetCluster == null) {
            throw new IllegalArgumentException("You must specify a target cluster.");
        }
        String path = "/apis/extensions/v1beta1/deployments";
        return kubernetesService.apiGetRequest(targetCluster, path, new ResourceList<DeploymentResource>());
    }

    @Override
    public DeploymentResource getDeploymentResource(String targetCluster, String namespace, String deploymentName){
        String path = v1beta1namespacesPath + namespace + deploymentsPath + deploymentName;
        return kubernetesService.apiGetRequest(targetCluster, path, new DeploymentResource());
    }

    @Override
    public void deleteDeploymentResource(String targetCluster, String namespace, String name) {
        if(!deploymentExists(targetCluster, namespace, name)){
            return;
        }
        String path = v1beta1namespacesPath + namespace + deploymentsPath + name;
        kubernetesService.apiDeleteRequest(targetCluster, path, null);
    }

    @Override
    public ResourceList<PodResource> getAllPods(String targetCluster) {
        if (targetCluster == null) {
            throw new IllegalArgumentException("You must specify a target cluster.");
        }
        String path = "/api/v1/pods";
        return kubernetesService.apiGetRequest(targetCluster, path, new ResourceList<PodResource>());
    }

    @Override
    public List<LabelPatch> addLabel(String targetCluster, String namespace, String deploymentName){
        DateTime currentTime = new DateTime();

        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH.mm.ss'Z'");

        String currentTimeString = format.print(currentTime);

        LabelPatch labelPatch = new LabelPatch();

        labelPatch.setOp("add");
        labelPatch.setPath("/metadata/labels/crashLoopDetectionTime");
        labelPatch.setValue(currentTimeString);

        List<LabelPatch> labelPatches = new ArrayList<>();
        labelPatches.add(labelPatch);

        String path = v1beta1namespacesPath + namespace + deploymentsPath + deploymentName;

        return kubernetesService.apiPatchRequest(targetCluster, path, labelPatches);
    }

    @Override public List<LabelPatch> removeLabel(String targetCluster, String namespace, String deploymentName) {
        LabelPatch labelPatch = new LabelPatch();

        labelPatch.setOp("remove");
        labelPatch.setPath("/metadata/labels/crashLoopDetectionTime");

        List<LabelPatch> labelPatches = new ArrayList<>();
        labelPatches.add(labelPatch);

        String path = v1beta1namespacesPath + namespace + deploymentsPath + deploymentName;

        return kubernetesService.apiPatchRequest(targetCluster, path, labelPatches);
    }
}
