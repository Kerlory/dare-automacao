FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# atualizar sistema
RUN apt-get update

# instalar dependências básicas
RUN apt-get install -y wget curl unzip gnupg

# adicionar repositório do chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google.gpg

RUN echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list

# instalar chrome
RUN apt-get update && apt-get install -y google-chrome-stable

# copiar projeto
COPY . .

# permitir execução do gradle
RUN chmod +x gradlew

# build da aplicação
RUN ./gradlew build --no-daemon

EXPOSE 8080

CMD ["java","-jar","build/libs/Projeto.Dare-1.0.0.jar"]