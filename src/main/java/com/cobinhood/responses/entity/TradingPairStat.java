package com.cobinhood.responses.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TradingPairStat {
    private String id;
    private Double lastPrice;
    private Double lowestAsk;
    private Double highestBid;
    private Double baseVolume;
    private Double quoteVolume;
    private boolean isFrozen;
    private Double high24hours;
    private Double low24hours;
    private Double percentChanged24hours;
}



