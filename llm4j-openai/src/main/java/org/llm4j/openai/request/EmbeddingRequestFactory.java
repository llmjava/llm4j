package org.llm4j.openai.request;


import dev.ai4j.openai4j.embedding.EmbeddingRequest;

public class EmbeddingRequestFactory extends RequestFactory<EmbeddingRequest> {

    private String text;
    private String user;

    public EmbeddingRequestFactory withText(String text) {
        this.text = text;
        return this;
    }

    public EmbeddingRequestFactory withUser(String user) {
        this.user = user;
        return this;
    }
    @Override
    public EmbeddingRequest build() {
        EmbeddingRequest request = EmbeddingRequest.builder()
                .model(modelId)
                .input(text)
                .user(user)
                .build();
        return request;
    }
}
