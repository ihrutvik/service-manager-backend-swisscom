#!/bin/bash

set -e  # Exit on any error

PROJECT_NAME="Service Manager Backend Swisscom"
SPRING_PROFILE="dev"
LOG_DIR="logs"
LOG_FILE="$LOG_DIR/app.log"

echo "🚀 Bootstrapping $PROJECT_NAME..."

# Step 1: Check for Maven wrapper
if [ ! -f "./mvnw" ]; then
  echo "⚠️  Maven wrapper (./mvnw) not found!"

  if ! command -v mvn &> /dev/null; then
    echo "❌ Neither Maven wrapper nor global Maven installation found!"
    echo "💡 Please install Maven manually from https://maven.apache.org/download.cgi"
    exit 1
  fi

  echo "Installing Maven wrapper using global Maven..."
  mvn -N io.takari:maven:wrapper
fi

# Step 2: Create logs directory
mkdir -p "$LOG_DIR"
echo "Logs will be written to $LOG_FILE"

# Step 3: Build the project
echo "Building project (skipping tests)..."
./mvnw clean install -DskipTests

# Step 4: Run the project
echo "Starting Spring Boot app with profile '$SPRING_PROFILE'..."
./mvnw spring-boot:run -Dspring-boot.run.profiles=$SPRING_PROFILE | tee "$LOG_FILE"
