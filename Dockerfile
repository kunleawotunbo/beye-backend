FROM maven:3.6.3-jdk-11-slim AS build

# Create a directory named workspace
RUN mkdir -p /workspace

# Set WORKDIR
WORKDIR /workspace

# Copy local code to the container image /workspace.
COPY pom.xml /workspace
COPY src /workspace/src

# Build a release artifact.
RUN mvn -B -f pom.xml clean package -DskipTests

FROM openjdk:11-jdk-slim
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]