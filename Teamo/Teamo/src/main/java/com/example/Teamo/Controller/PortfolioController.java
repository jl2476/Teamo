package com.example.Teamo.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.Teamo.Service.PortfolioService;

public class PortfolioController {
    
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }
}
