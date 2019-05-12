## dynamoDB の CRUD 操作

以下を参考に mapper を使ったデータアクセスを実装する。

https://docs.aws.amazon.com/ja_jp/amazondynamodb/latest/developerguide/HigherLevelInterfaces.html

### 設定クラスの作成

```java
package com.haruspring.sample.dynamodb.sdk.config;

/** DynamoDBの設定クラス。 */
public class DynamoDBConfig {

  @Value("${amazon.dynamodb.endpoint}")
  private String amazonDynamoDBEndpoint;

  @Value("${amazon.dynamodb.region}")
  private String amazonDynamoDBRegion;

  /**
   * DynamoDBMapperのセットアップを行う。 appilcation.ymlにamazon.dynamodb.endpoint,
   * amazon.dynamodb.regionを設定している必要がある。
   *
   * @return DynamoDBMapperオブジェクト
   */
  protected DynamoDBMapper setupMapper() {
    AmazonDynamoDB client =
        AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(
                new EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion))
            .build();

    DynamoDBMapperConfig mapperConfig =
        DynamoDBMapperConfig.builder()
            .withSaveBehavior(SaveBehavior.CLOBBER)
            .withConsistentReads(ConsistentReads.CONSISTENT)
            .withTableNameOverride(null)
            .withPaginationLoadingStrategy(PaginationLoadingStrategy.LAZY_LOADING)
            .build();

    return new DynamoDBMapper(client, mapperConfig);
  }
}
```

### dao

DB の取得結果を格納する dao を作成する。DynamoDB とマッピングするための設定を行う。

```java
package com.haruspring.sample.dynamodb.sdk.repository.dao;

/**
 * ProductCatalogテーブルのマッピング用オブジェクト。
 *
 * @author haruspring-jokt
 */
@Data
@DynamoDBTable(tableName = "ProductCatalog")
public class ProductCatalogDao {

  @DynamoDBHashKey(attributeName = "Id")
  private Integer id;

  @DynamoDBAttribute(attributeName = "Brand")
  private String brand;

  @DynamoDBAttribute(attributeName = "Description")
  private String description;

  @DynamoDBAttribute(attributeName = "Price")
  private Integer price;

  @DynamoDBAttribute(attributeName = "Color")
  private List<String> color;

  @DynamoDBAttribute(attributeName = "ProductCategory")
  private String productCategory;

  @DynamoDBAttribute(attributeName = "Title")
  private String title;

  @DynamoDBAttribute(attributeName = "BicycleType")
  private String bicycleType;

  @DynamoDBAttribute(attributeName = "InPublication")
  private Boolean inPublication;

  @DynamoDBAttribute(attributeName = "ISBN")
  private String isbn;

  @DynamoDBAttribute(attributeName = "PageCount")
  private Integer pageCount;

  @DynamoDBAttribute(attributeName = "Authors")
  private List<String> authors;

  @DynamoDBAttribute(attributeName = "Dimensions")
  private String dimensions;

  // DynamoDBからは無視されるフィールド
  @DynamoDBIgnore private String someProp;
}
```

### CRUD

`ProductCatalogRepository`は、先程つくった`DynamoDBConfig`を継承する。

#### scan

```java
  /** ProductCatalog内のItemをすべて取得する。 */
  public List<ProductCatalogDao> scanAllItems() {
    DynamoDBMapper mapper = super.setupMapper();

    List<ProductCatalogDao> items =
        mapper.scan(ProductCatalogDao.class, new DynamoDBScanExpression());

    return items;
  }
```

`DynamoDBScanExpression`にフィルタなどを設定できる。

```java
    Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
    eav.put(":val1", new AttributeValue().withN(lowestId));

    DynamoDBScanExpression scanExpression =
        new DynamoDBScanExpression()
            .withFilterExpression("Id >= :val1")
            .withExpressionAttributeValues(eav);
```

#### save

テーブルに 1 件保存する。

```java
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
```

実行すると 1 件のレコードが保存される。

![](img/2019-05-12-13-33-15.png)

#### query

SQL で言う SQLECT 文の発行。

```java
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
```

```
Book 301 Title
[Author 1, Author 2]
```

#### delete

`load`してItemを取得してから`delete`する。

```java
  /**
   * 1件のアイテムを削除する。
   *
   * @param id Id
   */
  public void deleteOne(Integer id) {
    DynamoDBMapper mapper = super.setupMapper();

    ProductCatalogDao item = mapper.load(ProductCatalogDao.class, id);

    mapper.delete(item);
  }
```
