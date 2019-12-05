package com.work.demos.mybatis.spider.web;

import com.alibaba.fastjson.JSON;
import com.work.demos.mybatis.generef.util.FileReader;
import com.work.demos.mybatis.generef.web.Mapptmp;
import com.work.demos.mybatis.spider.entity.WscEntity;
import com.work.demos.mybatis.spider.services.SpiderService;
import com.work.demos.utils.Result;
import com.work.demos.utils.ResultConstant;
import io.swagger.annotations.Api;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "gene_ref")
@Api(tags = "基因简介及参考文献生成、入库")
@CrossOrigin
public class SpiderWeb {
    private final Log log = LogFactory.getLog(SpiderWeb.class);
    @Autowired
    SpiderService service;
    @RequestMapping(value = "/domain", method = RequestMethod.GET)
    public Result domain(@RequestParam int page)  {
        String results =new FileReader().readFileByChars("/home/wangshichen/文档/work/postbody.json");
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(results);
        Mapptmp res=null;
        for (int i=page;i<10496;i++){
            log.info("--------------------------------------------------");
            log.info("第"+i+"页");
            jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Pubmed_Pager.CurrPage",i);
            jsonObject.put("EntrezSystem2.PEntrez.PubMed.Pubmed_Facets.FacetsUrlFrag","filters=;humans");
            res= service.domain(res,jsonObject);
        }


        return new Result(ResultConstant.SUCCESS_STATUE,"",res);
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result test(@RequestParam int page)  {

        service.getlast();

        return new Result(ResultConstant.SUCCESS_STATUE,"" );
    }

}
