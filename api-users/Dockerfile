# Utilise une image Java légère
FROM eclipse-temurin:17-jdk-jammy

# Définit le dossier de travail dans le conteneur
WORKDIR /app

# Copie ton JAR dans le conteneur (Docker ne voit que ce qui est dans ce dossier)
COPY target/server.jar app.jar
# Ouvre le port 7001
EXPOSE 7001

# Lance l'application
ENTRYPOINT ["java", "-jar", "app.jar"]