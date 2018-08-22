package com.kaishengit.mapper;

import com.kaishengit.entity.DailyStatistical;
import com.kaishengit.entity.DailyStatisticalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DailyStatisticalMapper {
    long countByExample(DailyStatisticalExample example);

    int deleteByExample(DailyStatisticalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DailyStatistical record);

    int insertSelective(DailyStatistical record);

    List<DailyStatistical> selectByExample(DailyStatisticalExample example);

    DailyStatistical selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DailyStatistical record, @Param("example") DailyStatisticalExample example);

    int updateByExample(@Param("record") DailyStatistical record, @Param("example") DailyStatisticalExample example);

    int updateByPrimaryKeySelective(DailyStatistical record);

    int updateByPrimaryKey(DailyStatistical record);
}