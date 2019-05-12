package com.haruspring.sample.dynamodb.sdk.domain.service;

import com.haruspring.sample.dynamodb.sdk.domain.model.ProductCatalogResponce;
import java.util.List;

public interface ProductCatalogService {

  public List<ProductCatalogResponce> getList();
}
