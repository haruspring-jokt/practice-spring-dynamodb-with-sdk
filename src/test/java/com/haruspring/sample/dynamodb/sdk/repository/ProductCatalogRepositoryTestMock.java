package com.haruspring.sample.dynamodb.sdk.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.haruspring.sample.dynamodb.sdk.repository.dao.ProductCatalogDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductCatalogRepositoryTestMock {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Mock private DynamoDBMapper dbMapper;

  @InjectMocks private ProductCatalogRepository repository;

  @Test
  public void test__loadOne() {
    ProductCatalogDao dao = new ProductCatalogDao();
    dao.setId(201);
    logger.debug(dao.toString());
    when(dbMapper.load(ProductCatalogDao.class, 201)).thenReturn(dao);
    ProductCatalogDao result = repository.loadOne(201);
    logger.debug(result.toString());
    assertThat(result, is(equalTo(dao)));
  }

  @Test
  public void test__deleteOne() {
    repository.deleteOne(201);
    verify(dbMapper).delete(eq(new ProductCatalogDao().withId(201)));
  }
}
