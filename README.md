# Survey Logging Service

This project provides a RESTful service for logging survey results to [Oracle Cloud Infrastructure (OCI) Logging](https://docs.oracle.com/en-us/iaas/Content/Logging/Concepts/loggingoverview.htm). It is built with Dropwizard and packaged as a Docker container for easy deployment.

---

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [OCI Configuration](#oci-configuration)
- [Configuration](#configuration)
- [Build & Run](#build--run)
    - [Local Build & Run](#local-build--run)
    - [Docker Build & Run](#docker-build--run)
- [Kubernetes/OCI Deployment](#kubernetesoci-deployment)
- [Testing](#testing)
- [Customization](#customization)
- [Troubleshooting](#troubleshooting)
- [License](#license)

---

## Features

- REST endpoint at `/surveys` for posting survey results
- Logs survey data to OCI Logging using the OCI Java SDK
- Dockerized for portability and easy deployment
- Includes automated unit test for endpoint verification

---

## Prerequisites

- [Java 11+](https://adoptopenjdk.net/)
- [Maven 3.6+](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- An [OCI account](https://cloud.oracle.com/) with permissions to write logs

---

## OCI Configuration

1. **Generate your `.oci` config file and API key**  
   Follow [OCI CLI configuration instructions](https://docs.oracle.com/en-us/iaas/Content/API/SDKDocs/javasdkconfig.htm) to create:
    - `~/.oci/config`
    - `~/.oci/oci_api_key.pem`

2. **Mount `.oci` as a Docker volume (if running in Docker):**
   ```sh
   docker run -v $HOME/.oci:/root/.oci ...
   ```
   > On Windows:  
   > `docker run -v C:\Users\youruser\.oci:/root/.oci ...`

3. **Ensure your config file uses a relative path for the key:**
   ```
   key_file=~/.oci/oci_api_key.pem
   ```

---

## Configuration

- **Edit `SurveyLoggingService.java`**  
  Set your OCI Log endpoint and Log OCID in the appropriate fields:
  ```java
  private static final String LOG_ID = "";
  private static final String ENDPOINT = "";
  ```


---

## Build & Run

### Local Build & Run

1. **Build with Maven:**
   ```sh
   mvn clean package
   ```
2. **Run locally:**
   ```sh
   java -jar target/surveyapp.jar server
   ```

### Docker Build & Run

1. **Build Docker image:**
   ```sh
   docker build -t surveyapp:latest .
   ```
2. **Run Docker container:**
   ```sh
   docker run -v $HOME/.oci:/root/.oci -p 8080:8080 surveyapp:latest
   ```
   > Adjust the volume path for your OS as needed.

---

## Kubernetes/OCI Deployment

- **A sample Kubernetes deployment YAML (`surveyapp-development.yaml`) is provided.**
- Update the image reference and volume mount for `.oci` as needed.
- Deploy with:
  ```sh
  kubectl apply -f surveyapp-development.yaml
  ```

---

## Testing

- **Unit Test:**  
  The `SurveyEndpointTest` class will send randomized POST requests to `/surveys` every 10 seconds.
- **Manual Test:**  
  Once running, access the endpoint:
  ```
  POST http://localhost:8080/surveys
  ```
  with a JSON body matching your survey schema.

---

## Customization

- **Log Endpoint and OCID:**  
  Edit these in `SurveyLoggingService.java` as described above.
- **OCI Region:**  
  Ensure your `.oci/config` file specifies the correct region.

---

## Troubleshooting

- **Port Already in Use:**  
  If you get a Docker port binding error, ensure port 8080 is free or change the mapping (`-p 8090:8080`).
- **OCI Config Not Found:**  
  Ensure `.oci` is correctly mounted and the config file paths are correct.
- **Jackson/SDK Errors:**  
  Use the provided Maven dependencies and avoid mixing incompatible versions.

---

## License

This project is licensed under the MIT License.

---

**For further help, see the [Oracle OCI Java SDK documentation](https://docs.oracle.com/en-us/iaas/Content/API/SDKDocs/javasdk.htm) and [OCI Logging documentation](https://docs.oracle.com/en-us/iaas/Content/Logging/Concepts/loggingoverview.htm).**

---
