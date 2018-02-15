package com.cobinhood.responses.market;

import org.json.JSONObject;
import com.cobinhood.responses.GenericListResponse;
import com.cobinhood.responses.entity.Currency;

public class Currencies extends GenericListResponse<Currency> implements Iterable<Currency>{

    public Currencies () {
        this.listJsonKey = "currencies";
    }

    @Override
    protected Currency fromJsonObject(JSONObject jsonObject) {
        Currency currency = new Currency();
        currency.setCurrency(jsonObject.getString("currency"));
        currency.setName(jsonObject.getString("name"));
        currency.setDepositFee(jsonObject.getDouble("deposit_fee"));
        currency.setWithdrawalFee(jsonObject.getDouble("withdrawal_fee"));
        currency.setMinUnit(jsonObject.getString("min_unit"));
        return currency;
    }
}
