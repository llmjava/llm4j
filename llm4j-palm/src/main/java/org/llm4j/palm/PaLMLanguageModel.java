package org.llm4j.palm;

import com.google.ai.generativelanguage.v1beta2.*;
import org.apache.commons.configuration2.Configuration;
import org.llm4j.api.ChatHistory;
import org.llm4j.api.LanguageModel;
import org.llm4j.api.LanguageModelFactory;
import org.llm4j.palm.request.EmbedRequestFactory;
import org.llm4j.palm.request.MessageRequestFactory;
import org.llm4j.palm.request.TextRequestFactory;

import java.util.List;
import java.util.Map;

public class PaLMLanguageModel implements LanguageModel {

    private Configuration config;
    private PaLMClient client;

    public PaLMLanguageModel(Builder builder) {
        this.config = builder.config;
        this.client = builder.client;
    }

    @Override
    public String process(String text) {
        GenerateTextRequest request = new TextRequestFactory()
                .withInputs(text)
                .withConfig(config)
                .build();

        TextCompletion response = client.generate(request);

        return response.getOutput();
    }

    @Override
    public String process(ChatHistory history) {
        MessageRequestFactory factory = new MessageRequestFactory();
        factory.withContext(history.getContext());
        for(Map.Entry<ChatHistory.Message, ChatHistory.Message> pair: history.getExampleList()) {
            ChatHistory.Message left = pair.getKey();
            ChatHistory.Message right = pair.getValue();
            factory.withExample(left.toString(), right.toString());
        }
        for(ChatHistory.Message message: history.getMessageList()) {
            factory.withMessage(message.getAuthor(), message.getContent());
        }
        GenerateMessageRequest request = factory.withConfig(config).build();

        Message response = client.chat(request);

        return response.toString();
    }

    @Override
    public List<Float> embed(String text) {
        EmbedTextRequest request = new EmbedRequestFactory()
                .withConfig(config)
                .withText(text)
                .build();
        List<Float> embeddings = client.embed(request);
        return embeddings;
    }

    public static final class Builder implements LanguageModelFactory {
        private Configuration config;
        private PaLMClient client;

        public LanguageModel getLanguageModel(Configuration config) {
            this.config = config;
            this.client = new PaLMClient.Builder().withConfig(config).build();
            return new PaLMLanguageModel(this);
        }
    }
}
