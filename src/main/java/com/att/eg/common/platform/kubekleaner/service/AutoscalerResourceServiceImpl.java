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

import com.att.eg.common.platform.kubekleaner.model.AutoscalerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoscalerResourceServiceImpl implements AutoscalerResourceService {
	@Autowired
    KubernetesService kubernetesService;

	@Override
	public AutoscalerResource getAutoscaler(String targetCluster, String namespace, String name) {
		String path = "/apis/autoscaling/v1/namespaces/" + namespace + "/horizontalpodautoscalers/" + name;
		return kubernetesService.apiGetRequest(targetCluster, path, new AutoscalerResource());
	}
	
	@Override
	public boolean autoscalerExists(String targetCluster, String namespace, String name) {
		return getAutoscaler(targetCluster, namespace, name) != null;
	}

	@Override
	public void deleteAutoscaler(String targetCluster, String namespace, String name) {
		if(!autoscalerExists(targetCluster, namespace, name)){
			return;
		}
		String path = "/apis/autoscaling/v1/namespaces/" + namespace + "/horizontalpodautoscalers/" + name;
		kubernetesService.apiDeleteRequest(targetCluster, path, null);
	}
}

