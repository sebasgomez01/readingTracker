FROM node:20 AS build
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
FROM nginx:alpine
# Copiar el archivo de configuración de nginx
COPY nginx.conf /etc/nginx/nginx.conf
# Copiar los archivos estáticos generados por el build
COPY --from=build ./dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]