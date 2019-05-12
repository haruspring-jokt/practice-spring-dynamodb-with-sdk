package com.haruspring.sample.dynamodb.sdk.repository.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.List;
import lombok.Data;

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

  @DynamoDBIgnore private String someProp;
}
