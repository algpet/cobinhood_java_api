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
    private List<OrderBookEntry> bids;
    private List<OrderBookEntry> asks;

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        JSONObject jsonObject = jsonNode.getObject().getJSONObject("result").getJSONObject("orderbook");
        asks = readSide("asks",jsonObject.getJSONArray("asks"));
        bids = readSide("bids",jsonObject.getJSONArray("bids"));
    }

    public ArrayList<OrderBookEntry> readSide(String side,JSONArray jsonArray){
        ArrayList<OrderBookEntry> sideArray = new ArrayList<>();
        for (int i = 0 ; i < jsonArray.length(); i++) {
            JSONArray item = (JSONArray) jsonArray.get(i);
            OrderBookEntry orderBookEntry = new OrderBookEntry();
            orderBookEntry.setSide(side);
            orderBookEntry.setPrice(item.getDouble(0));
            orderBookEntry.setVolume(item.getDouble(2));
            sideArray.add(orderBookEntry);
        }
        return sideArray;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "number of bids=" + bids.size() + ", " +
                "number of asks=" + asks.size() +
                "} " + super.toString();
    }

}
