package com.example.springai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
public class HelloController {
//
//    Map<Long, OrderInfo> orders = new HashMap<>();
//    Map<Integer, List<Message>> chatHistory = new HashMap<>();
//
//    @Bean
//    @Description("""
//            Tạo order. Order Id được nhận diện bởi orderID.
//            Order quality được nhận diện bởi orderQuality.
//            User được nhận diện bởi userId.
//            Order quality phải là một số nguyên dương.
//            Nếu order quality hoặc userId bị thiếu thì cần yêu cầu user cung cấp các thông tin bị thiếu này.
//            Hàm này trả về OrderInfo object chứa thông tin của order mới được tạo ra.
//            """)
//    public Function<CreateOrderRequest, OrderInfo> createOrderFn() {
//        return orderRq -> {
//            long orderId = ThreadLocalRandom.current().nextLong(1L, 1_000_000_000L);
//            System.out.println("Da tao ra order voi order id = " + orderId +
//                    " cho user id = " + orderRq.userId() +
//                    " voi order quality = " + orderRq.orderQuality());
//            OrderInfo orderInfo = new OrderInfo(orderId, orderRq.userId(), orderRq.orderQuality());
//            orders.put(orderId, orderInfo);
//            return orderInfo;
//        };
//    }
//
//    @Bean
//    @Description("""
//            Nhận về thông tin chi tiết của một order.
//            Người dùng sẽ cung cấp orderId để nhận về thông tin của order này.
//            OrderId phải là một số nguyên dương.
//            Hàm này trả về OrderInfo object chứa thông tin của order mới được tạo ra.
//            """)
//    public Function<GetOrderRequest, OrderInfo> getOrderFn() {
//        return getOrderRequest -> orders.get(getOrderRequest.orderId());
//    }
//
//    private final ChatModel chatModel;
//
//    Set<String> functions = Set.of("createOrderFn", "getOrderFn");
//
//    /*
//    All user đang bị trộn context với nhau, cần có cơ chế tách được context cho từng user
//     */
//    @GetMapping("/order")
//    public String order(@RequestParam String query, @RequestParam Integer user) {
//        List<Message> history;
//        if (chatHistory.containsKey(user)) {
//            history = chatHistory.get(user);
//        } else {
//            history = new ArrayList<>();
//            history.add(new SystemMessage("AI cần trả lời người dùng bằng tiếng Việt"));
//            history.add(new SystemMessage("AI cần trả lời người dùng một cách lịch sử, phải có vâng, ạ, dạ thưa"));
//            history.add(new SystemMessage("Người dùng là phái nam 60 tuổi"));
//            history.add(new SystemMessage("AI đóng vai trò là phái nữ tên là Kathy năm nay 18 tuổi nên cần xưng em với người dùng, và gọi người dùng là anh hoặc chị tuỳ theo giới tính người dùng"));
//            chatHistory.put(user, history);
//        }
//
//        history.add(new UserMessage(query));
//        Prompt prompt = new Prompt(history, OpenAiChatOptions.builder().functions(functions).build());
//        String response = chatModel.call(prompt).getResult().getOutput().getText();
//
//        history.add(new AssistantMessage(response));
//        while (history.size() > 14) {
//            history.remove(4);
//        }
//
//        for (Message message : history) {
//            System.out.println(message);
//        }
//        return response;
//    }
}
