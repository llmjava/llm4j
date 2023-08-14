package org.llm4j.openai.request;

public interface StreamingCallback<S> {

    void onPart(S response);
    void onComplete();
    void onFailure(Throwable throwable);
}
