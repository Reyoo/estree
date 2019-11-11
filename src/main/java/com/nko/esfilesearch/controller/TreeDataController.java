package com.nko.esfilesearch.controller;

import com.nko.esfilesearch.common.base.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.elasticsearch.common.Strings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ProjectName: esFileSearch
 * @Package: com.nko.esfilesearch.controller
 * @ClassName: TreeDataController
 * @Author: sun71
 * @Description:
 * @Date: 2019/10/21 15:28
 * @Version: 1.0
 */
//@ApiIgnore(value = "树操作")
@Api(value = "树操作",tags = "aaa/sss")
@RestController
@RequestMapping("/test")
public class TreeDataController {

    //1.新增树

    /**
     *
     * @param pid 父id
     * @param nodeName 目录名称
     * @param icon 图标名称
     * @return
     */
    public AjaxResult putNodeIntoTree(String pid,String nodeName,String icon){

        //生成base64 规则
        Strings.base64UUID();
        return AjaxResult.success();
    }

//    @ApiOperation(value = "添加用户", notes = "根据参数添加用户")
    @GetMapping(value = "/add")
    public String getUser() {
        return "zyc";
    }


}