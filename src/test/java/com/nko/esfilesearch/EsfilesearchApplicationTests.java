package com.nko.esfilesearch;

import com.nko.esfilesearch.client.ElkRestClient;
import com.nko.esfilesearch.common.init.ElkMappingInit;
import com.nko.esfilesearch.model.EsTreeModel;
import com.nko.esfilesearch.service.EsTreeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class EsfilesearchApplicationTests {

//    @Autowired
//    IndexSearchService indexSearchService;

    @Autowired
    ElkRestClient elkRestClient;

    @Autowired
    EsTreeService esTreeService;

    @Autowired
    ElkMappingInit elkMappingInit;

    @Test
    void contextLoads() {
//Integer id ,String nodeName,Integer parentId
        EsTreeModel treeDataModel = new EsTreeModel();
        String treeIndex = "treeindex";
        String treeType= "treetype";
//        indexSearchService.findTreeWithType(treeType,treeIndex,treeDataModel);
        System.out.println(treeDataModel.getId());
    }

    @Test
    void contextinnerLoads() {
        //Integer id ,String nodeName,Integer parentId
        esTreeService.removeTreeNode("AW3yS4FlDGqW6Y3ITsBs");
    }

    /**
     * 初始化数据
     */
    @Test
    void getInitLoads() {
//Integer id ,String nodeName,Integer parentId
//        esTreeService.getTreeJson();
        try {
            elkMappingInit.createMapping();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    void getTreeLoads() {
//Integer id ,String nodeName,Integer parentId
        try {
            esTreeService.getTreeJson("OA");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}