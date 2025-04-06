package com.example.springai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ToolController {

    private final ChatClient.Builder chatClientBuilder;

    public static class DateTimeTool {

        @Tool(description = "provide the current date and time")
        public String getCurrentDateTime(ToolContext context) {
            System.out.println("************run method getCurrentDateTime****************");
            System.out.println("context: " + context.getContext());
            System.out.println("tool call history: " + context.getToolCallHistory());
            return LocalDateTime.now().toString();
        }
    }

    @GetMapping("/now")
    public String now() {
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient
                .prompt("bây giờ là mấy giờ")
                .tools(new DateTimeTool())
                .toolContext(Map.of("workspace id", 1234, "workspace name", "tu hu con"))
                .call()
                .content();
    }
    @GetMapping("/tool")
    public String query(@RequestParam String query) {
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient.prompt(query)
                .tools(new DWHController(), new DateTimeTool()) //DWHCOntroller is a 3rd AI services
                .call()
                .content();
    }
}
