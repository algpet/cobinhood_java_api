package com.cobinhood.responses.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Trade {
    private String uuid;
    private String makerSide;
    private String askOrderId;
    private String bidOrderId;
    private Long timestamp;
    private Double price;
    private Double size;
}


