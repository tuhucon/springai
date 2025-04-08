package com.example.springai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DWHController {

    /**
     * Endpoint to connect to the data warehouse, execute a query, and generate a report.
     * The AI needs to pass the raw question of the user into the query parameter.
     *
     * @param query the raw question of the users
     * @return a response string indicating the result of the query
     */
    @GetMapping("/dwh")
    @Tool(
            description = """
                    connect to data warehouse, implement query and generate report.
                    data warehouse: DWH, dwh.
                    AI need to pass the raw question of user into query parameter.
                    """,
            returnDirect = true
    )
    public String dwh(@ToolParam(description = "the raw question of the users") String query) {
        System.out.println("query: " + query);
        return "dwh - response of: " + query;
    }

    @Tool(
            description = """
                    connect to customer services, implement query and generate report.
                    customer services: CS
                    AI need to pass the raw question of user into query parameter
                    """,
            returnDirect = true
    )
    public String customer(@ToolParam(description = "the raw question of the users") String query) {
        return  "customer - response of: " + query;
    }
}
