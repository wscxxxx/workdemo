package com.work.demos.mybatis.spider.services;

import com.alibaba.fastjson.JSONObject;
import com.work.demos.mybatis.generef.web.Mapptmp;
import com.work.demos.mybatis.spider.entity.WscEntity;

public interface SpiderService {
     String getall();
     void getlast( );
     Mapptmp domain(Mapptmp flag, JSONObject para);
}
