## swagger

### swagger editor on Docker

```sh
docker pull swaggerapi/swagger-editor
docker run -d -p 81:8080 --name swe swaggerapi/swagger-editor
```

http://192.168.99.100:81/ にswagger editor 環境が作成される。

### Gradle Swagger Generator Plugin

`build.gradle`に追加

```groovy
	// swagger
	compile "io.springfox:springfox-swagger2:2.9.2"
	compile "io.springfox:springfox-swagger-ui:2.9.2"
	compile "io.springfox:springfox-core:2.9.2"
```

- http://localhost:8080/swagger-ui.html でSwagger UIを参照できる。
- http://localhost:8080/v2/api-docs でSwagger Editor用のJSONを取得できる。

## formatter

### google-java-format

`build.gradle`に1行追加 https://plugins.gradle.org/plugin/com.github.sherter.google-java-format を参照

```groovy
plugins {
	id 'com.github.sherter.google-java-format' version "0.8"
}
```

- `Gradle > Tasks > other > googleJavaFormat` で全ファイルをフォーマットする。
- `Gradle > Tasks > other > verifyGoogleJavaFormat` でフォーマット漏れがないか確認する。

## 静的解析ツール

### SonarQube

#### to IDE

以下を参照してSonarLintというプラグインを取得する。

https://qiita.com/ot-aoyagi/items/de8861e53688c3db4084

> SonarQubeの開発を行っているSonarSource社は、各種IDE向けのSonarLintというツールも開発しています。これらはプラグイン形式で提供されており、SonarQubeのソース静的解析(Analyze)機能と同等のことをIDE上から利用できるようになっています。

ダウンロードし再起動すると、自動的に解析が始まるので、あとは怒られるだけ。

#### SonarQubeサーバーへの接続（未完了）

以下を参考にする（未完了）

- https://qiita.com/hiroshis/items/7733f9c01d2c328a42d6
- http://siosio.hatenablog.com/entry/2017/07/22/065652

### error prone

#### to IDE

- IntelliJ上で`Error-prone Compiler Integration`プラグインをダウンロードし再起動する。
- 以下に従い設定をJava Compilerを変更するが、**「シンボルを見つけられません」エラーが発生することがあるので、あまり使わないほうがいいかも**。

https://plugins.jetbrains.com/plugin/7349-error-prone-compiler-integration

#### Gradle

```groovy
plugins {
    id 'net.ltgt.errorprone' version '0.8'
}

dependencies {
    // error-prone
	errorprone 'com.google.errorprone:error_prone_core:2.3.3'
	compileOnly "com.google.errorprone:error_prone_annotations:2.3.3"
}
```

エラーがあるとビルドを中断してくれる。

```
> Task :compileJava
C:\ws\practice\spring\sdk\src\main\java\com\haruspring\sample\dynamodb\sdk\SdkApplication.java:11: エラー: [DeadException] Exception created but not thrown
    new Exception();
    ^
    (see https://errorprone.info/bugpattern/DeadException)
  Did you mean 'throw new Exception();'?
エラー1個
```

