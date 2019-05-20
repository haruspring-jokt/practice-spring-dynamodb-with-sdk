package com.haruspring.sample.dynamodb.sdk.repository;

import com.haruspring.sample.dynamodb.sdk.repository.dao.ProductCatalogDao;
import java.util.List;

public interface ProductCatalogRepository {

  public List<ProductCatalogDao> scanAllItems();

  public void scanItemsByLowestId(String lowestId);

  public void saveItem(Integer id);

  public void selectOne(Integer id);

  public ProductCatalogDao loadOne(Integer id);

  public void deleteOne(Integer id);
}
