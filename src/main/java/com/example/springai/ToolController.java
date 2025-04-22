package com.example.springai;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ToolController {

    private final ChatClient.Builder chatClientBuilder;

    private final List<McpSyncClient> mcpClients;

    public static class DateTimeTool {

        @Tool(description = "provide the current date and time")
        public String getCurrentDateTime(ToolContext context) {
            throw new RuntimeException("system error");
//            System.out.println("************run method getCurrentDateTime****************");
//            System.out.println("context: " + context.getContext());
//            System.out.println("tool call history: " + context.getToolCallHistory());
//            return LocalDateTime.now().toString();
        }
    }

    @GetMapping("/now")
    public String now() {
        try {
            ChatClient chatClient = chatClientBuilder.build();
            return chatClient
                    .prompt("bây giờ là mấy giờ")
                    .tools(new DateTimeTool())
                    .toolContext(Map.of("workspace id", 1234, "workspace name", "tu hu con"))
                    .call()
                    .content();
        } catch (Exception e) {
            return "co loi xay ra: " + e.getMessage();
        }
    }
    @GetMapping("/tool")
    public String query(@RequestParam String query) {

//        McpSyncClient mcpClient = mcpClients.getFirst();
//        McpSchema.CallToolRequest request = new McpSchema.CallToolRequest("randomName", new HashMap<>());
//
//        var result = mcpClient.callTool(request).content();
//
//        log.info("********MCP RESULT**********" + result.getFirst().toString());


        SyncMcpToolCallbackProvider toolCallbackProvider = new SyncMcpToolCallbackProvider(mcpClients);


        ChatClient chatClient = chatClientBuilder.build();
        return chatClient.prompt(query)
                .tools(new DWHController(), new DateTimeTool()) //DWHCOntroller is a 3rd AI services
                .tools(toolCallbackProvider)
                .toolContext(Map.of("workspace id", 1234, "workspace name", "tu hu con"))
                .call()
                .content();

    }
}
