package com.work.demos.mybatis.spider.mapper.extension;

import com.work.demos.mybatis.spider.entity.*;
import com.work.demos.mybatis.spider.mapper.GeneinfoEntityMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface GeneInfoNewExtensionMapper extends GeneinfoEntityMapper {
    GeneinfoEntity getgenelast(@Param("retable") Retable retable);
    Integer getauthorlast();
    Integer getcompanylast();
    Long getmappinglast();
     InfoAuthorEntity  findautbyname(String name);
    GeneinfoEntity geneidlist(int pre_num);
    void test(@Param("emps")List<WscEntity> emps);
    int geneadd(List<GeneinfoEntity> entities);
    int authoradd(List<InfoAuthorEntity> entities);
    int companyadd(List<InfoCompanyEntity> entities);
    int mappingadd(List<InfoMappingEntity> entities);
    List<InfoAuthorEntity> getbypage(Map<String,Object> page);



}
