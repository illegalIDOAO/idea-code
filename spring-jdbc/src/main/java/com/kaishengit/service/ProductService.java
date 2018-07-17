package com.kaishengit.service;

import com.kaishengit.dao.ProductDao;
import com.kaishengit.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 17:45 2018/7/17
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

/*    @Transactional(rollbackFor = RuntimeException.class)
    public void saveList(List<Product> productList) throws Exception {
        try{
            for(Product product : productList){
                productDao.save(product);
            }
        }catch(RuntimeException e){
            //测试验证rallbackFor=RuntimeException.class(默认)时会导致事务失败
            throw  new  Exception();
        }
    }*/

    @Transactional(rollbackFor = Exception.class)
    public void saveList(List<Product> productList){
        for(Product product : productList){
            productDao.save(product);
        }
    }
}
