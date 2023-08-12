package org.llm4j.spi;

import org.llm4j.api.LanguageModelFactory;

public interface LLM4JServiceProvider {

    LanguageModelFactory getLLMFactory();
}
