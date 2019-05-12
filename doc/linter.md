# swagger

## swagger editor on Docker

```sh
docker pull swaggerapi/swagger-editor
docker run -d -p 81:8080 --name swe swaggerapi/swagger-editor
```

http://192.168.99.100:81/ にswagger editor 環境が作成される。

## Gradle Swagger Generator Plugin

`build.gradle`に追加

```groovy
	// swagger
	compile "io.springfox:springfox-swagger2:2.9.2"
	compile "io.springfox:springfox-swagger-ui:2.9.2"
	compile "io.springfox:springfox-core:2.9.2"
```

- http://localhost:8080/swagger-ui.html でSwagger UIを参照できる。
- http://localhost:8080/v2/api-docs でSwagger Editor用のJSONを取得できる。

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


