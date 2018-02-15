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
public class Order extends GenericResponse {
     private String uuid;
     private String tradingPair;
     private String side;
     private String type;
     private Double price;
     private Double size;
     private Double filled;
     private String state;
     private Long timestamp;
     private Double eqPrice;
     private String completedAt;

     @Override
     public void childFieldsFromJsonNode(JsonNode jsonNode) {
          JSONObject jsonObject = jsonNode.getObject().getJSONObject("result").getJSONObject("order");
          this.fieldsFromJsonObject(jsonObject);
     }

     public void fieldsFromJsonObject(JSONObject jsonObject){
          this.setUuid(jsonObject.getString("id"));
          this.setTradingPair(jsonObject.getString("trading_pair"));
          this.setSide(jsonObject.getString("side"));
          this.setType(jsonObject.getString("type"));
          this.setPrice(jsonObject.getDouble("price"));
          this.setSize(jsonObject.getDouble("size"));
          this.setFilled(jsonObject.getDouble("filled"));
          this.setState(jsonObject.getString("state"));
          this.setTimestamp(jsonObject.getLong("timestamp"));
          this.setEqPrice(jsonObject.getDouble("eq_price"));

          Object completed_at = jsonObject.get("completed_at");
          if(completed_at.equals(null)){
               this.setCompletedAt(null);
          }
          else {
               this.setCompletedAt(jsonObject.getString("completed_at"));
          }
     }

}

