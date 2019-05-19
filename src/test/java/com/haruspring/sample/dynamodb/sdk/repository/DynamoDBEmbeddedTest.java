package com.haruspring.sample.dynamodb.sdk.repository;

import static org.junit.jupiter.api.Assertions.*;

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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** テスト用のDynamoDB localが作成されていることを検証するクラス. */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DynamoDBEmbeddedTest {

  private AmazonDynamoDB dynamoDB;

  @BeforeEach
  public void createDB() {
    AwsDynamoDbLocalTestUtils.initSqLite();
    dynamoDB = DynamoDBEmbedded.create().amazonDynamoDB();
  }

  @AfterEach
  public void shutdownDB() {
    dynamoDB.shutdown();
  }

  @Test
  @DisplayName("DynamoDB localのテーブルが作成されていること")
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
