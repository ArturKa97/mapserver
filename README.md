# MapServer Info API

A Spring Boot REST API that fetches metadata from an ArcGIS MapServer service URL.

## Requirements

- (** required if needed to run with command "mvn spring-boot:run")
- **Java 17+** (JDK must be installed and `JAVA_HOME` environment variable configured)  
- **Maven 3.6+** (must be installed and added to your system `PATH`)  
- Internet connection to access ArcGIS services (should be free and always available)

## How to run

1. Download the project:
  1.1 Download through my GitHub repo and extract it into your own projects folder or repository.
   or
  1.2 Clone it with terminal:
     git clone <your-repo-url>
     cd mapserver

3. Build and run the project:
  2.1 Run it directly in your IDE
   or
  2.2 Run with command in the terminal:
    mvn spring-boot:run

5. The API will start on the http://localhost:8080 and the tests will run on port 8089
   (make sure these ports are open on your system, if not change the port in .properties file to start the API on different port, if 8089 is taken just change the port in the tests to a different one).

## How to use

1. Use any HTTP client of your choice (e.g., Postman, curl, or a web browser) to send a GET request to the endpoint:
   http://localhost:8080/mapserver-info?url=https://www.geoportal.lt/mapproxy/gisc_pagrindinis/MapServer
   or
   http://localhost:8080/mapserver-info?url=https://www.geoportal.lt/mapproxy/nzt_ort10lt_2024_2026/MapServer
   The response will contain JSON-formatted information including the service’s mapName, description, and a list of layers with their respective IDs and names.

## Stopping the server

1. Press Ctrl + C in the same terminal and the server will be terminated.
