FROM debian:bookworm-slim

# Install essentials only
RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y \
    openjdk-21-jre mysql-server bash && \
    rm -rf /var/lib/apt/lists/*

# MySQL setup
RUN mkdir -p /var/run/mysqld && chown -R mysql:mysql /var/run/mysqld

# Copy resources
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /app.jar
COPY init.sql /init.sql
COPY start.sh /start.sh
RUN chmod +x /start.sh

EXPOSE 8080 3306
CMD ["/start.sh"]
