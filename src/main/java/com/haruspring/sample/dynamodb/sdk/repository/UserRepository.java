package com.haruspring.sample.dynamodb.sdk.repository;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.haruspring.sample.dynamodb.sdk.domain.model.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

public class UserRepository {

  @Value("${amazon.dynamodb.endpoint}")
  private String amazonDynamoDBEndpoint;

  @Value("${amazon.aws.accesskey}")
  private String amazonAWSAccessKey;

  @Value("${amazon.aws.secretkey}")
  private String amazonAWSSecretKey;

  @Value("${amazon.dynamodb.region}")
  private String amazonDynamoDBRegion;

  AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
      .withEndpointConfiguration(new EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion))
      .build();

  final String userTableName = "User";

//  public User listUser() {
//
//    return
//  }

  public void createUser(String id, String name) {

    DynamoDB dynamoDB = new DynamoDB(client);

    Table table = dynamoDB.getTable(userTableName);

//    final Map<String, String> userMap = new HashMap<>();
//    userMap.put("id", id);
//    userMap.put("name", name);

    PutItemOutcome outcome = table
        .putItem(new Item()
            .withPrimaryKey("id", id).withString("name", name));
  }

}
