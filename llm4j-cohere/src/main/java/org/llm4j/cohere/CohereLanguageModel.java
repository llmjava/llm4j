package org.llm4j.cohere;

import com.github.llmjava.cohere4j.CohereClient;
import com.github.llmjava.cohere4j.CohereConfig;
import com.github.llmjava.cohere4j.request.EmbedRequest;
import com.github.llmjava.cohere4j.request.GenerateRequest;
import com.github.llmjava.cohere4j.response.EmbedResponse;
import com.github.llmjava.cohere4j.response.GenerateResponse;
import org.apache.commons.configuration2.Configuration;
import org.llm4j.api.ChatHistory;
import org.llm4j.api.LanguageModel;
import org.llm4j.api.LanguageModelFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CohereLanguageModel implements LanguageModel {
    private CohereConfig config;

    private CohereClient client;
    CohereLanguageModel(Builder builder) {
        this.client = builder.client;
        this.config = builder.config;
    }

    @Override
    public String process(String text) {
        GenerateRequest request = new GenerateRequest.Builder()
                .withConfig(config)
                .withPrompt(text)
                .build();
        GenerateResponse response = client.generate(request);
        return response.getTexts().get(0);
    }

    @Override
    public String process(ChatHistory history) {
        List<String> lines = new ArrayList<>();
        // Add context
        if(history.getContext()!=null) lines.add(history.getContext());
        // Add examples
        for(Map.Entry<ChatHistory.Message, ChatHistory.Message> pair: history.getExampleList()) {
            lines.add(pair.getKey().toString());
            lines.add(pair.getValue().toString());
        }
        // Add conversations
        for(ChatHistory.Message message: history.getMessageList()) {
            lines.add(message.toString());
        }
        // submit
        String text = String.join("\n", lines);

        return process(text);
    }

    @Override
    public List<Float> embed(String text) {
        EmbedRequest request = new EmbedRequest.Builder()
                .withText(text)
                .withModel(config.getModel())
                .build();
        EmbedResponse response = client.embed(request);
        List<Float> embeddings = Arrays.asList(response.getEmbeddings(0));
        return embeddings;
    }


    public static final class Builder implements LanguageModelFactory {
        private CohereConfig config;
        private CohereClient client;

        public LanguageModel getLanguageModel(Configuration config) {
            this.config = new CohereConfig(config);
            this.client = new CohereClient.Builder().withConfig(this.config).build();
            return new CohereLanguageModel(this);
        }
    }
}
