package com.haruspring.sample.dynamodb.sdk.controller;

import com.haruspring.sample.dynamodb.sdk.domain.model.ProductCatalogResponce;
import com.haruspring.sample.dynamodb.sdk.domain.service.ProductCatalogService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product/catalog")
public class ProductCatalogController {

  @Autowired private ProductCatalogService service;

  @GetMapping("list")
  public List<ProductCatalogResponce> listProductCatalogResponces() {

    return service.getList();
  }
}
