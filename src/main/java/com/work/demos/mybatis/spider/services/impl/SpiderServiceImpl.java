package com.work.demos.mybatis.spider.services.impl;

import com.work.demos.mybatis.generef.web.Mapptmp;
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
        mapper.update_map( );
//         mapper.testmutationdownland();
        return "";
    }

    @Override
    public void getlast( ) {
        mapper.testmutationdownland();
    }

    @Override
    public void domain( int page,String gene) {
        Workers workers=new Workers();
        workers.dowlandweb(page,gene);
    }

    @Override
    public void savetosql(int page   ) {
        int flag=1;
        Mapptmp res2=new Mapptmp();

        for (int i=page;i<48608;i++){

            int end=0;
//            if (page+20>=2626){
//                page=page-20;
//                end=2626-page;
//                flag=20;
//            }
            flag++;
//            else {
//                end=2626;
//            }
            if (flag==10){

                log.info("--------------------------------------------------"+end);
                log.info("第"+page+"页到第"+(page+10)+"页");
                log.info(res2 );

                if (res2==null||res2.getCom_id()==0){
                    end=10;
                    res2= mapper.mutationdownland(  page ,end,null );

                }else {
                    res2= mapper.mutationdownland(  page ,end,res2 );

                }


                page=page+10;
                flag=1;

            }
        }

    }

    @Override
    public void saveothers() {
        Mapptmp res2=null;
        mapper.addothers(res2);
    }

    @Override
    public int update_mapping() {
       return mapper.update_map( );
    }
}
