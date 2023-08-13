package org.llm4j.api;

public interface LanguageModel {

    String process(String text);

    String process(ChatHistory history);
}

