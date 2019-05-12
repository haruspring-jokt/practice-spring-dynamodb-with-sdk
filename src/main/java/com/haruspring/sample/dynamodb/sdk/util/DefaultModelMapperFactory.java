package com.haruspring.sample.dynamodb.sdk.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** ModelMapper設定クラス */
@Configuration
@ComponentScan("com.haruspring.sample.dynamodb.sdk")
public class DefaultModelMapperFactory {

  /**
   * ModelMapperを設定した上でDIコンテナに登録する。
   *
   * @return 設定済みModelMapperオブジェクト
   */
  @Bean
  public ModelMapper modelMapper() {

    ModelMapper mapper = new ModelMapper();

    // 厳格なマッピングルールを適用
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    return new ModelMapper();
  }
}
