package com.kaishengit.mapper;

import com.kaishengit.entity.Student;
import com.kaishengit.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:57 2018/7/10
 */
public interface StudentMapper {

    Student findWithSchoolByIdForAs(Integer id);

    Student findWithSchoolByIdForMap(Integer id);

    Student findWithTagById(Integer id);

    int addBatchTag(@Param("tagList") List<Tag> tagList);

}
