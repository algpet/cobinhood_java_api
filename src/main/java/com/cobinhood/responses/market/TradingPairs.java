package com.cobinhood.responses.market;

import org.json.JSONObject;
import com.cobinhood.responses.GenericListResponse;
import com.cobinhood.responses.entity.TradingPair;

public class TradingPairs extends GenericListResponse<TradingPair> implements Iterable<TradingPair>{

    public TradingPairs(){
        this.listJsonKey = "trading_pairs";
    }

    @Override
    protected TradingPair fromJsonObject(JSONObject jsonObject) {
        TradingPair tradingPair = new TradingPair();
        tradingPair.setId(jsonObject.getString("id"));
        tradingPair.setBaseCurrencyId(jsonObject.getString("base_currency_id"));
        tradingPair.setQuoteCurrencyId(jsonObject.getString("quote_currency_id"));
        tradingPair.setBaseMaxSize(jsonObject.getDouble("base_max_size"));
        tradingPair.setBaseMinSize(jsonObject.getDouble("base_min_size"));
        tradingPair.setQuoteIncrement(jsonObject.getString("quote_increment"));
        return tradingPair;
    }
}
