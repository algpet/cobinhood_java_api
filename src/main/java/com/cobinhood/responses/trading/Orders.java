package com.cobinhood.responses.trading;

import org.json.JSONObject;
import com.cobinhood.responses.GenericListResponse;

public class Orders extends GenericListResponse<Order> implements Iterable<Order>{
    public Orders(){
        this.listJsonKey = "orders";
    }

    @Override
    protected Order fromJsonObject(JSONObject jsonObject) {
        Order order = new Order();
        order.fieldsFromJsonObject(jsonObject);
        return order;
    }
}

