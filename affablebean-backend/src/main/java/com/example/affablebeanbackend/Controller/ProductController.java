package com.example.affablebeanbackend.Controller;

import com.example.affablebeanbackend.dao.ProductDao;
import com.example.affablebeanbackend.entity.Product;
import com.example.affablebeanbackend.entity.Products;
import com.example.affablebeanbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/backend")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductDao productDao;
    @GetMapping("/products")
    public Products listProduct(){
        return productService.listAllProduct();
    }
    @GetMapping("/product/all")
    public Iterable<Product> products(){
        return productDao.findAll();
    }
}
