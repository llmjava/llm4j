# llm4j

[![build](https://github.com/llmjava/llm4j/actions/workflows/main.yml/badge.svg)](https://github.com/llmjava/llm4j/actions/workflows/main.yml) [![Jitpack](https://jitpack.io/v/llmjava/llm4j.svg)](https://jitpack.io/#llmjava/llm4j) [![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://llmjava.github.io/llm4j/javadoc/)  [![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

</b>

**llm4j** is Java library that you can use to interact with mulitple LLM backends: Google PaLM, Cohere, OpenAI, Hugging Face. It can be used in Android or any Java and Kotlin Project.

## Add Dependency

### Gradle

To use library in your gradle project follow the steps below:

1. Add this in your root `build.gradle` at the end of repositories:
    ```groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
    ```
2. Add the dependency
   ```groovy
   dependencies {
       def LLM4J_VERSION = "..."
       implementation "com.github.llmjava:llm4j:$LLM4J_VERSION"
   }
   ```

### Maven

To use the library in your Maven project, follow the steps below:

1. Add the JitPack repository to your build file:
    ```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    ```
2. Add the dependency
    ```xml
    <dependency>
        <groupId>com.github.llmjava</groupId>
        <artifactId>llm4j</artifactId>
        <version>${LLM4J_VERSION}</version>
    </dependency>
    ```

3. Add the dependency of the LLM backend API, for example Google PaLM
    ```xml
    <dependency>
        <groupId>com.github.llmjava</groupId>
        <artifactId>llm4j-palm</artifactId>
        <version>${LLM4J_VERSION}</version>
    </dependency>
    ```


## Usage

Example code to use against **Google PALM**:

Create a configuration file
```properties
# Set API key using env variable or put actual value
cohere.apiKey=${env:PALM_API_KEY}
```

Create an instance of `LanguageModel` and submit text generation requests

```java
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.llm4j.api.LLM4J;
import org.llm4j.api.LanguageModel;

public class Main {
   public static void main(String[] args) {
        Configuration config = new Configurations().properties("llm4j.properties");
        LanguageModel llm = LLM4J.getLanguageModel(config);

        String answer = llm.process("In what country is El Oued?");
        System.out.println(answer);
   }
}
```

The library uses a Java [Service Loader](https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html) under the hood to load the appropriate `LanguageModel` implementation. But we also manually create an instance of `LanguageModel` using a specific implementation, like for PaLM:

```java
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.llm4j.api.LLM4J;
import org.llm4j.palm.PaLMLanguageModel;

public class PaLMApp {

    public static void main(String[] args) throws ConfigurationException {
        Configuration config = new Configurations().properties("palm.properties");

        PaLMLanguageModel.Builder factory = new PaLMLanguageModel.Builder();
        PaLMLanguageModel llm = (PaLMLanguageModel) LLM4J.getLanguageModel(config, factory);

        String answer = llm.process("In what country is El Oued?");
        System.out.println(answer);
    }
}
```

## Build Project

Clone the repository and import as Maven project in IntelliJ IDEA or Eclipse

Before building the project, make sure you have the following things installed.

- Maven
- Java 8

To install the library to your local Maven repository, simply execute:

```shell
mvn install
```

To build the library using Gradle, execute the following command

```shell
./gradlew build
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.
