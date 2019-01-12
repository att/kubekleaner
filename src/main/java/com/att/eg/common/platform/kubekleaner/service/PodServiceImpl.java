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

import com.att.eg.common.platform.kubekleaner.model.PodList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Map;

@Service
public class PodServiceImpl implements PodService {

	@Autowired
    KubernetesService kubernetesService;

	@Override
	public PodList getPodsByLabels(String targetCluster, String namespace, MultivaluedMap<String, String> selector) {
		String path = "/api/v1/namespaces/" + namespace + "/pods?labelSelector=";
		for(String label : selector.keySet()){		// NOSONAR need to iterate through keySet
			path += label + "=" +selector.get(label).get(0) + ",";
		}
		path = path.substring(0, path.length() - 1);
		return kubernetesService.apiGetRequest(targetCluster, path, new PodList());
	}
}
