package org.llm4j.api;


import org.apache.commons.configuration2.Configuration;

public interface LanguageModelFactory {
    LanguageModel getLanguageModel(Configuration config);
}
