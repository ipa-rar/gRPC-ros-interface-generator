FROM openjdk:11

RUN apt update
RUN apt install -y maven

COPY /generator/pom.xml /usr/local/service/pom.xml
COPY /generator/org.acumos.gen.ros /usr/local/service/org.acumos.gen.ros
COPY /protoc/demo.ds /usr/local/service/demo.ds
COPY docker/entrypoint.sh /usr/local/service/entrypoint.sh

WORKDIR /usr/local/service
RUN mvn clean compile
RUN mvn -f pom.xml clean package

CMD ["java","-jar","org.acumos.gen.ros/target/org.acumos.gen.ros-0.7.0-SNAPSHOT-jar-with-dependencies.jar", "ros1", "./demo.ds"]