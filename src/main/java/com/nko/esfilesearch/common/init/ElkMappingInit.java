package com.nko.esfilesearch.common.init;

import com.nko.esfilesearch.client.ElkRestClient;
import com.nko.esfilesearch.common.base.EsTreeEnum;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * @ProjectName: esFileSearch
 * @Package: com.nko.esfilesearch.common.config
 * @ClassName: ElkMappingInit
 * @Author: sun71
 * @Description:
 * @Date: 2019/10/30 16:37
 * @Version: 1.0
 */
@Configuration
@Log4j2
public class ElkMappingInit {

    @Autowired
    ElkRestClient elkRestClient;
    //创建映射
    @PostConstruct
    public void createMapping()   {
        Client client = elkRestClient.getClient();
        String treeIndex = "treeindex";
        String treeType= "treetype";
        //1.先判断是否有索引，无索引创建索引
        IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(treeIndex);
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(inExistsRequest).actionGet();
       if(!inExistsResponse.isExists()){
           //不存在创建索引
        client.admin().indices().prepareCreate(treeIndex).execute().actionGet();
       }

        //2.查询是否有mapping
        ImmutableOpenMap<String, MappingMetaData> mappings = client.admin().cluster().prepareState().execute().actionGet().getState().getMetaData().getIndices().get(treeIndex).getMappings();
        if(!mappings.isEmpty()){
            log.info("mapping不为空");
            return;
        }else{
            System.out.println("mapping为空");
        //2.如果有mapping 跳过新建
        //3.如果没有 新建mapping

        //创建mapping约束字段 无mapping 无法索引
            XContentBuilder mapping =null;
            try {
                mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject(EsTreeEnum.createtime.name())
                .field("type","date")
                .endObject()
                .startObject(EsTreeEnum.id.name())
//                .field("type", "text")
                //es2.0版本无text 改为String
                .field("type", "String")
                .field("index", "not_analyzed")

                .endObject()
                .startObject(EsTreeEnum.nodename.name())
                .field("type","String")
                .field("index", "not_analyzed")
                .endObject()
                .startObject(EsTreeEnum.icon.name())
                .field("type", "String")
                .field("index", "not_analyzed")
                .endObject()
                .startObject(EsTreeEnum.checked.name())
                .field("type", "boolean")
                .endObject()

                .startObject(EsTreeEnum.nocheck.name())
                .field("type", "boolean")
                .endObject()

                .startObject(EsTreeEnum.parentId.name())
                .field("type", "String")
                .field("index", "not_analyzed")
                .endObject()

                .startObject(EsTreeEnum.open.name())
                .field("type", "boolean")
                .endObject()

                .startObject(EsTreeEnum.FLW_CODE.name())
                .field("type", "String")
                .field("index", "not_analyzed")
                .endObject()

                .startObject(EsTreeEnum.FLW_TYPE.name())
                .field("type", "String")
                .field("index", "not_analyzed")
                .endObject()

                .startObject(EsTreeEnum.type_name.name())
                .field("type", "String")
                .field("index", "not_analyzed")
                .endObject()

                .startObject(EsTreeEnum.type_code.name())
                .field("type", "String")
                .field("index", "not_analyzed")
                .endObject()

                .endObject()
                .endObject();
            }catch (Exception e){
                e.printStackTrace();
            }
        //添加mapping 绑定到 index
        PutMappingRequest putMappingRequest = Requests.putMappingRequest(treeIndex).type(treeType).source(mapping);
        client.admin().indices().putMapping(putMappingRequest).actionGet();

        }
    }
}
