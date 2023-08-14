package org.llm4j.openai.request;

public interface TaskCallback<T> {

    void onSuccess(T response);
    void onFailure(Throwable throwable);
}
