package main.java.com.att.eg.common.platform.kubekleaner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
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
}

