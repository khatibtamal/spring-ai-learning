package com.khatib.springai.learning.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Tool(description = "This method can be used to fetch the current stock price for a given company.",
            name = "getStockPrice")
    public String getStockPrice(@ToolParam(description = "The name of the company whose current stock price need to be fetched") String companyName) {
        return String.format("The current price of %s is $100.00", companyName);
    }
}