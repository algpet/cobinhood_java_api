package com.cobinhood.responses.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TradingPair {
    private String id;
    private String baseCurrencyId;
    private String quoteCurrencyId;
    private Double baseMaxSize;
    private Double baseMinSize;
    private String quoteIncrement;
}