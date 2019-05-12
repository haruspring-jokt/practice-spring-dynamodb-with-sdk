package com.haruspring.sample.dynamodb.sdk.domain.service.impl;

import com.haruspring.sample.dynamodb.sdk.domain.model.ProductCatalogResponse;
import com.haruspring.sample.dynamodb.sdk.domain.service.ProductCatalogService;
import com.haruspring.sample.dynamodb.sdk.repository.ProductCatalogRepository;
import com.haruspring.sample.dynamodb.sdk.repository.dao.ProductCatalogDao;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** ProductCatalogに関するサービスの実装クラス。 */
@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

  @Autowired private ProductCatalogRepository repository;

  @Autowired private ModelMapper mapper;

  /**
   * すべてのProductCatalogを取得する。
   *
   * @return すべてのProductCatalog
   */
  @Override
  public List<ProductCatalogResponse> getList() {
    List<ProductCatalogDao> items = repository.scanAllItems();

    List<ProductCatalogResponse> responses = new ArrayList<>();

    for (ProductCatalogDao item : items) {
      ProductCatalogResponse response = mapper.map(item, ProductCatalogResponse.class);
      responses.add(response);
    }

    return responses;
  }
}
