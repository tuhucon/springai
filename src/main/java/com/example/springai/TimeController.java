package com.example.springai;

import java.util.function.Function;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TimeController {

    private final ChatClient.Builder chatClientBuilder;

    private ChatClient chatClient;

    private String TIME_TEMPLATE = """
            Hãy cung cấp cho tôi thời gian hiện tại của thành phố {city}.
            Tôi cần câu trả lời một cách chính xác, do vậy nếu bạn cần thêm bất cứ thông tin gì để có thể cung cấp một câu trả lời chính xác,
            hãy hỏi lại người dùng để nhờ họ cung cấp thêm thông tin.
            """;

    public static record GetTimeRequest(String city) {}

    public static record GetTimeResponse(String currentTime) {}

    @PostConstruct
    public void init() {
        chatClient = chatClientBuilder.build();
    }

    @Bean
    @Description("""
            Trả về thời gian hiện tại của một city
            city đại diện cho một tên của một thành phố
            """)
    public Function<GetTimeRequest, GetTimeResponse> getTime() {
        return request -> new GetTimeResponse("Time hien tai cua " + request.city + " la 12-12-12");
    }

    @GetMapping("/time")
    public String getTime(@RequestParam String city) {
        return chatClient.prompt()
                .user(spec -> {
                    spec.text(TIME_TEMPLATE)
                        .param("city", city);
                })
                .functions("getTime")
                .call()
                .content();
    }
}
