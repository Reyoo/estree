package com.nko.esfilesearch.controller;

import com.nko.esfilesearch.common.base.AjaxResult;
import com.nko.esfilesearch.service.EsTreeService;
import io.swagger.annotations.*;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
@Api(value = "es索引操作", tags = "estree")
@RestController
@RequestMapping("/nko")
public class EsTreeController {

    //1.新增树

    /**
     * @param pid      父id
     * @param nodeName 目录名称
     * @param icon     图标名称
     * @return
     */
    public AjaxResult putNodeIntoTree(String pid, String nodeName, String icon) {
        //生成base64 规则
        Strings.base64UUID();
        return AjaxResult.success();
    }

    @Autowired
    EsTreeService esTreeService;

    /**
     * @param pid
     * @param nodename
     * @param icon
     * @return
     * @description : 创建一个节点
     */
    @ApiOperation(value = "新增树索引", httpMethod = "GET")
    @GetMapping(value = "/treeindex/add")
    @ApiImplicitParams({@ApiImplicitParam(name="pid",value = "父ID",required = false,defaultValue ="",paramType = "query"),
    @ApiImplicitParam(name="nodeName",value = "节点名称",required = true),
    @ApiImplicitParam(name = "icon",value = "图标名称",required = false)})
//    @ApiResponse(reference = "")
    @ResponseBody
    public AjaxResult addTreeNode(@RequestParam(defaultValue = "") String pid,
                              @RequestParam(required = true) String nodename,
                              @RequestParam(defaultValue = "") String icon) {
        return  esTreeService.addTreeNode(pid,nodename,icon);
    }


    @ApiOperation(value = "删除树节点", httpMethod = "DELETE")
    @ApiImplicitParams({@ApiImplicitParam(name="id",value = "待删除节点ID",required = true,paramType = "query")})
    @GetMapping(value = "/treeindex/remove")
    @ResponseBody
    public AjaxResult removeTreeNode(@RequestParam(required = true) String id){
        return  esTreeService.removeTreeNode(id);
    }


    @ApiOperation(value = "获取树节点", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name="dept",value = "部门",required = false,paramType = "query")})
    @GetMapping(value = "/treeindex/gettree")
    @ResponseBody
    public AjaxResult getTreeJson(@RequestParam(required = false) String dept){
        return esTreeService.getTreeJson(dept);
    }


    @ApiOperation(value = "更新树节点", httpMethod = "DELETE")
    @ApiImplicitParams({@ApiImplicitParam(name="id",value = "待删除节点ID",required = true,paramType = "query")})
    @GetMapping(value = "/treeindex/remove")
    @ResponseBody
    public AjaxResult updateTreeNode(@RequestParam(required = true) String id,String nodename){
        return  esTreeService.updateTreeNode(id,nodename);
    }



}