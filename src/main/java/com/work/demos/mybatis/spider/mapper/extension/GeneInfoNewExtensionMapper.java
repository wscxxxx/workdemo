package com.work.demos.mybatis.spider.mapper.extension;

import com.work.demos.mybatis.spider.entity.GeneinfoEntity;
 import com.work.demos.mybatis.spider.mapper.GeneinfoEntityMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GeneInfoNewExtensionMapper extends GeneinfoEntityMapper {
    int getgenelast();
    int getauthorlast();
    int getcompanylast();
    Long getmappinglast();

    GeneinfoEntity geneidlist(int pre_num);
    void geneadd(@Param("emps")List<GeneinfoEntity> emp);

}
