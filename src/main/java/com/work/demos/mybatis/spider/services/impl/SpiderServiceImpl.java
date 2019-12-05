package com.work.demos.mybatis.spider.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.work.demos.mybatis.generef.web.Mapptmp;
import com.work.demos.mybatis.spider.entity.WscEntity;
import com.work.demos.mybatis.spider.services.SpiderService;
import com.work.demos.mybatis.spider.entity.GeneinfoEntity;
import com.work.demos.mybatis.spider.repository.SpiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpiderServiceImpl implements SpiderService {



@Autowired
 SpiderRepository mapper;



    @Override
    public String getall() {

        GeneinfoEntity geneinfoEntity=mapper.getone();
        return geneinfoEntity.getAbstractEn();
    }

    @Override
    public void getlast( ) {
        mapper.testmutationdownland();
    }

    @Override
    public Mapptmp domain(Mapptmp flag, JSONObject para) {
        return mapper.mutationdownland(flag,  para);
    }
}
