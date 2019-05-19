## dynamodb-localを使用したリポジトリ層のテスト

### 概要

DynamoDBを使用したリポジトリ層の単体テストを、AWS上のDBを使用せずにローカル環境におけるDynamoDB localを使用しておこなう。なおかつ、DynamoDB localをいちいち作成・メンテするのは面倒なので、Gradleなどで管理する。

さらにJUnit5を使用する（Spring InitializerはJUnit4をデフォルトとしている）。

### 参考

- https://www.yo1000.com/dynamo-db-local%E3%82%92%E4%BD%BF%E7%94%A8%E3%81%97%E3%81%9F%E3%83%86%E3%82%B9%E3%83%88
- https://github.com/redskap/aws-dynamodb-java-example-local-testing
- https://github.com/sbrannen/junit5-demo/blob/master/build.gradle
- https://stackoverflow.com/questions/34401039/run-dynamodb-local-as-part-of-a-gradle-java-project

### build.gradle

DynamoDB localとJUnit5用の依存性を追加する。

```groovy
repositories {
    mavenCentral()
    // DynamoDB localはMaven Centralにアップロードされていないので、追加リポジトリを指定する。
    maven {
        name "DynamoDB Local Release Repository - Asia Pacific (Tokyo) Region"
        url "https://s3-ap-northeast-1.amazonaws.com/dynamodb-local-tokyo/release"
    }
}

dependencies {

    // 省略

    // for repository layer test using DynamoDB local
    testCompile group: 'com.amazonaws', name: 'DynamoDBLocal', version: '1.11.477'

    // for use JUnit5
    compile("org.junit.jupiter:junit-jupiter-api")
    testCompile("org.junit.jupiter:junit-jupiter-params")
    testCompile("org.junit.jupiter:junit-jupiter-migrationsupport")
    testCompile("org.junit.platform:junit-platform-runner")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
    testRuntime("org.junit.vintage:junit-vintage-engine")
}

// for JUnit5 and print test logs
test {
    useJUnitPlatform()
    testLogging {
        events "passed", "skipped", "failed"
    }
}
```

### sqlite4の使用

[引用元](https://www.yo1000.com/dynamo-db-local%E3%82%92%E4%BD%BF%E7%94%A8%E3%81%97%E3%81%9F%E3%83%86%E3%82%B9%E3%83%88)より

> なぜDynamoDBを使うのに、SQLiteが出てくるのか、という部分ですが、DynamoDB Localでは、
>
> - DynamoDBとインターフェースが等しいこと
> - データを永続化できること
> - の2点が満たせればよいだけなので、内部実装としては、SQLiteを使用した永続化が行われています。そのため、この依存が必要になってきます。

引用元ではこのSQLiteの依存性を`build.gradle`に追記することで解決するが、これでは`gradle`(`graldew`)コマンドによるテストに対応できるが、IDEからのテストには対応できなくなってしまう。そのため、[https://github.com/redskap/aws-dynamodb-java-example-local-testing](https://github.com/redskap/aws-dynamodb-java-example-local-testing)を参考にして、`AwsDynamoDbLocalTestUtils.java`を作成し、それぞれのテストクラスでは`initSqlite()`を実行することで解決する方法が紹介されていたため、これを流用した。

- [AwsDynamoDbLocalTestUtils.java](..\src\test\java\com\haruspring\sample\dynamodb\sdk\repository\AwsDynamoDbLocalTestUtils.java)
- テストクラスの実装: [DynamoDBEmbeddedTest.java](..\src\test\java\com\haruspring\sample\dynamodb\sdk\repository\DynamoDBEmbeddedTest.java)

### JUnit5

Spring Boot2に関連する注意点としては以下。ほかはJUnit5そのものの使い方に習う。

- `@RunWith(SpringRunner.class)`の代わりに`@ExtendWith(SpringExtension.class)`を使用する
