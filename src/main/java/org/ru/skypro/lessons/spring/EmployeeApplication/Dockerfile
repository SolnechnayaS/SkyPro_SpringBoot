# Используем базовый образ с установленным JDK
FROM openjdk:latest

# Копируем файлы приложения в образ
COPY ./target/EmployeeApplication-0.0.1-SNAPSHOT.jar /app/app.jar

# Устанавливаем рабочую директорию
WORKDIR  /app

# Запускаем приложение
RUN java -jar /app/app.jar