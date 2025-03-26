package com.example.springai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.moderation.ModerationModel;
import org.springframework.ai.moderation.ModerationPrompt;
import org.springframework.ai.openai.OpenAiModerationOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ModerationController {

    private final ModerationModel moderationModel;

    @GetMapping("/mod")
    public void moderation(@RequestParam String query) {
        //Sao trong file cau hinh ko an model ???
        //Co the la do tao ModerationPrompt truc tiep ko su dung bean
        ModerationPrompt prompt = new ModerationPrompt(query, OpenAiModerationOptions.builder().model("omni-moderation-latest").build());
        System.out.println(moderationModel.call(prompt));
    }
}
