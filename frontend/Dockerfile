FROM node:lts-alpine3.16 as build

WORKDIR /usr/app

# Copy package.json and package-lock.json before other files
COPY package.json ./
COPY package-lock.json ./

# Install dependencies
RUN npm install

# Add the source code to app
COPY ./ /usr/app

RUN npm run build

# Base image
FROM nginx:stable

# Support running as arbitrary user which belongs to the root group

# Copy build artifacts
COPY --from=build /usr/app/dist/websocket-app /usr/share/nginx/html

# Expose port 8081
EXPOSE 80

# Run nginx
CMD ["nginx", "-g", "daemon off;"]
