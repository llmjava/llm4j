package org.llm4j.palm;

import org.llm4j.api.LanguageModelFactory;
import org.llm4j.spi.LLM4JServiceProvider;

public class PaLMServiceProvider implements LLM4JServiceProvider {

    public LanguageModelFactory getLLMFactory() {
        return new PaLMLanguageModel.Builder();
    }
}
