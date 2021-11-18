# Split integration tests from unit tests using JUnit

This sample [Maven](https://maven.apache.org/) project demonstrates how to separate integration tests from unit tests by using a "marking" interface and an appropriate configuration for the [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/). 

By default, tests marked as integration tests are excluded when running ```mvn test```, but activating a dedicated profile (here: "it") will include them during test runs: ```mvn -Pit test```
