# Build application
FROM adoptopenjdk/maven-openjdk12 AS build
COPY src /textreplacement/src
COPY pom.xml /textreplacement
RUN mvn -f /textreplacement/pom.xml clean package

# Run application
FROM openjdk:12
COPY --from=build /textreplacement/target/text-replacement-1.0.jar /usr/local/lib/textreplacement.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/textreplacement.jar"]

# To run this application, use "docker run --rm -v "PATH_TO_YOUR_FILE":"PATHNAME_YOU_WANT" -it realcryzed/textreplacement-build-and-run:latest"
# For example: PATH_TO_YOUR_FILE can be "C:\Users\user\Desktop\text-folder", PATHNAME_YOU_WANT can be "/docker-path"