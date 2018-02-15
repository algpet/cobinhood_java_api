package com.cobinhood.responses.trading;

import com.cobinhood.responses.GenericResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@Setter
@ToString(callSuper = true)
public class Trade extends GenericResponse {
    private String uuid;
    private String makerSide;
    private String askOrderId;
    private String bidOrderId;
    private Long timestamp;
    private Double price;
    private Double size;

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        JSONObject jsonObject = jsonNode.getObject().getJSONObject("result").getJSONObject("trade");
        this.fieldsFromJsonObject(jsonObject);
    }

    public void fieldsFromJsonObject(JSONObject jsonObject) {
        this.setUuid(jsonObject.getString("id"));
        this.setMakerSide(jsonObject.getString("maker_side"));
        this.setAskOrderId(jsonObject.getString("ask_order_id"));
        this.setBidOrderId(jsonObject.getString("bid_order_id"));
        this.setTimestamp(jsonObject.getLong("timestamp"));
        this.setSize(jsonObject.getDouble("size"));
        this.setPrice(jsonObject.getDouble("price"));
    }
}


