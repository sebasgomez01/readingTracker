#FROM openjdk:21-jdk-slim
#ARG JAR_FILE=build/libs/backend-0.0.1-SNAPSHOT.jar 
#COPY ${JAR_FILE} backend.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "backend.jar"]

FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia todos los archivos del proyecto al contenedor
COPY . .

# Ejecuta el comando de construcción
RUN ./gradlew build -x test


# Define el argumento del archivo JAR
ARG JAR_FILE=build/libs/backend-0.0.1-SNAPSHOT.jar 

# Copia el archivo JAR generado al destino final
COPY ${JAR_FILE} backend.jar

# Expone el puerto 8080
EXPOSE 8080

# Define el punto de entrada
ENTRYPOINT ["java", "-jar", "backend.jar"]
