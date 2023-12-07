package com.example.servicioproducto.controller;

import com.example.servicioproducto.entity.Product;
import com.example.servicioproducto.repository.ProductRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    Environment environment;

    @GetMapping("/productos")
    public List<Product> listar(){
        List<Product> productList = productRepository.findAll();
        for(int i = 0; i< productList.size(); i++){
            int port = Integer.parseInt(environment.getProperty("local.server.port"));
            productList.get(i).setPort(port);
        }
        return productList;
    }

    @GetMapping("/productos/{id}")
    public Product buscarPorId(@PathVariable("id") int id) throws InterruptedException {
        if(id == 10){ //forzar un error
            throw new IllegalStateException("Error al buscar el producto");
        }
        if(id == 20){ //forzar un error
            Thread.sleep(5000);
        }

        //caso contrario, todo bien
        Optional<Product> optional = productRepository.findById(id);

        return optional.orElse(null);
    }
}
