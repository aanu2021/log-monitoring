# Error Log Monitoring

This project implements a log monitoring system that processes log entries from an input file and performs various operations based on the given commands. The project is implemented in Java and includes Docker support for easy deployment.

## Prerequisites

- Java Development Kit (JDK) installed (to run it locally)
- Docker installed (optional for Docker usage)

## Directory Structure

![image](https://github.com/aanu2021/log-monitoring/assets/91496248/5c111967-c2ea-481f-b040-df0234ad3f2c)


## Running the Program Locally

### Step 1: Compile the Java Program

1. Open a terminal or command prompt.
2. Clone the github repo locally.
   ```bash
   git clone https://github.com/aanu2021/log-monitoring.git
   ``` 
3. Navigate to the directory log-monitoring containing `Main.java`.

    ```bash
    cd path/to/log-monitoring
    ```

5. Compile the `Main.java` file.

    ```bash
    javac Main.java
    ```

    This command will generate a `Main.class` file in the same directory.

### Step 2: Run the Program

1. Ensure the `input.txt` file is present in the `testcases/test1` directory.
2. Run the compiled Java program.

    ```bash
    java Main
    ```

    This will execute the program, which will read from `testcases/test1/input.txt` and produce the output in the same directory.

### Step 3: Verify the Output

After running the program, check the output file generated in the `testcases/test1` directory.
 - Input file inside `testcases/test1` directory.

![image](https://github.com/aanu2021/log-monitoring/assets/91496248/9c47a906-972b-4aa0-a47f-9170fda51bdd)

 - Output file inside `testcases/test1` directory.

![image](https://github.com/aanu2021/log-monitoring/assets/91496248/6a1a3b94-320b-4823-8e89-0a668152ffdb)


## Multiple Test Cases

- There are total 10 different test cases, to use different test cases, change the inputFilePath and outputFilePath.

![image](https://github.com/aanu2021/log-monitoring/assets/91496248/603059e9-93ae-4387-b732-754e69afef48)

- To run the second test case, inputFilePath, outputFilePath would be :
  ```bash
  String inputFilePath = "testcases/test2/input.txt";
  String outputFilePath = "testcases/test2/output.txt";
  ```
- Similarly, new test cases can be added inside testcases directory, e.g `test11`, `test12`, `test13`, .....

## Containerization Process

### Step 1: Create the Dockerfile

![image](https://github.com/aanu2021/log-monitoring/assets/91496248/8ffc9de1-dfb4-4aa5-a270-005f5fd6dd88)

### Step 2: Build the Docker Image

1. Open a terminal or command prompt.
2. Navigate to the directory containing the `Dockerfile`.

    ```bash
    cd path/to/log-monitoring
    ```

3. Build the Docker image.

    ```bash
    docker build -t <name_of_the_docker_image> .
    ```

### Step 3: Run the Docker Container

1. Run the Docker container, mounting the `testcases` directory to ensure the program has access to the input files.

    ```bash
    docker run -v path/to/log-monitoring/testcases:/app/testcases <name_of_the_docker_image>
    ```

### Step 4: Pushing the Docker image inside the remote repository

1. Log in to the Docker CLI using the Github Personal Access Token (PAT).

    ```bash
    echo "<YOUR_GITHUB_PAT>" | docker login ghcr.io -u <YOUR_GITHUB_USERNAME> --password-stdin
    ```
2. Tag the Docker image for GitHub Container Registry (ghcr.io).

    ```bash
    docker tag <name_of_the_docker_image> ghcr.io/<YOUR_GITHUB_USERNAME>/<YOUR_REPOSITORY_NAME>:<DOCKER_TAG>
    ```    
3. Push the image to GitHub Package.

   ```bash
   docker push ghcr.io/<YOUR_GITHUB_USERNAME>/<YOUR_REPOSITORY_NAME>:<DOCKER_TAG>
   ``` 

### Local Docker Images
![image](https://github.com/aanu2021/log-monitoring/assets/91496248/a877f949-9837-4073-be2b-f0cdc5755897)

### Docker Images inside Github Container Registry
![image](https://github.com/aanu2021/log-monitoring/assets/91496248/b12c88ca-2054-4bbe-a87e-240cd7aa29a3)

## Running the Program Locally

### Step 1: Pulling the image

```bash
docker pull ghcr.io/<GITHUB_USERNAME>/<REPOSITORY_NAME>:<DOCKER_TAG>
```

### Step 2: Running Docker with volume mount

```bash
docker run -v %cd%/testcases:/app/testcases ghcr.io/<GITHUB_USERNAME>/<GITHUB_REPO_NAME>:<DOCKER_TAG>
```
