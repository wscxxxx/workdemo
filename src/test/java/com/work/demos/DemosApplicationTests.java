package com.work.demos;

import com.work.demos.mybatis.generef.util.Tmp_aut;
import com.work.demos.mybatis.generef.web.Mapptmp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemosApplicationTests {

    private Mapptmp add(Mapptmp res,int a){
        Mapptmp b=new Mapptmp();
         if (res==null){
              b.setCom_id(12);

             System.out.println(b);
         }else {
             b=res;
             System.out.println("xxxx"+b);

              b.setCom_id(res.getCom_id()+1);

         }
        return b;
    }

    @Test
    void contextLoads() {
        Mapptmp a=null;
        for (int i=0;i<5;i++){
             a=add(a,i);
            System.out.println(a);

        }

    }

}
