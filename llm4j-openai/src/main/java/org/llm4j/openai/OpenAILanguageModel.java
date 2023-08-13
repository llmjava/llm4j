package org.llm4j.openai;

import org.apache.commons.configuration2.Configuration;
import org.llm4j.api.ChatHistory;
import org.llm4j.api.LanguageModel;
import org.llm4j.api.LanguageModelFactory;

import java.util.List;

public class OpenAILanguageModel implements LanguageModel {

    OpenAILanguageModel(Builder builder) {

    }
    @Override
    public String process(String text) {
        return null;
    }

    @Override
    public String process(ChatHistory history) {
        return null;
    }

    @Override
    public List<Float> embed(String text) {
        return null;
    }

    public static final class Builder implements LanguageModelFactory {
        private Configuration config;

        public LanguageModel getLanguageModel(Configuration config) {
            this.config = config;
            return new OpenAILanguageModel(this);
        }
    }
}
