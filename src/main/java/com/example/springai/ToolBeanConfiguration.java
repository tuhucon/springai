package com.example.springai;

import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class ToolBeanConfiguration {

    public static final String GET_INTEREST = "getInterest";

    @Bean(GET_INTEREST)
    @Description("truy vấn và trả về số tiền lãi của một user khi chơi chứng khoán")
    Function<String, Double> getInterest() {
        return Double::parseDouble;
    }

}
