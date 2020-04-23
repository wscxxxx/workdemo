package com.work.demos.mybatis.spider.mapper;

import com.bailian.servicetk.core.data.IMapper;
import com.work.demos.mybatis.spider.entity.InfoCompanyEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoCompanyEntityMapper extends IMapper<InfoCompanyEntity> {
    int deleteByPrimaryKey(Integer id);

    int insert(InfoCompanyEntity record);

    int insertSelective(InfoCompanyEntity record);

    InfoCompanyEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InfoCompanyEntity record);

    int updateByPrimaryKeyWithBLOBs(InfoCompanyEntity record);

    List<InfoCompanyEntity> selectMult(List<Long> ids);
}