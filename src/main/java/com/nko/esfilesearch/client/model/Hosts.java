package com.nko.esfilesearch.client.model;

import lombok.Data;

/**
 * @ProjectName: elkapi
 * @Package: com.nko.elk.api.elasticsearch.config.model
 * @ClassName: Hosts
 * @Author: sun71
 * @Description:
 * @Date: 2019/8/1 16:50
 * @Version: 1.0
 */
@Data
public class Hosts {
    private String ip;
    private int port;
    private String schema;
}
