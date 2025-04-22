package com.example.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;
import redis.clients.jedis.JedisPooled;

@Configuration
public class BeanConfiguration {

    @Value("${spring.data.redis.host}")
    String redisHost;

    @Value("${spring.data.redis.port}")
    Integer redisPort;

    @Bean
    JedisPooled jedisPool() {
        return new JedisPooled(redisHost, redisPort); //Can we use redis cluster ???
    }

    @Bean
    ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
        return new DefaultToolExecutionExceptionProcessor(true);
    }

    @Bean("chatMemoryVectorStore")
    VectorStore chatMemoryVectorStore(JedisPooled jedis, EmbeddingModel embeddingModel) {
        return RedisVectorStore.builder(jedis, embeddingModel)
                .indexName("chatmemory")
                .prefix("chatmemory")
                .vectorAlgorithm(RedisVectorStore.Algorithm.HSNW)
                .metadataFields( //metadata field for indexing ???
                        RedisVectorStore.MetadataField.tag("conversationId") //have to hard code because it is private const in VectorStoreChaatMemoryAdvisor class.
                )
                .initializeSchema(true)
                .build();
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder builder, VectorStore vectorStore, @Qualifier("chatMemoryVectorStore") VectorStore chatMemoryVectorStore) {
        return builder
                .defaultSystem("AI cần trả lời người dùng bằng tiếng Việt.")
                .defaultSystem("AI tên là Kathy, giới tính nữ nên cần xưng em và nói chuyện lịch sự với người dùng.")
//                .defaultAdvisors( new VectorStoreChatMemoryAdvisor(chatMemoryVectorStore),
//                        new RetrievalAdvisor(vectorStore))
                .build();
    }

    @Bean
    RestClientCustomizer logbookCustomizer(LogbookClientHttpRequestInterceptor interceptor) {
        return restClient -> restClient.requestInterceptor(interceptor);
    }
}
