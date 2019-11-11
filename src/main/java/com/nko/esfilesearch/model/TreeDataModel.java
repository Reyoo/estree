package com.nko.esfilesearch.model;


import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: esFileSearch
 * @Package: com.nko.esfilesearch.model
 * @ClassName: TreeDataModel
 * @Author: sun71
 * @Description:
 * @Date: 2019/10/18 15:27
 * @Version: 1.0
 */
@Data
public class TreeDataModel<T> {

    /** 节点ID */
    String id;
    /** 节点名称 */
    String nodeName;
    /**节点父ID */
    String parentId;

    List<TreeDataModel> children ;

    public TreeDataModel(){

    }

    public TreeDataModel(String id ,String nodeName,String parentId){
        super();
        this.id=id;
        this.nodeName =nodeName;
        this.parentId = parentId;
    }

    @Override

    public String toString() {

        return JSON.toJSONString(this);

    }

}
