package com.haruspring.sample.dynamodb.sdk.domain.service.impl;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.haruspring.sample.dynamodb.sdk.domain.model.ProductCatalogResponse;
import com.haruspring.sample.dynamodb.sdk.repository.ProductCatalogRepository;
import com.haruspring.sample.dynamodb.sdk.repository.dao.ProductCatalogDao;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductCatalogServiceImplTest {

  @Mock private ProductCatalogRepository repository;

  @InjectMocks private ProductCatalogServiceImpl service;

  @Test
  public void getListShouldReturnProducts() throws Exception {
    ProductCatalogDao dao1 = new ProductCatalogDao().withId(201);
    ProductCatalogDao dao2 = new ProductCatalogDao().withId(202);
    when(repository.scanAllItems()).thenReturn(asList(dao1, dao2));
    List<ProductCatalogResponse> result = service.getList();
    assertEquals(result, asList(dao1, dao2));
  }
}
