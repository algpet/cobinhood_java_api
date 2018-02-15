package com.cobinhood.responses.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WithdrawalCryptoAddress{
    private String uuid;
    private String name;
    private String address;
    private Long createdAt;
    private String currency;
    private String type;
}


