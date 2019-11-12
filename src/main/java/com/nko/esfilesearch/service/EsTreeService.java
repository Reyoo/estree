package com.nko.esfilesearch.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.nko.esfilesearch.client.ElkRestClient;
import com.nko.esfilesearch.common.base.AjaxResult;
import com.nko.esfilesearch.common.base.EsTreeEnum;
import com.nko.esfilesearch.model.EsTreeModel;
import com.nko.esfilesearch.model.TreeDataModel;
import io.swagger.annotations.ApiModelProperty;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ProjectName: esFileSearch
 * @Package: com.nko.esfilesearch.service
 * @ClassName: TreeDataService
 * @Author: sun71
 * @Description: 树的增删改差
 * @Date: 2019/10/21 14:14
 * @Version: 1.0
 */
@Service
public class EsTreeService {

    @Autowired
    ElkRestClient elkRestClient;

    //由于老版本无法使用spring-data-elasticSearch 这里写死索引及类型
    String treeIndex = "treeindex";
    String treeType= "treetype";

    /**
     * @Description 新增一个树节点
     * @param pid
     * @param nodename
     * @param icon
     * @return
     */
     public AjaxResult addTreeNode(String pid, String nodename, String icon) {
         Client client = elkRestClient.getClient();
         EsTreeModel esTreeModel = new EsTreeModel(Strings.base64UUID(),pid,nodename,icon, LocalDateTime.now());
         Map<String, Object> map = BeanUtil.beanToMap(esTreeModel);
         IndexResponse response = client.prepareIndex(treeIndex, treeType)
                 .setSource(map).setId(esTreeModel.getId())
                 .get();
        if(response.isCreated()){
            return AjaxResult.success();
        }
        return  AjaxResult.error("新建节点失败");
     }


    //2.删除一个节点：

    /**
     * @Description 删除一个节点
     * @param id
     * @return
     */
    //思路：id是待删除节点的id 可以删除
    public AjaxResult removeTreeNode(String id) {
        Client client = elkRestClient.getClient();
        //待删除的id 是否是其他节点的父id 如果是其他节点的父id 不能删除
        SearchResponse searchResponse = client.prepareSearch().setIndices(treeIndex).setTypes(treeType)
                .setQuery(QueryBuilders.matchQuery("parentId", id)).get();
        if (searchResponse.getHits().getTotalHits() > 0) {
            return AjaxResult.error("存在下级节点，不允许删除");
        } else {
            DeleteResponse response = client.prepareDelete(treeIndex, treeType, id).get();
            if (response.isFound()) {
                return AjaxResult.success("删除成功");
            }
        }
        return AjaxResult.error("删除失败，请检查后重新删除");
    }


    //3.更新一个节点
    public AjaxResult updateTreeNode(String id,String nodename) {
        try{
            Client client = elkRestClient.getClient();
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.id(id).doc(EsTreeEnum.nodename.name(),nodename);
            UpdateResponse updateResponse =client.update(updateRequest).get();
            if(updateResponse.isCreated()){
                return AjaxResult.success();
            }
        }catch (Exception e){
            return AjaxResult.error("更新失败，请检查后重新更新");
        }
            return AjaxResult.error("更新失败，请检查后重新更新");
    }

    //4.返回树JSON
    public AjaxResult getTreeJson(String dept){
         //先获取跟节点 pid 为空的节点集合 转为List
        Client client = elkRestClient.getClient();
        SearchResponse searchResponse =null;
                List<TreeDataModel<EsTreeModel>> treeDataModels = new ArrayList<>();
        //获取根节点
        if(StrUtil.hasEmpty(dept)){
            searchResponse = client.prepareSearch().setIndices(treeIndex).setTypes(treeType).get();
        }else{
            searchResponse = client.prepareSearch().setIndices(treeIndex).setTypes(treeType)
                    .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("parentId", ""))
                            .must(QueryBuilders.termQuery("nodename", dept))).get();
        }
        System.out.println(searchResponse.getHits().getTotalHits());
        for(SearchHit searchHit : searchResponse.getHits().getHits()){
            treeDataModels.add(new TreeDataModel(searchHit.getSource().get(EsTreeEnum.id.name()).toString()
                    ,searchHit.getSource().get(EsTreeEnum.nodename.name()).toString()
                    ,searchHit.getSource().get(EsTreeEnum.parentId.name()).toString()));

        }
        List<TreeDataModel<EsTreeModel>> t = build_Trees(treeDataModels);
        System.out.println(t.toString());
//        List<EsTreeModel>treeNodes = new ArrayList();
//        //逐个给根节点找child
//        for(EsTreeModel esTreeModel:rootNodes){
//
//        }
         return AjaxResult.success(String.valueOf(searchResponse.getHits().getTotalHits()));
    }

    /**
     * 构造树
     * @param nodes
     * @return
     */
    public  List<TreeDataModel<EsTreeModel>> build_Trees(List<TreeDataModel<EsTreeModel>> nodes) {

        if (nodes == null) {
            return null;
        }
        List<TreeDataModel<EsTreeModel>> topNodes = new ArrayList<>();
        for (TreeDataModel<EsTreeModel> children : nodes) {
            String pid = children.getParentId();
            if ("".equals(pid)) {//根级结点
                topNodes.add(children);
                continue;
            }
            for (TreeDataModel<EsTreeModel> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {//表示有子级结点，就给Children设置对象
                    if(null == parent.getChildren()){
                        parent.setChildren(new ArrayList<TreeDataModel>());
                    }
                    parent.getChildren().add(children);
                    //children.setParent(true);
                    //parent.setChildren(true);
                    continue;
                }
            }
        }
        return topNodes;
    }


}



