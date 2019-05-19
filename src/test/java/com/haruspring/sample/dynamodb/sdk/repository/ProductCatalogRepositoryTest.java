package com.haruspring.sample.dynamodb.sdk.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductCatalogRepositoryTest {

  @Autowired private ProductCatalogRepository repository;

  @Test
  @Ignore
  public void scanAllItemsTest() {
    repository.scanAllItems();
  }

  @Test
  @Ignore
  public void scanItemsByLowestIdTest() {
    repository.scanItemsByLowestId("200");
  }

  @Test
  @Ignore
  public void saveItemTest() {
    repository.saveItem(301);
  }

  @Test
  @Ignore
  public void selectOneTest() {
    repository.selectOne(301);
  }

  @Test
  @Ignore
  public void loadOneTest() {
    repository.loadOne(301);
  }

  @Test
  @Ignore
  public void deleteOneTest() {
    repository.deleteOne(301);
  }
}
