FROM debian:bookworm-slim

# Copying setup files
COPY setup/debs/* /lib/downloads/debs/
COPY setup/packages/* /lib/downloads/

# Install essential system utilities
RUN dpkg -i /lib/downloads/debs/*.deb || true

# Installing java
RUN tar -xf /lib/downloads/jdk-21_linux-x64_bin.tar.gz -C /lib/downloads/
RUN mv /lib/downloads/jdk-21.0.7 /lib/jdk-21

# Installing and setting up MySQL
RUN groupadd mysql
RUN useradd -r -g mysql -s /bin/false mysql
RUN tar -xf /lib/downloads/mysql-8.0.42-linux-glibc2.28-x86_64.tar.xz -C /lib/downloads/
RUN mv /lib/downloads/mysql-8.0.42-linux-glibc2.28-x86_64 /lib/mysql
RUN ln -s /lib/mysql mysql
RUN cd /lib/mysql
RUN mkdir mysql-files
RUN chown mysql:mysql mysql-files
RUN chmod 750 mysql-files
RUN /lib/mysql/bin/mysqld --initialize-insecure --user=root
# RUN /lib/mysql/bin/mysql_ssl_rsa_setup --> Obsolete
RUN cp /lib/mysql/support-files/mysql.server /etc/init.d/mysql.server

# Adding path for Java and MySQL
ENV JAVA_HOME=/lib/jdk-21
ENV MYSQL_HOME=/lib/mysql
ENV PATH="$JAVA_HOME/bin:$MYSQL_HOME/bin:$PATH"

# Removing installation packages
RUN rm -rf /lib/downloads

# Set working directory
WORKDIR /opt

# Initialise data
COPY setup/DB/* /opt/DB/

# Copy resources
ARG JAR_FILE=/target/*.jar
COPY ${JAR_FILE} /app.jar

EXPOSE 8080

COPY setup/start.sh /opt/start.sh
ENTRYPOINT ["/opt/start.sh"]
