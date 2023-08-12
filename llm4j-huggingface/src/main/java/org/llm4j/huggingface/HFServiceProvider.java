package org.llm4j.huggingface;

import org.llm4j.api.LanguageModelFactory;
import org.llm4j.spi.LLM4JServiceProvider;

public class HFServiceProvider implements LLM4JServiceProvider {

    public LanguageModelFactory getLLMFactory() {
        return new HFLanguageModel.Builder();
    }
}
