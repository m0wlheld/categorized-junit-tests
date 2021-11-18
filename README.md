# Split integration tests from unit tests using JUnit

This sample [Maven](https://maven.apache.org/) project demonstrates how to separate integration tests from unit tests by using a "marking" interface and an appropriate configuration for the [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/). 

## How does is work?
JUnit 4 supports using the [@Category](https://junit.org/junit4/javadoc/4.13/org/junit/experimental/categories/Category.html) annotation to mark a test class or a single [@Test](https://junit.org/junit4/javadoc/4.13/org/junit/Test.html) method being a member of a category (or category group). Using a dedicated configuration for the [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/), you will be able to include and/or exclude defined category groups when running tests.

### Define an interface as category marker

A simple interface is suitable to serve as a category marker. The interface's name should represent it's purpose. In this project, the class [org.dahlen.IntegrationTest](./src/test/java/org/dahlen/IntegrationTest.java) is used.

```java
public interface IntegrationTest {

}
```

### Annotate test classes or methods using marker

Use the interface class with the [@Category](https://junit.org/junit4/javadoc/4.13/org/junit/experimental/categories/Category.html) to mark an entire test class or dedicated test methods. For demonstration, see class [org.dahlen.AppTest](./src/test/java/org/dahlen/AppTest.java).

```java
public class AppTest {

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    @Category(IntegrationTest.class)
    public void shouldAnswerWithFalse() {
        assertTrue(false);
    }
}
```

### Configure Maven Surefire Plugin to exclude marked tests

By adding a configuration entry to the [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/), tests being part of category `org.dahlen.IntegrationTest` are excluded from tests by default. You also need to add a specific dependency for JUnit. See this project's [pom.xml](./pom.xml) for details.

```xml
<plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.1</version>
    <dependencies>
    <dependency>
        <groupId>org.apache.maven.surefire</groupId>
        <artifactId>surefire-junit47</artifactId>
        <version>2.22.2</version>
    </dependency>
    </dependencies>
    <configuration>
    <excludedGroups>org.dahlen.IntegrationTest</excludedGroups>
    </configuration>
</plugin>
```

### Create Maven profile to re-include marked tests

Without further measures, all tests marked as integration test will now be skipped. To enable them on demand, a Maven profile is used to "clear" the `<excludedGroups />` element in the plugin's configuration.

Unfortunately, you cannot just provide an empty `<excludedGroups />` element. Instead you need to overwrite the configuration's value with something that does not harm. You could also use a non-existing interface name, but this will print a warning to console during tests. Unless one of your tests is unlikely using the Serializable interface, go with the profile below:

```xml
<profile>
    <id>it</id>
    <build>
    <plugins>
        <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <configuration>
            <excludedGroups>java.io.Serializable</excludedGroups>
        </configuration>
        </plugin>
    </plugins>
    </build>
</profile>
```

## Sample

The files in this repository are for demonstration purpose only. To run the demonstration, you obviously need a suitable Java and Maven runtime environment. 

Clone this repository to a local directory and console into that directory, before running the next commands.

### Start maven build without any profile enabled  

```console
$ mvn clean test
```

The tests will be run selectively and they will not fail. The output will include something like:

```console
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running org.dahlen.AppTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.004 s - in org.dahlen.AppTest
[INFO] 
[INFO] Results:
[INFO]
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
```

### Start maven build with the ```it``` profile enabled

```console
$ mvn -Pit clean test
```

This time, the `shouldAnswerWithFalse` test will be run and (since it is designed to do so) will fail.

```console
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running org.dahlen.AppTest
[ERROR] Tests run: 2, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.005 s <<< FAILURE! - in org.dahlen.AppTest
[ERROR] shouldAnswerWithFalse(org.dahlen.AppTest)  Time elapsed: 0 s  <<< FAILURE!
java.lang.AssertionError
        at org.dahlen.AppTest.shouldAnswerWithFalse(AppTest.java:32)

[INFO] 
[INFO] Results:
[INFO]
[ERROR] Failures: 
[ERROR]   AppTest.shouldAnswerWithFalse:32
[INFO]
[ERROR] Tests run: 2, Failures: 1, Errors: 0, Skipped: 0
```

## See also:
* https://github.com/junit-team/junit4/wiki/Categories