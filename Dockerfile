FROM eclipse-temurin:21-jdk

WORKDIR /app

# 👇 ESSA LINHA É ESSENCIAL
COPY . .

# Dá permissão pro gradlew (Linux precisa disso)
RUN chmod +x gradlew

# Build do projeto
RUN ./gradlew build

EXPOSE 8080

CMD ["java", "-jar", "build/libs/Projeto.Dare-1.0.0.jar"]