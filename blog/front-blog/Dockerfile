# Utiliser une image officielle de Node.js en tant qu'étape de build
FROM node:18 AS build

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier package.json et package-lock.json (si disponible)
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Copier le reste des fichiers de l'application
COPY . .

# Construire l'application
RUN npm run build

# Utiliser une image de serveur web léger pour servir les fichiers statiques
FROM nginx:alpine

# Copier les fichiers build dans le répertoire où Nginx sert les fichiers
COPY --from=build /app/dist /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80

# Démarrer Nginx
CMD ["nginx", "-g", "daemon off;"]

