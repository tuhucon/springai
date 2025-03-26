package com.example.springai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ToolController {

    private final ChatClient.Builder chatClientBuilder;

    public static class DateTimeTool {

        @Tool(description = "provide the current date and time")
        public String getCurrentDateTime() {
            System.out.println("************run method getCurrentDateTime****************");
            return LocalDateTime.now().toString();
        }
    }

    @GetMapping("/now")
    public String now() {
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient
                .prompt("bây giờ là mấy giờ")
                .tools(new DateTimeTool())
                .call()
                .content();
    }
}
