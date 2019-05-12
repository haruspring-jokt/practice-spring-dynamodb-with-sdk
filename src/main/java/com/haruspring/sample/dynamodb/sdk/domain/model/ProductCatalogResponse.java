package com.haruspring.sample.dynamodb.sdk.domain.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCatalogResponse {

  private Integer id;

  private String brand;

  private String description;

  private Integer price;

  private List<String> color;

  private String productCategory;

  private String title;

  private String bicycleType;

  private Boolean inPublication;

  private String isbn;

  private Integer pageCount;

  private List<String> authors;

  private String dimensions;
}
