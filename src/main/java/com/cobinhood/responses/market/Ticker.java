package com.cobinhood.responses.market;

import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;
import com.cobinhood.responses.GenericResponse;

@Getter
@Setter
@ToString
public class Ticker extends GenericResponse {
    private String tradingPairId;
    private Double lastPrice;
    private Double lowestAsk;
    private Double highestBid;
    private Double high24hours;
    private Double low24hours;
    private Double open24hours;
    private Double volume24hours;
    private Long timestamp;

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        JSONObject jsonObject = jsonNode.getObject().getJSONObject("result").getJSONObject("ticker");
        this.tradingPairId = jsonObject.getString("trading_pair_id");
        this.lastPrice = jsonObject.getDouble("last_trade_price");
        this.lowestAsk = jsonObject.getDouble("lowest_ask");
        this.highestBid = jsonObject.getDouble("highest_bid");
        this.high24hours = jsonObject.getDouble("24h_high");
        this.low24hours = jsonObject.getDouble("24h_low");
        this.open24hours = jsonObject.getDouble("24h_open");
        this.volume24hours = jsonObject.getDouble("24h_volume");
        this.timestamp = jsonObject.getLong("timestamp");
    }
}





