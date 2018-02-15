package com.cobinhood.responses.market;

import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import org.json.JSONObject;
import com.cobinhood.responses.GenericResponse;
import com.cobinhood.responses.entity.TradingPairStat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Getter
public class TradingPairStatistics extends GenericResponse{
    private Map<String,TradingPairStat> statistics;

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        this.statistics = new HashMap<>();

        JSONObject jsonObject;
        jsonObject = jsonNode.getObject().getJSONObject("result");
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()){
            String key = keys.next();
            JSONObject jsonStatObject = jsonObject.getJSONObject(key);
            TradingPairStat tradingPairStat = fromJsonObject(jsonStatObject);
            this.statistics.put(key,tradingPairStat);
        }
    }

    private TradingPairStat fromJsonObject(JSONObject jsonObject) {
        TradingPairStat tradingPairStat = new TradingPairStat();
        tradingPairStat.setId(jsonObject.getString("id"));
        tradingPairStat.setLastPrice(jsonObject.getDouble("last_price"));
        tradingPairStat.setLowestAsk(jsonObject.getDouble("lowest_ask"));
        tradingPairStat.setHighestBid(jsonObject.getDouble("highest_bid"));
        tradingPairStat.setBaseVolume(jsonObject.getDouble("base_volume"));
        tradingPairStat.setQuoteVolume(jsonObject.getDouble("quote_volume"));
        tradingPairStat.setFrozen(jsonObject.getBoolean("is_frozen"));
        tradingPairStat.setHigh24hours(jsonObject.getDouble("high_24hr"));
        tradingPairStat.setLow24hours(jsonObject.getDouble("low_24hr"));
        tradingPairStat.setPercentChanged24hours(jsonObject.getDouble("percent_changed_24hr"));

        return tradingPairStat;
    }

    @Override
    public String toString() {
        return "TradingPairStatistics{" +
                "number of items=" + statistics.size() +
                "} " + super.toString();
    }
}

