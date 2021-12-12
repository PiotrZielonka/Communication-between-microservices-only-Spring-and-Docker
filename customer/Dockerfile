FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /target/customer-0.0.1-SNAPSHOT-exec.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/target/customer-0.0.1-SNAPSHOT-exec.jar"]