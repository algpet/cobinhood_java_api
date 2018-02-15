package com.cobinhood.responses.market;

import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;
import com.cobinhood.responses.GenericListResponse;
import com.cobinhood.responses.entity.Candle;

public class Candles extends GenericListResponse<Candle> implements Iterable<Candle>{

    public Candles(){
        this.listJsonKey = "candles";
    }

    @Override
    protected Candle fromJsonObject(JSONObject jsonObject) {
        Candle candle = new Candle();
        candle.setTradingPairId(jsonObject.getString("trading_pair_id"));
        candle.setTimeframe(jsonObject.getString("timeframe"));
        candle.setTimestamp(jsonObject.getLong("timestamp"));
        candle.setVolume(jsonObject.getDouble("volume"));
        candle.setOpen(jsonObject.getDouble("open"));
        candle.setClose(jsonObject.getDouble("close"));
        candle.setHigh(jsonObject.getDouble("high"));
        candle.setLow(jsonObject.getDouble("low"));
        return candle;
    }
}

