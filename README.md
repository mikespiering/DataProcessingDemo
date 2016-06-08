# DataProcessingDemo
- mvn package
- cf create-service p-rabbitmq standard testrabbit
- cf create-service p-mysql 100mb testmysql
- cf create-service p-mysql 100mb fakemongo
- cf push -f manifest-NoMongo.yml

#OR Just
-Add credentials to ~/.m2/settings.xml  (see:https://docs.cloudfoundry.org/buildpacks/java/build-tool-int.html   )
-mvn clean package cf:push