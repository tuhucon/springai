package com.example.springai;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests for the ToolController class
 */
@WebMvcTest(ToolController.class)
class ToolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChatClient.Builder chatClientBuilder;

    /**
     * Test that the DateTimeTool throws the expected exception
     */
    @Test
    void testDateTimeTool_ThrowsException() {
        // Arrange
        ToolController.DateTimeTool dateTimeTool = new ToolController.DateTimeTool();
        ToolContext mockContext = mock(ToolContext.class);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            dateTimeTool.getCurrentDateTime(mockContext);
        });

        assertEquals("system error", exception.getMessage());
    }

    /**
     * Test that the /now endpoint properly handles exceptions.
     *
     * This test verifies that the /now endpoint in the ToolController
     * correctly handles exceptions thrown by the ChatClient. It mocks
     * the ChatClient to throw a RuntimeException and checks that the
     * response status is OK and the content contains the expected error message.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    void testNowEndpoint_HandlesException() throws Exception {
        // Arrange
        ChatClient mockChatClient = mock(ChatClient.class);
        when(chatClientBuilder.build()).thenReturn(mockChatClient);
        when(mockChatClient.prompt(anyString())).thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/now"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("co loi xay ra: Test exception"));
    }
}