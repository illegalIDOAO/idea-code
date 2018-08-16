package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Parts;
import com.kaishengit.entity.PartsStream;
import com.kaishengit.entity.Type;

import java.util.List;
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
    Parts selectParsById(int id);

    /**
     *  分页查询parts
     *
     * @param pageNo
     * @param map
     * @return PageInfo对象
     */
    PageInfo<Parts> selectPage(Integer pageNo, Map<String, Object> map);


    /**
     * 根据主键删除
     * @param id
     */
    void delect(int id);

    /**
     * 入库新增
     * @param parts
     * @param employeeId
     */
    void partsNew(Parts parts,Integer employeeId);

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

    /**
     * 增加备件库存
     * @param partsId
     * @param addNum
     * @param emplyeeId
     */
    void addInventory(Integer partsId, Integer addNum,Integer emplyeeId);

    /**
     * 查所有配件列表
     * @return
     */
    List<Parts> selectAllParts();

    /**
     * 查所有配件(带类型信息)列表
     * @return
     */
    List<Parts> selectAllPartsWithType();

    /**
     * 根据类型id查找配件列表
     * @param id
     * @return
     */
    List<Parts> findPartsByTypeId(int id);

    /**
     * 配件入库查询
     * @param pageNo
     * @param map
     * @return
     */
    PageInfo<PartsStream> selectInPage(Integer pageNo, Map<String,String> map);

    /**
     * 配件出库查询
     * @param pageNo
     * @param map
     * @return
     */
    PageInfo<PartsStream> selectOutPage(Integer pageNo, Map<String,String> map);


    /**
     * 查找类型商品类型列表
     * @return
     */
    List<Type> findTypes();

    /**
     * 删除类型
     * @param id
     */
    void typeDel(int id);

    /**
     * 新增类型
     * @param typeName
     */
    void typeNew(String typeName);
}
