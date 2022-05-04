package com.example.mois_currency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Currency {

    private String code;
    private double unit;
    private double price;

}