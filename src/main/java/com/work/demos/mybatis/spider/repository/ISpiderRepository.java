package com.work.demos.mybatis.spider.repository;

 import com.bailian.servicetk.core.data.IBaseRepository;
 import com.work.demos.mybatis.generef.web.Mapptmp;
 import com.work.demos.mybatis.spider.entity.GeneinfoEntity;
 import org.springframework.stereotype.Component;

@Component
public interface ISpiderRepository   {
    Mapptmp mutationdownland(int start,int end,Mapptmp flag ) ;
    GeneinfoEntity getone();
    Mapptmp testmutationdownland( );

    void savetomysql();

}
