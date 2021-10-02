./gradlew bootJar
cp build/libs/milkyway-0.0.1-SNAPSHOT.jar ~/Desktop/docker/universe/milkyway/ser.jar
#cp src/main/resources/application.yml ~/Desktop/docker/universe/milkyway/config/
cp src/main/resources/application-uat.yml ~/Desktop/docker/universe/milkyway/config/
cp src/main/resources/log4j2.yml ~/Desktop/docker/universe/milkyway/config/
cp docker-compose.yml ~/Desktop/docker/universe/milkyway/docker-compose.yml
cd ~/Desktop/docker/universe/milkyway

docker compose down
docker compose rm -f
docker compose up -d
