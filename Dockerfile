FROM eclipse-temurin:21-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    chromium-browser \
    chromium-driver

COPY . .

RUN chmod +x gradlew

RUN ./gradlew build --no-daemon

EXPOSE 8080

CMD ["java", "-jar", "build/libs/Projeto.Dare-1.0.0.jar"]