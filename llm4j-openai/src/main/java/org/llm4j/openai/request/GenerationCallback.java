package org.llm4j.openai.request;

public interface GenerationCallback<S> {

    void onSuccess(S response);
    void onFailure(Throwable throwable);
}