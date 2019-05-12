package com.haruspring.sample.dynamodb.sdk.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCatalogRepositoryTest {

  @Autowired private ProductCatalogRepository repository;

  @Test
  public void scanAllItemsTest() {
    repository.scanAllItems();
  }

  @Test
  public void scanItemsByLowestIdTest() {
    repository.scanItemsByLowestId("200");
  }

  @Test
  public void saveItemTest() {
    repository.saveItem(301);
  }

  @Test
  public void selectOneTest() {
    repository.selectOne(301);
  }

  @Test
  public void loadOneTest() {
    repository.loadOne(301);
  }

  @Test
  public void deleteOneTest() {
    repository.deleteOne(301);
  }
}
