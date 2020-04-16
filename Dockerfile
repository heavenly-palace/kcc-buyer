FROM java:8
COPY ./target/kcc-buyer-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]