package com.example.mois_currency.service;

import com.example.mois_currency.domain.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.var;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Service

public class CurrencyService {

    private RestTemplate restTemplate = new RestTemplate();

    public Currency getCurrencyByShortcut(String code, LocalDate date) {

        String stringDate = date.getYear() + "" + date.getMonthValue() + "" + date.getDayOfMonth();

        JSONObject currencies = restTemplate.getForObject("https://data.kurzy.cz/json/meny/b[1]den[" + date + "].json", JSONObject.class);

        Gson gson = new Gson();

        Map map1 = gson.fromJson(currencies.toJSONString(), Map.class);
        Map map2 = (Map)map1.get("kurzy");
        code = code.toUpperCase();
        Map map3 = (Map)map2.get(code);

        Currency currentCurrency = new Currency(code, (double)map3.get("jednotka"), (double)map3.get("val_nakup"));

        return currentCurrency;
    }


}
