package com.kaishengit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Parts;
import com.kaishengit.entity.PartsExample;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.PartsMapper;
import com.kaishengit.service.PartsService;
import com.kaishengit.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: chuzhaohui
 * @Date: Created in 21:36 2018/7/23
 */
@Service
public class PartsServiceImpl implements PartsService {

    @Autowired
    private PartsMapper partsMapper;

    /**
     * 根据id查找
     *
     * @param id
     * @return parts对象
     */
    @Override
    public Parts findParsById(int id) {
        return partsMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询parts
     *
     * @param pageNo
     * @param map
     * @return PageInfo对象
     */
    @Override
    public PageInfo<Parts> findPage(Integer pageNo, Map<String,Object> map) {
        PageHelper.startPage(pageNo,Constant.DEFAULT_PAGE_SIZE);

        List<Parts> parts = partsMapper.findPageByKey(map);

        PageInfo<Parts> partsPageInfo = new PageInfo<>(parts);
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
     */
    @Override
    public void partsNew(Parts parts) {
        partsMapper.insertSelective(parts);
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
}
