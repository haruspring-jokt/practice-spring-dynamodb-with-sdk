package com.haruspring.sample.dynamodb.sdk.domain.service;

import com.haruspring.sample.dynamodb.sdk.domain.model.ProductCatalogResponse;
import java.util.List;

public interface ProductCatalogService {

  public List<ProductCatalogResponse> getList();
}
