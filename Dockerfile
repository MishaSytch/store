# Используем официальный образ Gradle для сборки проекта
FROM gradle:7.2-jdk8 AS build
WORKDIR /app

# Копируем файлы сборки и исходный код
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Собираем проект
RUN gradle build --no-daemon

# Используем официальный образ OpenJDK для запуска приложения
FROM openjdk:8-jre-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]