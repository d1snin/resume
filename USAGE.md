# Usage

This document describes how to configure the application and how to run it.

### Configuring

You need to configure the `application.yml`
located in `src/main/resources/application.yml` with your own data. Every property **is not** necessary, you can freely
remove it from the configuration, for example, if you don't want to reveal your country or the name.

Here are the definitions of each property:

- `resume.preferHttps`: whether to use `https` instead of `http` in URLs building. Default value is `true`.
- `resume.banner`: ASCII art, whatever. Showed in the header of the resume.
- `resume.name`: your name.
- `resume.nickname`: your nickname, username, pseudonym.
- `resume.shortBio`: short bio. It will appear in the header of the resume alongside with the art/banner.
- `resume.longBio`: long bio.
- `resume.location`: your location.
- `resume.age`: your age.
- `resume.languages`: languages you do use, with `name` and `knowledge` properties.
- `resume.frameworks`: frameworks you do use, with `name` and `knowledge` properties.
- `resume.contacts`: your contacts, with `service` and `address` properties
- `resume.projects`: projects you do develop, with `name`. `description`, `status` and `url` properties.

### Running

There are few methods available to run the application on your server. The default port is `8080`, but you can change it
using the `SERVER_PORT` environment variable or `server.port` property in the `application.yml` file.

#### Using `docker-compose`

Build the OCI image using `bootBuildImage` gradle task:

```shell
./gradlew bootBuildImage
```

Run the application using your `docker-compose` installation:

```shell
docker-compose up
```

#### Using `bootRun` gradle task

Run the application using `bootRun`:

```shell
./gradlew bootRun
```

#### Using JAR

Build the JAR using `bootJar` gradle task:

```shell
./gradlew bootJar
```

Now run it using your JRE/JDK installation:

```shell
java -jar ./build/libs/resume-{version}.jar
```