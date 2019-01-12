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

import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Component
public class HipchatServiceImpl implements HipchatService {
    
    /* You will need to have access to your HipChat instance as well as a valid authentication token */
    private String hipchatUrl = "https://hipchat.url.here.com/v1/rooms/message?auth_token=auth.token.here" +
            "format=json&room_id=room.id.here&from=KubeKleaner&message_format=text";

    private Client webClient = ClientBuilder.newClient();

    @Override
    public void notifyDelete(String targetCluster, String namespace, String deploymentName) {
        
	/* Utilizes custom (k8s) and (kleaner) emoticons. Either add those to HipChat or tailor this message accordingly. */
        String hipchatMessage = "&color=purple&message=%28k8s%29%20%28kleaner%29%20Deployment%2C%20replica%20set%2C" +
                "%20pod%2C%20service%2C%20ingress%2C%20and%20horizontal%20pod%20autoscaler%20resources%20deleted%21%20%7C%20" +
                "SERVICE%20NAME%20=%20" +
                deploymentName + "%20%7C%20NAMESPACE%20=%20" + namespace + "%20%7C%20CLUSTER%20=%20" +
                targetCluster + "%20";
        webClient.target(hipchatUrl + hipchatMessage).request().get();
    }

}
