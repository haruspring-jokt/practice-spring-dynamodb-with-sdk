## ModelMapper

Bean から Bean への詰替えを簡単にしてくれる。

### build.gradle

```groovy
dependencies {
    // https://mvnrepository.com/artifact/org.modelmapper/modelmapper
	compile group: 'org.modelmapper', name: 'modelmapper', version: '2.3.3'
}
```

### DI コンテナに登録

```java
package com.haruspring.sample.dynamodb.sdk.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper設定クラス
 */
@Configuration
@ComponentScan("com.haruspring.sample.dynamodb.sdk")
public class DefaultModelMapperFactory {

  /**
   * ModelMapperを設定した上でDIコンテナに登録する。
   * @return 設定済みModelMapperオブジェクト
   */
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
```

### 実装例

```java

/** ProductCatalogに関するサービスの実装クラス。 */
@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

  @Autowired private ProductCatalogRepository repository;

  @Autowired private ModelMapper mapper;

  /**
   * すべてのProductCatalogを取得する。
   *
   * @return すべてのProductCatalog
   */
  @Override
  public List<ProductCatalogResponse> getList() {
    List<ProductCatalogDao> items = repository.scanAllItems();

    List<ProductCatalogResponse> responses = new ArrayList<>();

    for (ProductCatalogDao item : items) {
      ProductCatalogResponse response = mapper.map(item, ProductCatalogResponse.class);
      responses.add(response);
    }

    return responses;
  }
```
