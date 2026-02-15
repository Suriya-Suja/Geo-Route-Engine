# ==========================================
# Stage 1: Build the React Application
# ==========================================
FROM node:22.12-alpine AS build
WORKDIR /app

# Copy package.json and install dependencies first (for caching)
COPY package*.json ./
RUN npm install

# 1. FIXED: Copy EVERYTHING (including index.html, vite.config.js, etc.)
COPY . .

# Build the app for production (Vite will compile this into a /dist folder)
RUN npm run build

# ==========================================
# Stage 2: Serve with Nginx
# ==========================================
FROM nginx:alpine

# Remove default Nginx static assets
RUN rm -rf /usr/share/nginx/html/*

# 2. FIXED: Tell Docker to grab the /dist folder instead of /build
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80 (Nginx default)
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]