package com.cobinhood.responses.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Currency {
    private String currency;
    private String name;
    private String minUnit;
    private Double depositFee;
    private Double withdrawalFee;
}


