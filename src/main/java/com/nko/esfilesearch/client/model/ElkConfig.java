package com.nko.esfilesearch.client.model;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author : Nko
 * @Date: 2018年12月26日14:54:50
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "elkconfig")
@Data
public class ElkConfig {

    private String alias;
    private int shards;
    private int replicas;
    private int maxWindow;
    private long scroll;
    private String poolSize;
    @NestedConfigurationProperty
    private List<Hosts> hosts;

}
