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

import com.att.eg.common.platform.kubekleaner.model.Cluster;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.ws.rs.BadRequestException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;

@Service
@EnableConfigurationProperties
@ConfigurationProperties
public class KubernetesServiceImpl implements KubernetesService {

    private RestTemplate restTemplate;

    @Autowired Kubernetes kubernetes;
    @Autowired ObjectMapper objectMapper;

    String clusterString = "Cluster ";

    @Component
    @ConfigurationProperties(prefix="kubernetes")
    public static class Kubernetes {
        private Map<String, Object> clusters;
        private boolean interceptorEnabled;
        public Map<String, Object> getClusters() {
            return clusters;
        }

        public void setClusters(Map<String, Object> clusters) {
            this.clusters = clusters;
        }

        public boolean isInterceptorEnabled() {
            return interceptorEnabled;
        }

        public void setInterceptorEnabled(boolean interceptorEnabled) {
            this.interceptorEnabled = interceptorEnabled;
        }
    }

    @PostConstruct
    public void init() {
        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslContext;
            sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            restTemplate = new RestTemplate(requestFactory);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T apiGetRequest(String targetCluster, String path, T entity){
        return executeApiRequest(targetCluster, path, HttpMethod.GET, entity, false);
    }
    @Override
    public <T> T apiPutRequest(String targetCluster, String path, T entity){
        return executeApiRequest(targetCluster, path, HttpMethod.PUT, entity, false);
    }
    @Override
    public <T> T apiPostRequest(String targetCluster, String path, T entity){
        return executeApiRequest(targetCluster, path, HttpMethod.POST, entity, false);
    }
    @Override
    public <T> T apiDeleteRequest(String targetCluster, String path, T entity){
        return executeApiRequest(targetCluster, path, HttpMethod.DELETE, entity, false);
    }
    @Override
    public <T> T apiPatchRequest(String targetCluster, String path, T entity){
        return executeApiRequest(targetCluster, path, HttpMethod.PATCH, entity, true);
    }

    private <T> T executeApiRequest(String targetCluster, String path, HttpMethod method, T entity, Boolean patch){
        // Could not get the application.yml map of clusters to map to map of Cluster objects
        // For now leaving as map of Objects and converting the lookup to Cluster object
        Cluster cluster = null;
        try {
            cluster = objectMapper.convertValue(kubernetes.getClusters().get(targetCluster), Cluster.class);
        } catch (NullPointerException e){
            throw new BadRequestException(clusterString + targetCluster + " does not exist");
        }
        if(!cluster.getEnabled()){
            throw new BadRequestException(clusterString + targetCluster + " is not enabled");
        }
        if(method != HttpMethod.GET && !cluster.getEditable()){
            throw new BadRequestException(clusterString + targetCluster + " is not editable");
        }
        String url = cluster.getUrl() + path;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", cluster.getAuth());
        if (!patch) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        if (patch) {
            headers.add("Content-Type", "application/json-patch+json");
        }
        HttpEntity<T> httpEntity = new HttpEntity<>(entity, headers);
        ResponseEntity<T> response = null;
        try {
            response = restTemplate.exchange(url, method, httpEntity, new ParameterizedTypeReference<T>() {});
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            } else {
                throw e;
            }
        }

        if(entity != null){
            if (!patch) {
                return (T) objectMapper.convertValue(response.getBody(), entity.getClass());
            }
            return entity;
        } else {
            return null;
        }
    }
}

