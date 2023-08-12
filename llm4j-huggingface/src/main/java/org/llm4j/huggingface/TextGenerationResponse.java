package org.llm4j.huggingface;

public class TextGenerationResponse {

    private final String generatedText;

    public TextGenerationResponse(String generatedText) {
        this.generatedText = generatedText;
    }

    public String getGeneratedText() {
        return generatedText;
    }
}