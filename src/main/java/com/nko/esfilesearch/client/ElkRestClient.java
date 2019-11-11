package com.nko.esfilesearch.client;


import com.nko.esfilesearch.client.model.ElkConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * @ProjectName: elkapi
 * @Package: com.nko.elk.api.elasticsearch.config
 * @ClassName: EsClient
 * @Author: sun71
 * @Description:
 * @Date: 2019/8/1 10:47
 * @Version: 1.0
 */
@Configuration
@Slf4j
public class ElkRestClient {

    @Autowired
    private ElkConfig elkConfig;

    private  ElkRestClient elkRestClient;

    private TransportClient client = null;

    @PostConstruct
    public void init() {
        elkRestClient = this;
        elkRestClient.elkConfig = this.elkConfig;
    }

    public TransportClient getClient() {
        if (client != null) {
            return client;
        } else {
            synchronized (ElkRestClient.class) {
                Settings settings = Settings.builder()
                        //增加嗅探机制，找到ES集群
                        .put("client.transport.sniff", true)
                        //增加线程池个数，暂时设为5
                        .put("thread_pool.search.size", Integer.parseInt(elkRestClient.elkConfig.getPoolSize()))
                        // 忽略集群名字验证, 打开后集群名字不对也能连接上
                        .put("client.transport.ignore_cluster_name", true)
                        .build();
                client =  TransportClient.builder().settings(settings).build();

                for (int i = 0; i < elkRestClient.elkConfig.getHosts().size(); i++) {
                    client.addTransportAddress(
                            new InetSocketTransportAddress(new InetSocketAddress(elkRestClient.elkConfig.getHosts().get(i).getIp(), elkRestClient.elkConfig.getHosts().get(i).getPort())));
                }
                return client;
            }
        }
    }



}
