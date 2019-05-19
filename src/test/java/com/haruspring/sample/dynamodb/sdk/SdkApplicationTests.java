package com.haruspring.sample.dynamodb.sdk;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** JUnit5を使用する場合は{@link ExtendWith}を使用する。 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SdkApplicationTests {

  @Test
  public void contextLoads() {}
}
