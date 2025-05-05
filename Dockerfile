# Imagen base con JDK 18
FROM openjdk:18-jdk-slim

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR generado por Maven
COPY target/eventospet-0.0.1-SNAPSHOT.jar app.jar

# Copia la carpeta del Oracle Wallet al contenedor
COPY Wallet_O8QS17ER78VQQ79L /app/wallet

# Copia el archivo de configuración para entorno Docker
COPY src/main/resources/application-docker.properties /app/application.properties

# Expone el puerto usado por la app
EXPOSE 8080

# Ejecuta la aplicación
ENTRYPOINT ["java", "-Dspring.config.location=file:/app/application.properties", "-jar", "app.jar"]
