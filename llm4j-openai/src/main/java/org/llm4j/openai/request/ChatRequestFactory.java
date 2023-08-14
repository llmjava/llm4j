package org.llm4j.openai.request;


import dev.ai4j.openai4j.chat.ChatCompletionRequest;
import dev.ai4j.openai4j.chat.Role;
import org.llm4j.api.ChatHistory;

import java.util.Map;

public class ChatRequestFactory extends RequestFactory<ChatCompletionRequest> {

    private final ChatCompletionRequest.Builder factory = ChatCompletionRequest.builder();

    public ChatRequestFactory withChat(ChatHistory chat) {
        // add system message
        String context = chat.getContext();
        if(context != null && !context.isEmpty()) {
            factory.addSystemMessage(context);
        }
        // add examples
        for(Map.Entry<ChatHistory.Message, ChatHistory.Message> pair: chat.getExampleList()) {
            factory.addUserMessage(pair.getKey().toString());
            factory.addUserMessage(pair.getValue().toString());
        }
        // add messages
        for(ChatHistory.Message message: chat.getMessageList()) {
            String author = message.getAuthor();
            String text = message.getContent();
            if(author==null || author.isEmpty()) {
                factory.addUserMessage(text);
            } else if(author.equalsIgnoreCase(Role.SYSTEM.toString())) {
                factory.addSystemMessage(text);
            } else if(author.equalsIgnoreCase(Role.USER.toString())) {
                factory.addUserMessage(text);
            } else if(author.equalsIgnoreCase(Role.ASSISTANT.toString())) {
                factory.addAssistantMessage(text);
            } else {
                // Unknown user
            }
        }
        return this;
    }

    @Override
    public ChatCompletionRequest build() {
        ChatCompletionRequest request = factory
                .model(modelId)
                .temperature(temperature)
                .topP(topP)
                .maxTokens(maxTokens)
                .presencePenalty(presencePenalty)
                .frequencyPenalty(frequencyPenalty)
                .build();

        return request;
    }
}
