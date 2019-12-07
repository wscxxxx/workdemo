package com.work.demos.mybatis.spider.services.impl;

import com.work.demos.mybatis.generef.web.Mapptmp;
import com.work.demos.mybatis.spider.entity.GeneinfoEntity;
import com.work.demos.mybatis.spider.services.SpiderService;
import com.work.demos.mybatis.spider.repository.SpiderRepository;
import com.work.demos.utils.Workers;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpiderServiceImpl implements SpiderService {

private Log log =LogFactory.getLog(SpiderServiceImpl.class);

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
    public void domain( int page) {
        Workers workers=new Workers();
        workers.dowlandweb(page);
    }

    @Override
    public void savetosql(int page   ) {
        int flag=1;
          Mapptmp res=new Mapptmp();
        for (int i=page;i<2626;i++){


            int end=0;
            if (page+5<2626){
                end=page+5;
            }
            flag++;
//            else {
//                end=2626;
//            }

            if (flag==5){
                Mapptmp res2=new Mapptmp();
                log.info("--------------------------------------------------");
                log.info("第"+page+"页到第"+(page+10)+"页");
                log.info(res );

                if (res.getAut_id()==0){
                    res= mapper.mutationdownland(  page ,end,null );

                }else {
                    res= mapper.mutationdownland(  page ,end,res );

                }


                page=page+5;
                flag=1;

            }
        }

    }
}
