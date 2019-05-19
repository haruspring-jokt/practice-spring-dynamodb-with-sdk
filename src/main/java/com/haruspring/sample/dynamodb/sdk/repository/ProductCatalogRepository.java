package com.haruspring.sample.dynamodb.sdk.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.haruspring.sample.dynamodb.sdk.config.DynamoDBConfig;
import com.haruspring.sample.dynamodb.sdk.repository.dao.ProductCatalogDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ProductCatalogテーブルに対応するリポジトリクラス。
 *
 * @author haruspring-jokt
 */
@Repository
public class ProductCatalogRepository extends DynamoDBConfig {

  @Autowired private DynamoDBMapper dbMapper;

  /** ProductCatalog内のItemをすべて取得する。 */
  public List<ProductCatalogDao> scanAllItems() {
    DynamoDBMapper mapper = super.setupMapper();

    List<ProductCatalogDao> items =
        mapper.scan(ProductCatalogDao.class, new DynamoDBScanExpression());

    return items;
  }

  /**
   * ProductCatalog内のIdがlowestId以上のItemをすべて取得する。
   *
   * @param lowestId Scan対象となる最低のId値
   */
  public void scanItemsByLowestId(String lowestId) {
    DynamoDBMapper mapper = super.setupMapper();

    Map<String, AttributeValue> eav = new HashMap<>();
    eav.put(":val1", new AttributeValue().withN(lowestId));

    DynamoDBScanExpression scanExpression =
        new DynamoDBScanExpression()
            .withFilterExpression("Id >= :val1")
            .withExpressionAttributeValues(eav);

    List<ProductCatalogDao> items = mapper.scan(ProductCatalogDao.class, scanExpression);

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
    DynamoDBMapper mapper = super.setupMapper();

    ProductCatalogDao item = new ProductCatalogDao();

    item.setId(id);
    item.setTitle("Book 301 Title");
    item.setIsbn("333-3333333333");
    item.setAuthors(new ArrayList<>(Arrays.asList("Author 11", "Author 12")));
    item.setSomeProp("Test");

    mapper.save(item);
  }

  /**
   * queryメソッドを使用して、1件のアイテムを取得する。
   *
   * @param id Id
   */
  public void selectOne(Integer id) {
    DynamoDBMapper mapper = super.setupMapper();

    ProductCatalogDao partitionKey = new ProductCatalogDao();

    partitionKey.setId(id);
    DynamoDBQueryExpression<ProductCatalogDao> queryExpression =
        new DynamoDBQueryExpression<ProductCatalogDao>().withHashKeyValues(partitionKey);

    List<ProductCatalogDao> itemList = mapper.query(ProductCatalogDao.class, queryExpression);

    for (ProductCatalogDao productCatalogDao : itemList) {
      System.out.println(productCatalogDao.getTitle());
      System.out.println(productCatalogDao.getAuthors());
    }
  }

  //  /**
  //   * loadメソッドを使用して、1件のアイテムを取得する。
  //   *
  //   * @param id Id
  //   */
  //  public void loadOne(Integer id) {
  //    DynamoDBMapper mapper = super.setupMapper();
  //
  //    ProductCatalogDao item = mapper.load(ProductCatalogDao.class, id);
  //    System.out.println(item);
  //    return item;
  //  }

  /**
   * loadメソッドを使用して、1件のアイテムを取得する。
   *
   * @param id Id
   */
  public ProductCatalogDao loadOne(Integer id) {
    //    DynamoDBMapper mapper = super.setupMapper();

    ProductCatalogDao item = dbMapper.load(ProductCatalogDao.class, id);
    System.out.println(item);
    return item;
  }

  /**
   * 1件のアイテムを削除する。
   *
   * @param id Id
   */
  public void deleteOne(Integer id) {
    //    DynamoDBMapper mapper = super.setupMapper();

    ProductCatalogDao item = dbMapper.load(ProductCatalogDao.class, id);

    dbMapper.delete(new ProductCatalogDao().withId(201));
  }
}
