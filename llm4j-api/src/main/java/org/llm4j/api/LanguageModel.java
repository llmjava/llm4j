package org.llm4j.api;

import java.util.List;

public interface LanguageModel {

    String process(String text);

    String process(ChatHistory history);

    List<Float> embed(String text);
}

