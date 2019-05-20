package com.haruspring.sample.dynamodb.sdk.repository;

import static org.junit.Assert.assertEquals;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** テスト用のDynamoDB localが作成されていることを検証するクラス. 現在使用していないためスキップする. sqlite4がないので動かない. */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class DynamoDBEmbeddedTest {

  private AmazonDynamoDB dynamoDB;

  @Before
  public void createDB() {
    AwsDynamoDbLocalTestUtils.initSqLite();
    dynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
  }

  @After
  public void shutdownDB() {
    dynamoDB.shutdown();
  }

  @Test
  @Ignore
  public void test__tableIsCreatedEquallyWithSetting() {

    final String tableName = "Stationery";
    final String hashKeyName = "item_id";
    final long readCapacityUnits = 1000L;
    final long writeCapacityUnits = 1000L;

    CreateTableResult result =
        dynamoDB.createTable(
            new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(
                    new KeySchemaElement().withAttributeName(hashKeyName).withKeyType(KeyType.HASH))
                .withAttributeDefinitions(
                    new AttributeDefinition()
                        .withAttributeName(hashKeyName)
                        .withAttributeType(ScalarAttributeType.S))
                .withProvisionedThroughput(
                    new ProvisionedThroughput(readCapacityUnits, writeCapacityUnits)));

    TableDescription tableDesc = result.getTableDescription();

    assertEquals(tableName, tableDesc.getTableName());
  }
}
