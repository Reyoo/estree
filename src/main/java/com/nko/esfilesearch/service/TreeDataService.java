package com.nko.esfilesearch.service;

import com.nko.esfilesearch.model.TreeDataModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: esFileSearch
 * @Package: com.nko.esfilesearch.service
 * @ClassName: TreeDataService
 * @Author: sun71
 * @Description: 树
 * @Date: 2019/10/21 14:14
 * @Version: 1.0
 */
@Service
public class TreeDataService {

    //由于老版本无法使用spring-data-elasticSearch 这里写死索引及类型
    String treeIndex = "treeindex";
    String treeType= "treetype";

    //1.新建一个节点：
//    public String addTreeNode


    public List data(List<TreeDataModel> nodes) {
        ArrayList<TreeDataModel> rootNode = new ArrayList<>();
        for(TreeDataModel node:nodes){
            if(node.getParentId().equals("0")){
                rootNode.add(node);
            }
        }
        for(TreeDataModel node:rootNode){
            List<TreeDataModel> child = getChild(String.valueOf(node.getId()), nodes);
            node.setChildren(child);
        }
        return rootNode;
    }


    /**
     * @return
     * @Author
     * @Description //TODO 获取根节点的子节点
     * @Date 2018/10/30 0030 下午 11:37
     * @Param
     */
    public List<TreeDataModel> getChild(String id, List<TreeDataModel> allNode) {
        //存放子菜单的集合
        ArrayList<TreeDataModel> listChild = new ArrayList<>();
        for (TreeDataModel node : allNode) {
            if (node.getParentId().equals(id)) {
                listChild.add(node);
            }
        }
        //递归：
        for (TreeDataModel node : listChild) {
            node.setChildren(getChild(String.valueOf(node.getId()), allNode));
        }
        if (listChild.size() == 0) {
            return null;
        }
        return listChild;
    }






}
