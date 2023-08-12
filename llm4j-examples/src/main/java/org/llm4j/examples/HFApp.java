package org.llm4j.examples;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.llm4j.api.LLM4J;
import org.llm4j.huggingface.HFLanguageModel;

public class HFApp {

    public static void main(String[] args) throws ConfigurationException {
        Configuration config = new Configurations().properties("llm4j.properties");

        HFLanguageModel.Builder factory = new HFLanguageModel.Builder();
        HFLanguageModel llm = (HFLanguageModel) LLM4J.getLanguageModel(config, factory);

        String answer = llm.process("In what country is Andalossia?");
        System.out.println(answer);
    }
}