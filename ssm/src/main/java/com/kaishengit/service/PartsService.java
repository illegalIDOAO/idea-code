package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Parts;

import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:32 2018/7/23
 */
public interface PartsService {

    /**
    * 根据id查找
    * @param id
    * @return parts对象
    */
    Parts findParsById(int id);

    /**
     *  分页查询parts
     *
     * @param pageNo
     * @param map
     * @return PageInfo对象
     */
    PageInfo<Parts> findPage(Integer pageNo, Map<String,Object> map);


    /**
     * 根据主键删除
     * @param id
     */
    void delect(int id);

    /**
     * 入库新增
     * @param parts
     */
    void partsNew(Parts parts);

    /**
     * 修改
     * @param parts
     */
    void partsEdit(Parts parts);

    /**
     * 坚持partsNo是否可用
     * @param partsNo
     */
    void check(String partsNo);
}
