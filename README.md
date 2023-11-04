# ![RealWorld Example App](logo.png)

> ### **Java 17 + Spring Boot 3 + Native image** codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.

### [Demo](https://demo.realworld.io/)&nbsp;&nbsp;&nbsp;&nbsp;[RealWorld](https://github.com/gothinkster/realworld)

This codebase was created to demonstrate a fully fledged fullstack application built with ****Java 17 + Spring Boot 3 running as a native application**** including CRUD operations, authentication, routing, pagination, and more.

This application is based on [Java 17 + Spring Boot 3 Real world application](https://github.com/shirohoo/realworld-java17-springboot3) with changes made only to make it working as a native application

> **Note:** You just need to have JDK 17 installed.
>
> **Note:** If permission denied occurs when running the gradle task, enter `chmod +x gradlew` to grant the permission.

## Install GraalVM distribution with sdkman

```shell
sdk install java 22.3.2.r17-nik
sdk use java 22.3.2.r17-nik
```

## Build application

```shell
./gradlew build
```

## Run application with native-image-agent attached to collect reachability metadata

```shell
java -agentlib:native-image-agent=config-output-dir=./aot/META-INF/native-image/,experimental-class-define-support -Dspring.aot.enabled=true -jar ./build/libs/realworld.jar
```

## In second terminal window run api integration tests

```shell
./api/run-api-tests.sh
```

## Close previously run application with native-image-agent
## Build native image

```shell
./gradlew nativeCompile
```

## Run the application

```shell
./build/native/nativeCompile/realworld 
```
