package com.haruspring.sample.dynamodb.sdk.domain.service.impl;

import com.haruspring.sample.dynamodb.sdk.domain.model.ProductCatalogResponce;
import com.haruspring.sample.dynamodb.sdk.domain.service.ProductCatalogService;
import com.haruspring.sample.dynamodb.sdk.repository.ProductCatalogRepository;
import com.haruspring.sample.dynamodb.sdk.repository.dao.ProductCatalogDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** ProductCatalogに関するサービスの実装クラス。 */
@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

  @Autowired private ProductCatalogRepository repository;

  /**
   * すべてのProductCatalogを取得する。
   *
   * @return すべてのProductCatalog
   */
  @Override
  public List<ProductCatalogResponce> getList() {
    List<ProductCatalogDao> items = repository.scanAllItems();

    List<ProductCatalogResponce> responses = new ArrayList<>();

    for (ProductCatalogDao item : items) {
      ProductCatalogResponce response = new ProductCatalogResponce();
      response.setId(item.getId());
      response.setBrand(item.getBrand());
      response.setDescription(item.getDescription());
      response.setPrice(item.getPrice());
      response.setColor(item.getColor());
      response.setProductCategory(item.getProductCategory());
      response.setTitle(item.getTitle());
      response.setInPublication(item.getInPublication());
      response.setIsbn(item.getIsbn());
      response.setPageCount(item.getPageCount());
      response.setAuthors(item.getAuthors());
      response.setDimensions(item.getDimensions());

      responses.add(response);
    }

    return responses;
  }
}
