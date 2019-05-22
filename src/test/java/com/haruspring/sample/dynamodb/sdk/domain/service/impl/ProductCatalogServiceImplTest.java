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
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCatalogServiceImplTest {

  @Mock private ProductCatalogRepository repository;

  @InjectMocks private ProductCatalogServiceImpl service;

  @Mock ModelMapper modelMapper;

  @Test
  public void getListShouldReturnProducts() throws Exception {
    ProductCatalogDao dao1 = new ProductCatalogDao().withId(201);
    ProductCatalogDao dao2 = new ProductCatalogDao().withId(202);
    when(repository.scanAllItems()).thenReturn(asList(dao1, dao2));
    ProductCatalogResponse res1 = new ProductCatalogResponse();
    ProductCatalogResponse res2 = new ProductCatalogResponse();
    when(modelMapper.map(dao1, ProductCatalogResponse.class)).thenReturn(res1);
    when(modelMapper.map(dao2, ProductCatalogResponse.class)).thenReturn(res2);
    List<ProductCatalogResponse> result = service.getList();
    assertEquals(asList(res1, res2), result);
  }
}
