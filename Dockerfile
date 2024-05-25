# Use an OpenJDK base image
FROM openjdk:latest

# Set the working directory inside the container
WORKDIR /app

# Copy the Java source code into the container
COPY Main.java /app

# Copy the testcases folder into the container
COPY testcases /app/testcases

# Compile the Java code
RUN javac Main.java

# Run the Java application
CMD ["java", "Main"]
