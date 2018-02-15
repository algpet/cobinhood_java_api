package com.cobinhood.responses.market;

import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;
import com.cobinhood.responses.GenericResponse;
import com.cobinhood.responses.entity.OrderBookEntry;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderBook extends GenericResponse{
    private List<OrderBookEntry> bids = new ArrayList<>();
    private List<OrderBookEntry> asks = new ArrayList<>();

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        JSONObject jsonObject = jsonNode.getObject().getJSONObject("result").getJSONObject("orderbook");

        if (!jsonObject.isNull("asks"))
            readSide("asks",jsonObject.getJSONArray("asks"),asks);
        if (!jsonObject.isNull("bids"))
            readSide("bids",jsonObject.getJSONArray("bids"),bids);
    }

    public void readSide(String side,JSONArray jsonArray,List<OrderBookEntry> sideArray){

        for (int i = 0 ; i < jsonArray.length(); i++) {
            JSONArray item = (JSONArray) jsonArray.get(i);
            OrderBookEntry orderBookEntry = new OrderBookEntry();
            orderBookEntry.setSide(side);
            orderBookEntry.setPrice(item.getDouble(0));
            orderBookEntry.setVolume(item.getDouble(2));
            sideArray.add(orderBookEntry);
        }
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "number of bids=" + bids.size() + ", " +
                "number of asks=" + asks.size() +
                "} " + super.toString();
    }

}
