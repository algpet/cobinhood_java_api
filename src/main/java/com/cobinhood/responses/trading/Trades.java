package com.cobinhood.responses.trading;

import com.cobinhood.responses.GenericListResponse;
import org.json.JSONObject;

public class Trades extends GenericListResponse<Trade> implements Iterable<Trade> {

    public Trades(){
        this.listJsonKey = "trades";
    }

    @Override
    protected Trade fromJsonObject(JSONObject jsonObject) {
        Trade trade = new Trade();
        trade.fieldsFromJsonObject(jsonObject);
        return trade;
    }
}
