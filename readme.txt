1. local test:
spring-boot:run




2.package
mvn package -Dmaven.test.skip




3. run
java -Xms256m -Xmx512m -jar -Dspring.profiles.active=prod dcode-1.jar

