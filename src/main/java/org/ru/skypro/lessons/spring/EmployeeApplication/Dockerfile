FROM openjdk:latest
COPY ./target/EmployeeApplication-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
CMD ["java", "-jar", "app.jar", "--spring.datasource.url=jdbc:postgresql://localhost:6543/employeeaplication"]