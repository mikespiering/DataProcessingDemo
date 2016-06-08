# DataProcessingDemo
mvn package
cf create-service p-rabbitmq standard testrabbit
cf create-service p-mysql 100mb testmysql
cf create-service p-mysql 100mb fakemongo
cf push -f manifest-NoMongo.yml