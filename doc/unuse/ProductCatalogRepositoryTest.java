package com.haruspring.sample.dynamodb.sdk.repository;

import static org.junit.Assert.assertEquals;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.haruspring.sample.dynamodb.sdk.repository.impl.ProductCatalogRepositoryImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** sqlite4がないので動かない. */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class ProductCatalogRepositoryTest {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private ProductCatalogRepositoryImpl repository;

  private AmazonDynamoDB dynamoDB;

  private final String tableName = "ProductCatalog";

  @Before
  @Ignore
  public void createDB() {
    AwsDynamoDbLocalTestUtils.initSqLite();
    dynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
  }

  private CreateTableResult createTable() {

    final String hashKeyName = "Id";
    final long readCapacityUnits = 1000L;
    final long writeCapacityUnits = 1000L;
    return dynamoDB.createTable(
        new CreateTableRequest()
            .withTableName(tableName)
            .withKeySchema(
                new KeySchemaElement().withAttributeName(hashKeyName).withKeyType(KeyType.HASH))
            .withAttributeDefinitions(
                new AttributeDefinition()
                    .withAttributeName(hashKeyName)
                    .withAttributeType(ScalarAttributeType.N))
            .withProvisionedThroughput(
                new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits)));
  }

  @After
  @Ignore
  public void shutdownDB() {
    dynamoDB.shutdown();
  }

  @Test
  @Ignore
  public void test__scanAllItem() {
    assertEquals("ProductCatalog", tableName);

    Map<String, AttributeValue> item = new HashMap<>();
    item.put("Id", new AttributeValue().withN("201"));
    item.put("brand", new AttributeValue().withS("Mountain A"));
    item.put("price", new AttributeValue().withN("100"));
    item.put(
        "color",
        new AttributeValue()
            .withL(new AttributeValue().withS("Red"), new AttributeValue().withS("Black")));
    item.put("productCategory", new AttributeValue().withS("Bicycle"));
    item.put("title", new AttributeValue().withS("18-Bike-201"));
    item.put("bicycleType", new AttributeValue().withS("Road"));
    item.put("inPublication", new AttributeValue().withNULL(true));
    item.put("isbn", new AttributeValue().withNULL(true));
    item.put("pageCount", new AttributeValue().withNULL(true));
    item.put("authors", new AttributeValue().withL());
    item.put("dimensions", new AttributeValue().withNULL(true));

    //    Item item = new Item()
    //        .withPrimaryKey("Id", 201)
    //        .withString("brand", "Mountain A")
    //        .withNumber("price", 100)
    //        .withList("color", Arrays.asList("Red", "Black"))
    //        .withString("productCategory", "Bicycle")
    //        .withString("title", "18-Bike-201")
    //        .withString("bicycleType", "Road")
    //        .withNull("inPublication")
    //        .withNull("isbn")
    //        .withNull("pageCount")
    //        .withList("authors", new List<String>)
    //        .withNull("dimensions");

    PutItemResult putResult = dynamoDB.putItem(new PutItemRequest(tableName, item));
    logger.debug(putResult.toString());

    repository.selectOne(201);
  }
}
