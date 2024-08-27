package com.example.portfoliomanagement.controller;

import com.example.portfoliomanagement.model.Portfolio;
import com.example.portfoliomanagement.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long id) {
        Optional<Portfolio> portfolio = portfolioService.getPortfolioById(id);
        return portfolio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody Portfolio portfolio) {
        Portfolio savedPortfolio = portfolioService.savePortfolio(portfolio);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPortfolio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable Long id, @RequestBody Portfolio portfolio) {
        if (!portfolioService.getPortfolioById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        portfolio.setId(id);
        Portfolio updatedPortfolio = portfolioService.savePortfolio(portfolio);
        return ResponseEntity.ok(updatedPortfolio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        if (!portfolioService.getPortfolioById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        portfolioService.deletePortfolio(id);
        return ResponseEntity.noContent().build();
    }
}
