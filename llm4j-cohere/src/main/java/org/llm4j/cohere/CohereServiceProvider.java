package org.llm4j.cohere;

import org.llm4j.api.LanguageModelFactory;
import org.llm4j.spi.LLM4JServiceProvider;

public class CohereServiceProvider implements LLM4JServiceProvider {

    public LanguageModelFactory getLLMFactory() {
        return new CohereLanguageModel.Builder();
    }
}
