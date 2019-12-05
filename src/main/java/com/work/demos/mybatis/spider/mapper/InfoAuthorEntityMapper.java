package com.work.demos.mybatis.spider.mapper;

import com.bailian.servicetk.core.data.IMapper;
import com.work.demos.mybatis.spider.entity.InfoAuthorEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoAuthorEntityMapper extends IMapper<InfoAuthorEntity> {
    int deleteByPrimaryKey(Integer id);

    int insert(InfoAuthorEntity record);

    int insertSelective(InfoAuthorEntity record);

    InfoAuthorEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InfoAuthorEntity record);

    int updateByPrimaryKey(InfoAuthorEntity record);

    List<InfoAuthorEntity> selectMult(List<Long> ids);
}