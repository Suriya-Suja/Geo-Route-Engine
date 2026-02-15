# 🗺️ Geo-Route Engine

A full-stack, fully containerized spatial routing application that calculates optimal paths and manages geolocated data. Built with a Spring Boot backend, a React frontend, and powered by PostGIS and GraphHopper for high-performance spatial querying and routing.

## ✨ Key Features
* **Spatial Data Management:** Stores and queries exact geographical coordinates using PostGIS geometry points.
* **High-Performance Routing:** Utilizes GraphHopper to process millions of OpenStreetMap (OSM) nodes and calculate routes instantly.
* **Secure Authentication:** Stateless JWT (JSON Web Token) based authentication with Spring Security.
* **Fully Dockerized:** Effortless deployment using a multi-container Docker Compose architecture (Frontend, Backend, and Database).
* **Optimized Frontend:** Lightning-fast React frontend built with Vite and served in production via Nginx.

## 🚀 Tech Stack
* **Frontend:** React, Vite, Nginx
* **Backend:** Java 21, Spring Boot 3, Spring Security (JWT), Hibernate Spatial
* **Database:** PostgreSQL, PostGIS
* **Routing Engine:** GraphHopper Core
* **Infrastructure:** Docker, Docker Compose

---

## 🐳 Quick Start (Docker)

This project is fully containerized. You do not need to install Java, Node, or PostgreSQL on your local machine to run it.

### 1. Prerequisites
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running.

### 2. Clone the Repository
```bash
git clone https://github.com/Suriya-Suja/Geo-Route-Engine.git
cd Geo-Route-Engine
```

### 3.Environment Variables
Create a .env file in the root directory and configure your secure database credentials.Use the below template:

DB_USER=your_custom_username
DB_PASSWORD=your_custom_password
DB_NAME=georoute_db
JWT_SECRET=your_super_long_secure_jwt_secret_key

### 4. Add Map Data
1. Create a folder name "osm" inside the "/backend" directory.
2. Download an OpenStreetMap .pbf file (e.g., Southern India) from Geofabrik.
3. Place the downloaded file exactly here: backend/osm/southern-zone-india.osm.pbf

### 5. Generate the Routing Cache (First Boot Only)
GraphHopper requires significant memory (~4GB) to parse the raw map data into a routing graph. We need to temporarily boost the container's RAM.

1. Open docker-compose.yml and temporarily add this line to the backend environment variables:
JAVA_TOOL_OPTIONS: -Xmx4g -Xms4g 

2. Spin up the containers to build the cache:
```Bash
docker compose up --build
```

3. Wait for the Spring Boot application to fully start (this takes 2-5 minutes). Once it starts, the cache is safely generated and stored on your disk.

4. Press Ctrl + C in your terminal to stop the containers.

5. CRITICAL: Remove the JAVA_TOOL_OPTIONS line from docker-compose.yml to return your container to a lightweight state!

### 6. Start the Application
Now that the heavy lifting is done, you can run the application normally:

```Bash
docker compose up -d
```
Frontend UI: http://localhost:3000

Backend API: http://localhost:8080
