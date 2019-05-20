package com.haruspring.sample.dynamodb.sdk.controller;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.haruspring.sample.dynamodb.sdk.domain.model.ProductCatalogResponse;
import com.haruspring.sample.dynamodb.sdk.domain.service.ProductCatalogService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductCatalogControllerTest {

  @Mock private ProductCatalogService service;

  @InjectMocks private ProductCatalogController controller;

  @Test
  public void listAllTest() throws Exception {
    ProductCatalogResponse response1 =
        new ProductCatalogResponse(
            201,
            "brand",
            "desc",
            1000,
            asList("red", "yellow"),
            "category",
            "title",
            "bycicleType",
            true,
            "isbn",
            100,
            asList("name1", "name2"),
            "dimensions");
    ProductCatalogResponse response2 =
        new ProductCatalogResponse(
            202,
            "brand",
            "desc",
            1000,
            asList("red", "yellow"),
            "category",
            "title",
            "bycicleType",
            true,
            "isbn",
            100,
            asList("name1", "name2"),
            "dimensions");
    when(service.getList()).thenReturn(asList(response1, response2));
    List<ProductCatalogResponse> result = controller.listProductCatalogResponses();
    assertEquals(result, asList(response1, response2));
  }
}
