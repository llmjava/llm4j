package org.llm4j.api;

import org.stringtemplate.v4.ST;

public class PromptTemplate {

    private final ST template;

    /**
     *
     * @param template The prompt template. e.g. "Hello, <name>!"
     * @param input_variables A list of the names of the variables the prompt template expects.
     */
    public PromptTemplate(String template, String[] input_variables) {
        this.template = new ST(template);
        hello.add("name", "World");
        String output = hello.render();
    }



    public String format() {
        return template.render();
    }
}
