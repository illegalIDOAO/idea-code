package com.kaishengit.mapper;

import com.kaishengit.entity.EmployeeRole;
import com.kaishengit.entity.EmployeeRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeRoleMapper {
    long countByExample(EmployeeRoleExample example);

    int deleteByExample(EmployeeRoleExample example);

    int insert(EmployeeRole record);

    int insertSelective(EmployeeRole record);

    List<EmployeeRole> selectByExample(EmployeeRoleExample example);

    int updateByExampleSelective(@Param("record") EmployeeRole record, @Param("example") EmployeeRoleExample example);

    int updateByExample(@Param("record") EmployeeRole record, @Param("example") EmployeeRoleExample example);
}