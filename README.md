### Build Docker Image

- make sure you navigate to project root path
- make sure JAVA_HOME points to java version 17
- run command:
  - mvn spring-boot:build-image
- or you can use maven wrapper instead:
  - mvnw spring-boot:build-image
- image 'authcheck:latest' will be generated
- image name is configured in 'spring-boot-maven-plugin' as 'authcheck'

### Run Docker Compose

- run command:
    - docker compose up

### Example Postman Collection

- You can import Postman Collection from project root path:
    - *AUTH-CHECK.postman_collection.json*
 