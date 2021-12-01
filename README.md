# RSocket Client CLI
This repository demonstrates how to create / get data using rsc (RSocket Client CLI) very easy and real quick

```bash
brew reinstall making/tap/rsc

mvn clean compile spring-boot:start
rsc --route=/api/v1/customers/create-customer --data='{"name":"Maksimko"}' tcp://127.0.0.1:7070
rsc --route=/api/v1/customers --stream tcp://127.0.0.1:7070
rsc --route=/api/v1/customers/4 tcp://127.0.0.1:7070
mvn spring-boot:stop
```

## RTFM
* https://github.com/making/rsc
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.1/maven-plugin/reference/html/#build-image)
* [Coroutines section of the Spring Framework Documentation](https://docs.spring.io/spring/docs/5.3.13/spring-framework-reference/languages.html#coroutines)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/2.6.1/reference/html/spring-boot-features.html#boot-features-r2dbc)
* [Acessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)
* [R2DBC Homepage](https://r2dbc.io)
