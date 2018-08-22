package com.kaishengit.mapper;

import com.kaishengit.entity.FixTimeout;
import com.kaishengit.entity.FixTimeoutExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FixTimeoutMapper {
    long countByExample(FixTimeoutExample example);

    int deleteByExample(FixTimeoutExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FixTimeout record);

    int insertSelective(FixTimeout record);

    List<FixTimeout> selectByExample(FixTimeoutExample example);

    FixTimeout selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FixTimeout record, @Param("example") FixTimeoutExample example);

    int updateByExample(@Param("record") FixTimeout record, @Param("example") FixTimeoutExample example);

    int updateByPrimaryKeySelective(FixTimeout record);

    int updateByPrimaryKey(FixTimeout record);
}