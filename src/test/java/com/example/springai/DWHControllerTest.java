package com.example.springai;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DWHController.class)
public class DWHControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDwhMethodWithBasicQuery() throws Exception {
        // Basic query returns expected response
        String query = "What is the total sales for 2023?";
        
        mockMvc.perform(MockMvcRequestBuilders.get("/dwh")
                .param("query", query))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("dwh - response of: " + query)));
    }

    @Test
    public void testDwhMethodWithEmptyQuery() throws Exception {
        // Empty query parameter handling
        mockMvc.perform(MockMvcRequestBuilders.get("/dwh")
                .param("query", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("dwh - response of: ")));
    }

    @Test
    public void testDwhMethodWithNullQuery() throws Exception {
        // Null query parameter handling - the controller accepts null parameters
        mockMvc.perform(MockMvcRequestBuilders.get("/dwh"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("dwh - response of: null")));
    }

    @Test
    public void testDwhMethodWithLongQuery() throws Exception {
        // Long query string handling
        StringBuilder longQuery = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longQuery.append("test ");
        }
        
        mockMvc.perform(MockMvcRequestBuilders.get("/dwh")
                .param("query", longQuery.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("dwh - response of: " + longQuery.toString().substring(0, 20))));
    }

    @Test
    public void testDwhMethodWithSpecialCharacters() throws Exception {
        // Special characters in query
        String specialCharsQuery = "SELECT * FROM sales WHERE date='2023-01-01' AND product_id=123;";
        
        mockMvc.perform(MockMvcRequestBuilders.get("/dwh")
                .param("query", specialCharsQuery))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("dwh - response of: " + specialCharsQuery)));
    }

    @Test
    public void testDwhEndpointReturnsCorrectStatus() throws Exception {
        // API endpoint returns correct status
        mockMvc.perform(MockMvcRequestBuilders.get("/dwh")
                .param("query", "test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8")); // Expecting a plain text response
    }
}