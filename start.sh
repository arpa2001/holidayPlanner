#!/bin/bash
set -e

# Start MySQL in background
service mysql start

# Wait a bit for MySQL to warm up
sleep 10

# Load your schema, data, and create app user
mysql -uroot -prootpass < /init.sql

# Run your Spring Boot app
java -jar /app.jar
