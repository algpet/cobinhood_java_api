package com.cobinhood.responses.market;

import org.json.JSONObject;
import com.cobinhood.responses.GenericListResponse;
import com.cobinhood.responses.entity.Trade;


public class RecentTrades extends GenericListResponse<Trade> implements Iterable<Trade>{

    public RecentTrades(){
        this.listJsonKey = "trades";
    }

    @Override
    protected Trade fromJsonObject(JSONObject jsonObject) {
        Trade trade = new Trade();
        trade.setUuid(jsonObject.getString("id"));
        trade.setMakerSide(jsonObject.getString("maker_side"));
        trade.setAskOrderId(jsonObject.getString("ask_order_id"));
        trade.setBidOrderId(jsonObject.getString("bid_order_id"));
        trade.setTimestamp(jsonObject.getLong("timestamp"));
        trade.setPrice(jsonObject.getDouble("price"));
        trade.setSize(jsonObject.getDouble("size"));
        return trade;
    }
}


