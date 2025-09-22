package com.example.Teamo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Teamo.DAO.PortfolioDAO;
import com.example.Teamo.DAO.UserDAO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PortfolioService {
    private final PortfolioDAO portfolioDAO;

    @Autowired
    public PortfolioService(PortfolioDAO portfolioDAO) {
        this.portfolioDAO = portfolioDAO;
    }
}
