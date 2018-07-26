package com.kaishengit.service.impl;

import com.kaishengit.entity.Parts;
import com.kaishengit.entity.PartsExample;
import com.kaishengit.entity.Type;
import com.kaishengit.entity.TypeExample;
import com.kaishengit.exception.NotAllowException;
import com.kaishengit.mapper.PartsMapper;
import com.kaishengit.mapper.TypeMapper;
import com.kaishengit.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 14:40 2018/7/24
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private PartsMapper partsMapper;

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
