FROM openjdk:11

RUN apt update
RUN apt install -y maven

COPY /generator/pom.xml /usr/local/service/pom.xml
COPY /generator/org.acumos.gen.ros /usr/local/service/org.acumos.gen.ros
WORKDIR /usr/local/service
RUN mvn package

CMD [ "java", "-jar", "org.acumos.gen.ros/target/org.acumos.gen.ros-0.7.0-SNAPSHOT-jar-with-dependencies.jar ros1 " ]