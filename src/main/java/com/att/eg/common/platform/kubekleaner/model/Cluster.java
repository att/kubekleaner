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
public class Cluster {
    private String name;
    private String title;
    private String url;
    private String version;
    private String auth;
    private Boolean enabled;
    private Boolean visible;
    private Boolean editable;
    private Boolean labPromotable;
    private Boolean prodPromotable;
    private Boolean releaseTarget;
    private String serviceApi;
    private String deploymentApi;
    private String configMapApi;
    private String globalConfigMapNamepspace;
    private String globalConfigMapName;

    public Cluster(){/* needed for implementation */}

    public Cluster(String name,
                   String title,
                   Boolean labPromotable,
                   Boolean prodPromotable,
                   Boolean releaseTarget,
                   Boolean editable){
        this.name = name;
        this.title = title;
        this.labPromotable = labPromotable;
        this.prodPromotable = prodPromotable;
        this.releaseTarget = releaseTarget;
        this.editable = editable;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getAuth() {
        return auth;
    }
    public void setAuth(String auth) {
        this.auth = auth;
    }
    public String getServiceApi() {
        return serviceApi;
    }
    public void setServiceApi(String serviceApi) {
        this.serviceApi = serviceApi;
    }
    public String getDeploymentApi() {
        return deploymentApi;
    }
    public void setDeploymentApi(String deploymentApi) {
        this.deploymentApi = deploymentApi;
    }
    public String getConfigMapApi() {
        return configMapApi;
    }
    public void setConfigMapApi(String configMapApi) {
        this.configMapApi = configMapApi;
    }
    public String getGlobalConfigMapNamepspace() {
        return globalConfigMapNamepspace;
    }
    public void setGlobalConfigMapNamepspace(String globalConfigMapNamepspace) {
        this.globalConfigMapNamepspace = globalConfigMapNamepspace;
    }
    public String getGlobalConfigMapName() {
        return globalConfigMapName;
    }
    public void setGlobalConfigMapName(String globalConfigMapName) {
        this.globalConfigMapName = globalConfigMapName;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public Boolean getVisible() {
        return visible;
    }
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    public Boolean getProdPromotable() {
        return prodPromotable;
    }
    public void setProdPromotable(Boolean prodPromotable) {
        this.prodPromotable = prodPromotable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getLabPromotable() {
        return labPromotable;
    }

    public void setLabPromotable(Boolean labPromotable) {
        this.labPromotable = labPromotable;
    }

    public Boolean getReleaseTarget() {
        return releaseTarget;
    }

    public void setReleaseTarget(Boolean releaseTarget) {
        this.releaseTarget = releaseTarget;
    }
}


