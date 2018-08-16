package com.kaishengit.mapper;

import com.kaishengit.entity.Parts;
import com.kaishengit.entity.PartsStream;
import com.kaishengit.entity.PartsStreamExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PartsStreamMapper {
    long countByExample(PartsStreamExample example);

    int deleteByExample(PartsStreamExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartsStream record);

    int insertSelective(PartsStream record);

    List<PartsStream> selectByExample(PartsStreamExample example);

    PartsStream selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartsStream record, @Param("example") PartsStreamExample example);

    int updateByExample(@Param("record") PartsStream record, @Param("example") PartsStreamExample example);

    int updateByPrimaryKeySelective(PartsStream record);

    int updateByPrimaryKey(PartsStream record);

    List<PartsStream> selectInPartsList(Map<String,String> map);

    List<PartsStream> selectOutPartsList(Map<String,String> map);
}