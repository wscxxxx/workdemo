package com.work.demos;

import com.work.demos.mybatis.spider.entity.GeneinfoEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class mytest {
    @Test
    public void test01(){
        List<GeneinfoEntity> userList = new ArrayList<>();
        GeneinfoEntity geneinfoEntitf=new GeneinfoEntity();
        geneinfoEntitf.setPreNum(123);
        GeneinfoEntity geneinfoEntitf1=new GeneinfoEntity();
        geneinfoEntitf1.setPreNum(321);
        GeneinfoEntity geneinfoEntitf2=new GeneinfoEntity();
        geneinfoEntitf2.setPreNum(123);
        GeneinfoEntity geneinfoEntitf3=new GeneinfoEntity();
        geneinfoEntitf3.setPreNum(123);
        userList.add( geneinfoEntitf);
        userList.add(geneinfoEntitf1 );
        userList.add(geneinfoEntitf2 );
        userList.add(geneinfoEntitf3 );

        Set<GeneinfoEntity> userSet = new HashSet<>(userList);
        List<GeneinfoEntity> list = new ArrayList<>(userSet);
        list.forEach(System.out::println);
    }
}
