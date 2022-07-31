FROM openjdk:12
ADD target/text-replacement-1.0.jar text-replacement-1.0.jar
ENTRYPOINT ["java","-jar","text-replacement-1.0.jar"]