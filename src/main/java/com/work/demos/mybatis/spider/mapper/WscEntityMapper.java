package com.work.demos.mybatis.spider.mapper;

import com.bailian.servicetk.core.data.IMapper;
import com.work.demos.mybatis.spider.entity.WscEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WscEntityMapper extends IMapper<WscEntity> {
    int deleteByPrimaryKey(Integer id);

    int insert(WscEntity record);

    int insertSelective(WscEntity record);

    WscEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WscEntity record);

    int updateByPrimaryKey(WscEntity record);

    List<WscEntity> selectMult(List<Long> ids);
}