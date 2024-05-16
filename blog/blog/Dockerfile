# Utiliser une image officielle de Maven pour construire le projet
FROM maven:3.8.4-openjdk-17 AS build

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .

# Télécharger les dépendances sans construire le projet
RUN mvn dependency:go-offline -B

# Copier le reste des fichiers de l'application
COPY src ./src

# Construire l'application
RUN mvn package -DskipTests

# Utiliser une image officielle de OpenJDK pour exécuter l'application
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le jar de l'application depuis l'étape de construction
COPY --from=build /app/target/blog-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port 8090
EXPOSE 8090

# Définir le point d'entrée pour exécuter le jar
ENTRYPOINT ["java", "-jar", "app.jar"]
