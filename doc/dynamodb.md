# DynamoDB周りの設定

## aws config credential

以下のように設定済み。

`C:\Users\{user}\.aws\config`

```
[default]
region = ap-northeast-1
output = json
```

`C:\Users\{user}\.aws\credentials`

```
[default]
aws_access_key_id = key
aws_secret_access_key = key2
```

## dynamodb-local

### Docker image

Docker Toolboxを使用した場合は`http://192.168.99.100:8000`に構築される。

```bash
docker pull amazon/dynamodb-local
docker run -d --name dynamodb -p 8000:8000 amazon/dynamodb-local
```

### Docker admin

```bash
npm install dynamodb-admin -g
export DYNAMO_ENDPOINT=http://192.168.99.100:8000
dynamodb-admin
```

## テストデータの投入

テストデータは https://docs.aws.amazon.com/ja_jp/amazondynamodb/latest/developerguide/SampleData.LoadData.html からダウンロードできる。

```sh
cd doc/sample_data
aws dynamodb batch-write-item --request-items file://ProductCatalog.json --endpoint-url http://192.168.99.100:8000
```

## build.gradle

dependenciesに以下を追加する。

```groovy
	// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk
	compile group: 'com.amazonaws', name: 'aws-java-sdk', version: '1.11.546'
	compile group: 'com.amazonaws', name: 'aws-java-sdk-dynamodb', version: '1.11.546'
```
