package org.llm4j.api;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatHistory {

    String context;
    List<Map.Entry<Message, Message>> exampleList = new ArrayList<>();
    List<Message> messageList = new ArrayList<>();

    public ChatHistory setContext(String context) {
        this.context = context;
        return this;
    }

    public ChatHistory addExample(String text1, String text2) {
        Map.Entry<Message, Message> pair = new ImmutablePair<>(new Message(text1), new Message(text1));
        exampleList.add(pair);
        return this;
    }

    public ChatHistory addMessage(String content) {
        messageList.add(new Message(content));
        return this;
    }

    public ChatHistory addMessage(String author, String content) {
        messageList.add(new Message(author, content));
        return this;
    }

    public String getContext() {
        return context;
    }

    public List<Map.Entry<Message, Message>> getExampleList() {
        return exampleList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public static class Message {

        private final String author;
        private final String content;

        public Message(String author, String content) {
            this.author = author;
            this.content = content;
        }

        public Message(String content) {
            this(null, content);
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            String text = "";
            if(author!=null && !author.isEmpty()) {
                text += author;
            }
            if(content!=null && !content.isEmpty()) {
                text += text.isEmpty() ? content: ":" + content;
            }
            return text;
        }
    }
}
