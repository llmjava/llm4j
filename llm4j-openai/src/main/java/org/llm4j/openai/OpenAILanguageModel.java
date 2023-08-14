package org.llm4j.openai;

import dev.ai4j.openai4j.chat.ChatCompletionRequest;
import dev.ai4j.openai4j.chat.ChatCompletionResponse;
import dev.ai4j.openai4j.completion.CompletionRequest;
import dev.ai4j.openai4j.completion.CompletionResponse;
import org.apache.commons.configuration2.Configuration;
import org.llm4j.api.ChatHistory;
import org.llm4j.api.LanguageModel;
import org.llm4j.api.LanguageModelFactory;
import org.llm4j.openai.request.ChatRequestFactory;
import org.llm4j.openai.request.TextRequestFactory;

import java.util.List;
import java.util.Map;

public class OpenAILanguageModel implements LanguageModel {

    private final OpenAIClient client;
    private final OpenAIConfig config;
    OpenAILanguageModel(Builder builder) {
        this.client = builder.client;
        this.config = builder.config;
    }
    @Override
    public String process(String text) {
        CompletionRequest request = new TextRequestFactory()
                .withText(text)
                .withConfig(config)
                .build();

        CompletionResponse response = client.generate(request);

        return response.text();
    }

    @Override
    public String process(ChatHistory history) {
        ChatCompletionRequest request = new ChatRequestFactory()
                .withChat(history)
                .withConfig(config)
                .build();

        ChatCompletionResponse response = client.generate(request);
        return response.content();
    }

    @Override
    public List<Float> embed(String text) {
        return null;
    }

    public static final class Builder implements LanguageModelFactory {
        private OpenAIClient client;
        private OpenAIConfig config;

        public LanguageModel getLanguageModel(Configuration config) {
            this.config = new OpenAIConfig(config);
            this.client = new OpenAIClient.Builder()
                    .withConfig(this.config)
                    .build();
            return new OpenAILanguageModel(this);
        }
    }
}
