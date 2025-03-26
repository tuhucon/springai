package com.example.springai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AIController {

    private final ChatClient chatClient;

    private final VectorStore vectorStore;

    @Value("file:///Users/tuhucon/Desktop/Agg1.pdf")
    Resource resourcePath;

    @GetMapping("/ai")
    public String generate(@RequestParam String query, @RequestParam String id) {
        var x=  chatClient.prompt()
                .user(query)
                .advisors(adSpec -> {
                    adSpec.param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, id);
                })
                .call();
        return x.content();
    }

    @GetMapping("/index")
    public void index() {
        this.indexDocs();
    }

    @GetMapping("/search")
    public void search(@RequestParam String query) {
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .topK(5)
                        .similarityThreshold(0.8)
                        .query(query)
                        .build()
        );
        if (docs != null) {
            log.info(docs.getFirst().getText());
        } else {
            log.info("No documents found");
        }
    }

    /*
    index docs vao trong vector store (redis)
    Document object nhan vao content (string) va metadata (key,value)
     */
    private void indexDocs() {
        TikaDocumentReader reader = new TikaDocumentReader(resourcePath);
        List<Document> docs = reader.read();
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> chunks = textSplitter.split(docs);
        vectorStore.add(chunks);
    }
}
