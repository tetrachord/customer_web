# About

This REST application exposes 2 REST endpoints

1. POST endpoint, which can be used to create a customer resource, if the client adds a request body to the request with a customer in JSON format.

http://localhost:8080/customer

2. GET endpoint, which can be used to retrieve a customer in JSON format given a customer reference as a path variable 

http://localhost:8080/customer/{customerRef}

Note that this application is not production quality, since it uses an in-memory SQL DB `h2`.

# How to run the application

execute on the commandline

```
./gradlew bootRun
```

which will start the SpringBoot application on port 8080.

# How to test the application

execute on the commandline

```
./gradlew test
```