# RSocket Client CLI
This repository demonstrates how to create / get data using rsc (RSocket Client CLI) very easy and real quick

```bash
mvn clean compile spring-boot:start

# brew tap making/tap
# brew reinstall making/tap/rsc
rsc tcp://127.0.0.1:7070 --route=/api/v1/customers/create-customer --data='{"name":"Maksimko"}'
rsc tcp://127.0.0.1:7070 --route=/api/v1/customers --stream
rsc tcp://127.0.0.1:7070 --route=/api/v1/customers/4

rsc tcp://127.0.0.1:7070 --route=/api/v1/customers/stream --stream &
rsc tcp://127.0.0.1:7070 --route=/api/v1/customers/fire-and-forget --data='{"name":"Max Fire"}' -fnf
rsc tcp://127.0.0.1:7070 --route=/api/v1/customers/fire-and-forget --data='{"name":"And Forget"}' -fnf

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
