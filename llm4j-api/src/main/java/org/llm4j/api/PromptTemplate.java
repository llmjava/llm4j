package org.llm4j.api;

import org.apache.commons.text.StringSubstitutor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PromptTemplate {
    String template = "";
    Map<String, Object> params = new HashMap<>();

    String prefix = "{";
    String suffix = "}";

    public PromptTemplate withFile(String fileName) {
        template = "";
        try {
            ClassLoader classloader = getClass().getClassLoader();
            Path path = Paths.get(classloader.getResource(fileName).toURI());
            for(String line: Files.readAllLines(path)) {
                template += "\n" + line;
            }
            template = template.substring("\n".length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public PromptTemplate withParam(String key, Object value) {
        this.params.put(key, value);
        return this;
    }


    public String render() {
        return StringSubstitutor.replace(template, params, prefix, suffix);
    }
}
