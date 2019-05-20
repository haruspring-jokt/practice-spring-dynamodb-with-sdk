package com.haruspring.sample.dynamodb.sdk.repository.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.haruspring.sample.dynamodb.sdk.repository.ProductCatalogRepository;
import com.haruspring.sample.dynamodb.sdk.repository.dao.ProductCatalogDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ProductCatalogテーブルに対応するリポジトリクラス。
 *
 * @author haruspring-jokt
 */
@Repository
public class ProductCatalogRepositoryImpl implements ProductCatalogRepository {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private DynamoDBMapper dbMapper;

  /** ProductCatalog内のItemをすべて取得する。 */
  public List<ProductCatalogDao> scanAllItems() {

    List<ProductCatalogDao> items =
        dbMapper.scan(ProductCatalogDao.class, new DynamoDBScanExpression());

    return items;
  }

  /**
   * ProductCatalog内のIdがlowestId以上のItemをすべて取得する。
   *
   * @param lowestId Scan対象となる最低のId値
   */
  public void scanItemsByLowestId(String lowestId) {

    Map<String, AttributeValue> eav = new HashMap<>();
    eav.put(":val1", new AttributeValue().withN(lowestId));

    DynamoDBScanExpression scanExpression =
        new DynamoDBScanExpression()
            .withFilterExpression("Id >= :val1")
            .withExpressionAttributeValues(eav);

    List<ProductCatalogDao> items = dbMapper.scan(ProductCatalogDao.class, scanExpression);

    for (ProductCatalogDao item : items) {
      System.out.println(item.getTitle());
    }
  }

  /**
   * 1件のアイテムを追加する。 mapperメソッドを使用すると、存在しないItemを作成するか、 存在する場合は同じIDのItemを更新する。
   *
   * @author haruspring-jokt
   */
  public void saveItem(Integer id) {

    ProductCatalogDao item = new ProductCatalogDao();

    item.setId(id);
    item.setTitle("Book 301 Title");
    item.setIsbn("333-3333333333");
    item.setAuthors(new ArrayList<>(Arrays.asList("Author 11", "Author 12")));
    item.setSomeProp("Test");

    dbMapper.save(item);
  }

  /**
   * queryメソッドを使用して、1件のアイテムを取得する。
   *
   * @param id Id
   */
  public void selectOne(Integer id) {

    ProductCatalogDao partitionKey = new ProductCatalogDao();

    partitionKey.setId(id);
    DynamoDBQueryExpression<ProductCatalogDao> queryExpression =
        new DynamoDBQueryExpression<ProductCatalogDao>().withHashKeyValues(partitionKey);

    List<ProductCatalogDao> itemList = dbMapper.query(ProductCatalogDao.class, queryExpression);

    for (ProductCatalogDao productCatalogDao : itemList) {
      logger.debug(productCatalogDao.toString());
    }
  }

  /**
   * loadメソッドを使用して、1件のアイテムを取得する。
   *
   * @param id Id
   */
  @Override
  public ProductCatalogDao loadOne(Integer id) {
    ProductCatalogDao item = dbMapper.load(ProductCatalogDao.class, id);
    return item;
  }

  /**
   * 1件のアイテムを削除する.
   *
   * @param id Id
   */
  @Override
  public void deleteOne(Integer id) {
    dbMapper.delete(new ProductCatalogDao().withId(201));
  }
}
