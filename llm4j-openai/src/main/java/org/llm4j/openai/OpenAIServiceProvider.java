package org.llm4j.openai;

import org.llm4j.api.LanguageModelFactory;
import org.llm4j.spi.LLM4JServiceProvider;

public class OpenAIServiceProvider implements LLM4JServiceProvider {
    @Override
    public LanguageModelFactory getLLMFactory() {
        return new OpenAILanguageModel.Builder();
    }
}