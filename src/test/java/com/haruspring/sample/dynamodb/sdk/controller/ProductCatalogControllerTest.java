package com.haruspring.sample.dynamodb.sdk.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductCatalogControllerTest {

  @Autowired private MockMvc mockMvc;

  /**
   * 以下の形式で返却されることをテストする
   *
   * <pre>
   * [
   * {
   * "id": 201,
   * "brand": "Mountain A",
   * "description": "201 Description",
   * "price": 100,
   * "color":[
   * "Red",
   * "Black"
   * ],
   * "productCategory": "Bicycle",
   * "title": "18-Bike-201",
   * "bicycleType": "Road",
   * "inPublication": null,
   * "isbn": null,
   * "pageCount": null,
   * "authors": null,
   * "dimensions": null
   * },
   * // omit
   * </pre>
   */
  @Test
  public void listProductCatalogResponsesTest() throws Exception {
    mockMvc
        .perform(get("/api/v1/product/catalog/list"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(9)))
        .andExpect(jsonPath("$[0].id").value(201))
        .andExpect(jsonPath("$[0].brand").value("Mountain A"))
        .andExpect(jsonPath("$[0].description").value("201 Description"))
        .andExpect(jsonPath("$[0].color[0]").value("Red"))
        .andExpect(jsonPath("$[0].color[1]").value("Black"))
        .andExpect(jsonPath("$[0].productCategory").value("Bicycle"))
        .andExpect(jsonPath("$[0].title").value("18-Bike-201"))
        .andExpect(jsonPath("$[0].bicycleType").value("Road"))
        .andExpect(jsonPath("$[0].inPublication").isEmpty())
        .andExpect(jsonPath("$[0].isbn").isEmpty())
        .andExpect(jsonPath("$[0].pageCount").isEmpty())
        .andExpect(jsonPath("$[0].authors").isEmpty())
        .andExpect(jsonPath("$[0].dimensions").isEmpty());
  }
}
