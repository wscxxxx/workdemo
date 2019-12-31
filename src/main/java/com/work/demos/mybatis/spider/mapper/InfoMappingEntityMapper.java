package com.work.demos.mybatis.spider.mapper;

import com.bailian.servicetk.core.data.IMapper;
import com.bailian.servicetk.core.data.IMapperV2;
import com.work.demos.mybatis.spider.entity.InfoMappingEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoMappingEntityMapper extends IMapperV2<InfoMappingEntity> {
    int deleteByPrimaryKey(Integer id);

    int insert(InfoMappingEntity record);

    int insertSelective(InfoMappingEntity record);

    InfoMappingEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InfoMappingEntity record);

    int updateByPrimaryKeyWithBLOBs(InfoMappingEntity record);

    int updateByPrimaryKey(InfoMappingEntity record);

    List<InfoMappingEntity> selectMult(List<Long> ids);
}