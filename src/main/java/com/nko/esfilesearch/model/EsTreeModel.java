package com.nko.esfilesearch.model;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: esFileSearch
 * @Package: com.nko.esfilesearch.model
 * @ClassName: EsTreeModel
 * @Author: sun71
 * @Description:
 * @Date: 2019/10/22 9:31
 * @Version: 1.0
 */
@Data
@ApiModel(description="树结构")
public class EsTreeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public EsTreeModel(){

    }

    public EsTreeModel(String id,String parentId, String nodename,String icon,LocalDateTime createtime){
        super();
        this.id= id;
        this.parentId=parentId;
        this.nodename=nodename;
        this.icon=icon;
        this.createtime =createtime;

    }

    /** 节点ID */
    @ApiModelProperty(value="文档ID",name="id",example="1")
    String id;
    /** 节点名称 */
    @ApiModelProperty(value="节点名称",name="nodename",example="1")
    String nodename;
    /**节点父ID */
    @ApiModelProperty(value="父ID",name="parentId",example="1")

    String parentId;
    /**节点标题*/
    @Value("blank")
    String title;
    /** 图标 */
    String icon;
    /** 是否勾选 */
    private boolean checked = false;

    /** 是否展开 */
    private boolean open = false;

    /** 是否能勾选 */
    private boolean nocheck = false;

    String FLW_CODE;
    String FLW_TYPE;
    String type_code;
    String type_name;

    LocalDateTime createtime;

//    public String getTitle() {
//        if(StrUtil.isEmpty(title)){
//           return "blank";
//        }else{
//            return title;
//        }
//    }
//
//    public void setTitle(String title) {
//            this.title = title;
//    }
//
//    public String getFLW_CODE() {
//        if(StrUtil.isEmpty(FLW_CODE)){
//            return "blank";
//        }else{
//            return FLW_CODE;
//        }
//    }
//
//    public void setFLW_CODE(String FLW_CODE) {
//            this.FLW_CODE = FLW_CODE;
//    }
//
//    public String getFLW_TYPE() {
//        if(StrUtil.isEmpty(FLW_TYPE)){
//            return "blank";
//        }else{
//            return FLW_TYPE;
//        }
//
//    }
//
//    public void setFLW_TYPE(String FLW_TYPE) {
//            this.FLW_TYPE = FLW_TYPE;
//    }
//
//    public String getType_code() {
//        if(StrUtil.isEmpty(type_code)){
//            return "blank";
//        }else{
//            return type_code;
//        }
//    }
//
//    public void setType_code(String type_code) {
//            this.type_code = type_code;
//    }
//
//    public String getType_name() {
//        if(StrUtil.isEmpty(type_name)){
//            return "blank";
//        }else{
//            return type_name;
//        }
//    }
//
//    public void setType_name(String type_name) {
//            this.type_name = type_name;
//    }

}
