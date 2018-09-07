package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.Product;
import com.kaishengit.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chuzhaohui
 * @Date: Created in 11:23 2018/8/24
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/find")
    @ResponseBody
    public List<Product> findProduct(Model model){
        List<Product> productList = productService.selectByPage();
        return productList;
    }
    @GetMapping("/findPage")
    @ResponseBody
    public PageInfo<Product> findProduct2(Model model){
        PageInfo<Product> productPageInfo = productService.selectByPage2();
        return productPageInfo;
    }

    @GetMapping("/count")
    public String count(Model model){
        Integer count = productService.count();
        model.addAttribute("count",count);
        return "news";
    }

    @GetMapping("/{id:\\d+}/select")
    @ResponseBody
    public Product selectById(@PathVariable Integer id){
        return productService.selectById(id);
    }
    @GetMapping("/{id:\\d+}/del")
    public String delById(@PathVariable Integer id){
        productService.delById(id);
        return "success";
    }
}
