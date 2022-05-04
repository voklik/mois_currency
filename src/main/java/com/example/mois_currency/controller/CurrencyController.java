package com.example.mois_currency.controller;

import com.example.mois_currency.domain.Currency;
import com.example.mois_currency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rest/api/currency")
public class CurrencyController {

    public final CurrencyService currencyService;

    @GetMapping("/{code}/{date}")
    @DateTimeFormat(pattern="dd-MM-yyyy")
    public Currency getCurrency(@PathVariable String code, @PathVariable LocalDate date) {

        Currency currency = currencyService.getCurrencyByShortcut(code, date);
        return currency;
    }
}
