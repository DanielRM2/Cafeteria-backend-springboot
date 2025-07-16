# Imagen base oficial de Java
FROM openjdk:17-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar archivos necesarios para construir el proyecto
COPY . .

# Dar permisos de ejecución al Maven Wrapper
RUN chmod +x ./mvnw

# Construir el proyecto y empaquetar el JAR
RUN ./mvnw clean package -DskipTests

# Exponer el puerto predeterminado de Spring Boot
EXPOSE 8080

# Ejecutar el archivo JAR generado
CMD ["java", "-jar", "target/springboot-cafeteria-1.0.jar"]