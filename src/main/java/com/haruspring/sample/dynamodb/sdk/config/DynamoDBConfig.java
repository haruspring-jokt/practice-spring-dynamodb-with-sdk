package com.haruspring.sample.dynamodb.sdk.config;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.ConsistentReads;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.PaginationLoadingStrategy;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import org.springframework.beans.factory.annotation.Value;

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
