package com.work.demos.mybatis.spider.mapper;

import com.bailian.servicetk.core.data.IMapper;
import com.work.demos.mybatis.spider.entity.GeneinfoEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GeneinfoEntityMapper extends IMapper<GeneinfoEntity> {
    int deleteByPrimaryKey(Integer id);

    int insert(GeneinfoEntity record);

    int insertSelective(GeneinfoEntity record);

    GeneinfoEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GeneinfoEntity record);

    int updateByPrimaryKeyWithBLOBs(GeneinfoEntity record);

    int updateByPrimaryKey(GeneinfoEntity record);

    List<GeneinfoEntity> selectMult(List<Long> ids);
}