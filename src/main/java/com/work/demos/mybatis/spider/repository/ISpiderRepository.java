package com.work.demos.mybatis.spider.repository;

 import com.alibaba.fastjson.JSONObject;
 import com.bailian.servicetk.core.data.IBaseRepository;
 import com.work.demos.mybatis.generef.web.Mapptmp;
 import com.work.demos.mybatis.spider.entity.GeneinfoEntity;
import org.springframework.stereotype.Component;

@Component
public interface ISpiderRepository extends IBaseRepository<GeneinfoEntity> {
    Mapptmp mutationdownland(Mapptmp flag, JSONObject para) ;
    GeneinfoEntity getone();
    Mapptmp testmutationdownland( );
}
