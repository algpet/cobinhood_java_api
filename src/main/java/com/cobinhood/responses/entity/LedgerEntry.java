package com.cobinhood.responses.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LedgerEntry {
    private String timestamp;
    private String currency;
    private String type;
    private String action;
    private Double amount;
    private Double balance;
    private String tradeId;
    private String depositId;
    private String withdrawalId;
    private String fiatDepositId;
    private String fiatWithdrawalId;
}


