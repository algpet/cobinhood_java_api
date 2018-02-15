package com.cobinhood.responses.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Candle {

    @Getter
    public static enum Timeframe{
        TIMEFRAME_1_MINUTE ("1m"),
        TIMEFRAME_5_MINUTES ("5m"),
        TIMEFRAME_15_MINUTES ("5m"),
        TIMEFRAME_30_MINUTES ("5m"),
        TIMEFRAME_1_HOUR ("5m"),
        TIMEFRAME_3_HOURS ("3h"),
        TIMEFRAME_6_HOURS ("6h"),
        TIMEFRAME_12_HOURS ("12h"),
        TIMEFRAME_1_DAY ("1D"),
        TIMEFRAME_7_DAYS ("7D"),
        TIMEFRAME_14_DAYS ("14D"),
        TIMEFRAME_1_MONTH ("1M");

        private String timeframe;
        Timeframe(String timeframe){
            this.timeframe = timeframe;
        }

    }

    private String tradingPairId;
    private String timeframe;
    private Long timestamp;
    private Double volume;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
}






