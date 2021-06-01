./gradlew bootJar
cp build/libs/milkyway-0.0.1-SNAPSHOT.jar ~/Desktop/docker/universe/milkyway/ser.jar
#cp src/main/resources/application.yml ~/Desktop/docker/universe/milkyway/config/
cp src/main/resources/application-uat.yml ~/Desktop/docker/universe/milkyway/config/
cp src/main/resources/log4j2.xml ~/Desktop/docker/universe/milkyway/config/

docker-compose down
docker-compose up -d

