package com.work.demos.mybatis.spider.web;

import com.alibaba.fastjson.JSON;
import com.work.demos.mybatis.generef.util.FileReader;
import com.work.demos.mybatis.generef.web.Mapptmp;
import com.work.demos.mybatis.spider.entity.WscEntity;
import com.work.demos.mybatis.spider.services.SpiderService;
import com.work.demos.utils.Getproxy;
import com.work.demos.utils.Result;
import com.work.demos.utils.ResultConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("保存至数据库")
     @RequestMapping(value = "/save", method = RequestMethod.GET)
    public Result domain(@RequestParam int page )  {

        service.savetosql(page);

        return new Result(ResultConstant.SUCCESS_STATUE,"" );
    }
    @ApiOperation("单独保存有问题的数据")
    @RequestMapping(value = "/saveothers", method = RequestMethod.GET)
    public Result saveothers(@RequestParam int page )  {

        service.saveothers( );

        return new Result(ResultConstant.SUCCESS_STATUE,"" );
    }
    @ApiOperation("下载保存指定页数开始的网页")
    @RequestMapping(value = "/dowland", method = RequestMethod.GET)
    public void dowland(@RequestParam int page)  {
        String gene="diagnosis";
        System.out.println(page);
       service.domain(page,gene);


    }
    @ApiOperation("测试")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result test(@RequestParam int page)  {


        return new Result(ResultConstant.SUCCESS_STATUE,service.getall()+"");
    }

}
