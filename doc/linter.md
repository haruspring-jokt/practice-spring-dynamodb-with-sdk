# swagger

## swagger editor on Docker

```sh
docker pull swaggerapi/swagger-editor
docker run -d -p 81:8080 --name swe swaggerapi/swagger-editor
```

http://192.168.99.100:81/ にswagger editor 環境が作成される。

## Gradle Swagger Generator Plugin

# formatter

## google-java-format

`build.gradle`に1行追加 https://plugins.gradle.org/plugin/com.github.sherter.google-java-format を参照

```groovy
plugins {
	id 'com.github.sherter.google-java-format' version "0.8"
}
```

- `Gradle > Tasks > other > googleJavaFormat` で全ファイルをフォーマットする。
- `Gradle > Tasks > other > verifyGoogleJavaFormat` でフォーマット漏れがないか確認する。


