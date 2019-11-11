package com.nko.esfilesearch.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nko.esfilesearch.client.ElkRestClient;
import com.nko.esfilesearch.model.EsTreeModel;
import com.nko.esfilesearch.model.TreeDataModel;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: esFileSearch
 * @Package: com.nko.esfilesearch.service
 * @ClassName: IndexSearchService
 * @Author: sun71
 * @Description: 索引查询
 * @Date: 2019/10/18 16:17
 * @Version: 1.0
 */
@Service
public class IndexSearchService {

    @Autowired
    ElkRestClient elkRestClient;

    /**
     * 新增索引
     * @param treeType
     * @param treeIndex
     * @param esTreeModel
     */
    public void findTreeWithType(String treeType , String treeIndex, EsTreeModel esTreeModel){
        try{

        if(StrUtil.hasEmpty(treeType)){
            return;
        }
        Client client = elkRestClient.getClient();
        String jsonStr = JSONUtil.toJsonStr(esTreeModel);
        IndexResponse response = client.prepareIndex(treeIndex, treeType)
                .setSource(jsonStr).setId(esTreeModel.getId())
                .get();
        if(response.isCreated()){

        }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public boolean findIndex(String index){

        return false;
    }




}
