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
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public Result domain(@RequestParam int page )  {

        service.savetosql(page);

        return new Result(ResultConstant.SUCCESS_STATUE,"" );
    }

    @RequestMapping(value = "/dowland", method = RequestMethod.GET)
    public void dowland(@RequestParam int page)  {
        System.out.println(page);
       service.domain(page);


    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result test(@RequestParam int page)  {

        service.getlast();

        return new Result(ResultConstant.SUCCESS_STATUE,"" );
    }

}
