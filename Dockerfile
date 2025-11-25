# Stage 1: Build frontend
FROM node:20-alpine AS frontend-build
WORKDIR /app/pair-finder-frontend
COPY pair-finder-frontend/package*.json ./
RUN npm install
COPY pair-finder-frontend/ ./
RUN npm run build

# Stage 2: Build backend
FROM maven:3.9.4-eclipse-temurin-20 AS backend-build
WORKDIR /app

# Copy backend files
COPY pom.xml .
COPY src ./src

# Copy frontend build into backend resources
COPY --from=frontend-build /app/pair-finder-frontend/dist ./src/main/resources/static

# Build Spring Boot jar
RUN mvn clean package -DskipTests

# Stage 3: Runtime
FROM eclipse-temurin:20-jdk-alpine
WORKDIR /app

# Copy jar
COPY --from=backend-build /app/target/PairOfEmployeeCounter-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
