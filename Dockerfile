# Usar una imagen base de OpenJDK
FROM openjdk:17
# Establecer un directorio de trabajo

COPY "./target/shop-0.0.1-SNAPSHOT.jar" "app.jar"

# Exponer el puerto en el que correrá la aplicación
EXPOSE 8099

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
