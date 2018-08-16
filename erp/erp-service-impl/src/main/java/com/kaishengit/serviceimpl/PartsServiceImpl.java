package com.kaishengit.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.*;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.PartsMapper;
import com.kaishengit.mapper.PartsStreamMapper;
import com.kaishengit.mapper.TypeMapper;
import com.kaishengit.service.PartsService;
import com.kaishengit.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:36 2018/7/23
 */
@Service
public class PartsServiceImpl implements PartsService {

    Logger logger = LoggerFactory.getLogger(PartsServiceImpl.class);

    @Autowired
    private PartsMapper partsMapper;
    @Autowired
    private PartsStreamMapper partsStreamMapper;
    @Autowired
    private TypeMapper typeMappper;
    @Autowired
    private TypeMapper typeMapper;

    /**
     * 根据id查找
     *
     * @param id
     * @return parts对象
     */
    @Override
    public Parts selectParsById(int id) {
        Parts parts = partsMapper.selectByPrimaryKey(id);
        Type type  = typeMappper.selectByPrimaryKey(parts.getTypeId());
        parts.setType(type);
        return parts;
    }

    /**
     * 分页查询parts
     *
     * @param pageNo
     * @param map
     * @return PageInfo对象
     */
    @Override
    public PageInfo<Parts> selectPage(Integer pageNo, Map<String,Object> map) {
        PageHelper.startPage(pageNo,Constant.DEFAULT_PAGE_SIZE);

        List<Parts> parts = partsMapper.findPageByKey(map);

        PageInfo<Parts> partsPageInfo = new PageInfo(parts);
        return partsPageInfo;
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    @Override
    public void delect(int id) {
        partsMapper.deleteByPrimaryKey(id);

        //TODO 以下方法更好
        /*Parts parts = partsMapper.selectByPrimaryKey(id);
        if(parts.getInventory() != 0){
            throw new NotAllowChangeException();
        }else{
            partsMapper.deleteByPrimaryKey(id);
        }*/
    }

    /**
     * 入库新增
     *
     * @param parts
     * @param employeeId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void partsNew(Parts parts,Integer employeeId) {
        partsMapper.insertSelective(parts);

        //记录日志
        logger.debug("employId={}在{}新增了备件{}", employeeId, new Date(),parts.getPartsNo());

        //记录数据库流水日志
        PartsStream partsStream = new PartsStream();
        partsStream.setEmployeeId(employeeId);
        partsStream.setPartsId(parts.getId());
        partsStream.setPreInventory(0);
        partsStream.setNum(parts.getInventory());
        partsStream.setAfterInventory(parts.getInventory());
        partsStream.setCreateTime(new Date());
        partsStream.setType(PartsStream.PARTSSTREAM_TYPE_NEW);
        partsStreamMapper.insertSelective(partsStream);
    }

    /**
     * 修改
     *
     * @param parts
     */
    @Override
    public void partsEdit(Parts parts) {
        partsMapper.updateByPrimaryKeySelective(parts);
    }

    /**
     * 坚持partsNo是否可用
     *
     * @param partsNo
     */
    @Override
    public void check(String partsNo) {

        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andPartsNoEqualTo(partsNo);
        List<Parts> partsList = partsMapper.selectByExample(partsExample);

        if(partsList.size() != 0){
            throw new NotAllowException("该pageNo已存在");
        }

    }

    /**
     * 增加备件库存
     *  @param partsId
     * @param addNum
     * @param employeeId
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addInventory(Integer partsId, Integer addNum,Integer employeeId) {
        Parts parts = partsMapper.selectByPrimaryKey(partsId);
        Integer preInventory = parts.getInventory();
        parts.setInventory(addNum + preInventory);
        partsMapper.updateByPrimaryKeySelective(parts);

        //记录日志
        logger.debug("employId={}在{}增加了{}的库存{}", employeeId, new Date(),parts.getPartsNo(),addNum);

        //记录数据库流水日志
        PartsStream partsStream = new PartsStream();
        partsStream.setEmployeeId(employeeId);
        partsStream.setPartsId(partsId);
        partsStream.setPreInventory(preInventory);
        partsStream.setNum(addNum);
        partsStream.setAfterInventory(parts.getInventory());
        partsStream.setCreateTime(new Date());
        partsStream.setType(PartsStream.PARTSSTREAM_TYPE_IN);
        partsStreamMapper.insertSelective(partsStream);
    }

    /**
     * 查所有配件列表
     *
     * @return
     */
    @Override
    public List<Parts> selectAllParts() {
        PartsExample partsExample = new PartsExample();
        return partsMapper.selectByExample(partsExample);
    }

    /**
     * 查所有配件(带类型信息)列表
     *
     * @return
     */
    @Override
    public List<Parts> selectAllPartsWithType() {
        return partsMapper.findPage();
    }

    /**
     * 根据类型id查找配件列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Parts> findPartsByTypeId(int id) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andTypeIdEqualTo(id);
        return partsMapper.selectByExample(partsExample);
    }

    /**
     * 配件入库查询
     *
     * @param pageNo
     * @param map
     * @return
     */
    @Override
    public PageInfo<PartsStream> selectInPage(Integer pageNo, Map<String, String> map) {
        PageHelper.startPage(pageNo,Constant.DEFAULT_PAGE_SIZE);
        List<PartsStream> partsList = partsStreamMapper.selectInPartsList(map);
        PageInfo<PartsStream> partsPageInfo = new PageInfo<>(partsList);
        return partsPageInfo;
    }

    /**
     * 配件出库查询
     *
     * @param pageNo
     * @param map
     * @return
     */
    @Override
    public PageInfo<PartsStream> selectOutPage(Integer pageNo, Map<String, String> map) {
        PageHelper.startPage(pageNo,Constant.DEFAULT_PAGE_SIZE);
        List<PartsStream> partsList = partsStreamMapper.selectOutPartsList(map);
        PageInfo<PartsStream> partsPageInfo =  new PageInfo<>(partsList);
        return partsPageInfo;
    }



    /**
     * 查找类型商品类型列表
     *
     * @return
     */
    @Override
    public List<Type> findTypes() {
        TypeExample typeExample = new TypeExample();
        return typeMapper.selectByExample(typeExample);
    }

    /**
     * 删除类型
     *
     * @param id
     */
    @Override
    public void typeDel(int id) {
        PartsExample partsExample = new PartsExample();
        partsExample.createCriteria().andTypeIdEqualTo(id);
        List<Parts> partsList = partsMapper.selectByExample(partsExample);
        if(partsList.size() != 0){
            throw new NotAllowException("该类型下有产品");
        }else{
            typeMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 新增类型
     *
     * @param typeName
     */
    @Override
    public void typeNew(String typeName) {
        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andTypeNameEqualTo(typeName);
        List<Type> typeList = typeMapper.selectByExample(typeExample);
        if(typeList.size() != 0){
            throw new NotAllowException("该类型已存在");
        }else{
            Type type = new Type();
            type.setTypeName(typeName);
            typeMapper.insertSelective(type);
        }
    }

}
