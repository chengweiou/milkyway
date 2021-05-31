./gradlew bootJar
cp build/libs/milkyway-0.0.1-SNAPSHOT.jar ~/Desktop/docker/universe/milkyway/ser.jar
#cp src/main/resources/application.yml ~/Desktop/docker/universe/milkyway/config/
cp src/main/resources/application-uat.yml ~/Desktop/docker/universe/milkyway/config/
cp src/main/resources/log4j2.xml ~/Desktop/docker/universe/milkyway/config/
cd ~/Desktop/docker/universe/milkyway
docker stop milkyway
docker run --rm -it -d --name milkyway -p 60001:8906 --network net -v /Users/chengweiou/Desktop/docker/universe/milkyway:/proj/ -w /proj/ openjdk java -jar ser.jar