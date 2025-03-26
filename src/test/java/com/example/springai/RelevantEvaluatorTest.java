package com.example.springai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.evaluation.FactCheckingEvaluator;
import org.springframework.ai.evaluation.RelevancyEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RelevantEvaluatorTest {

    @Autowired
    ChatClient.Builder chatClientBuilder;

    private FactCheckingEvaluator factCheckingEvaluator;

    @BeforeEach
    public void setup() {
        new RelevancyEvaluator(chatClientBuilder);
        factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder);
    }

    @Test
    public void testRelevantEvaluator() {
        String query = "12 * 12 = bao nhieu";
        String anwser = chatClientBuilder.build()
                .prompt()
                .user(query)
                .call()
                .content();
        System.out.println(query);
        System.out.println(anwser);
        EvaluationRequest evaluationRequest = new EvaluationRequest(query, anwser);
//        EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);
//        Assertions.assertTrue(evaluationResponse.isPass());

        EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);
        Assertions.assertTrue(evaluationResponse.isPass());

    }

    @Test
    public void testFactCheckingEvaluator() {
        String query = "Why is the sky blue?";
        String anwser = chatClientBuilder.build()
                .prompt()
                .user(query)
                .call()
                .content();
        System.out.println(query);
        System.out.println(anwser);
        EvaluationRequest evaluationRequest = new EvaluationRequest(query, anwser);
        EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);
        Assertions.assertTrue(evaluationResponse.isPass());
    }

}
