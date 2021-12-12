FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /target/eureka-server-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar","/target/eureka-server-0.0.1-SNAPSHOT.jar"]