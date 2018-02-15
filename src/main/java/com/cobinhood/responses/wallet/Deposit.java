package com.cobinhood.responses.wallet;

import com.cobinhood.responses.GenericResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@Setter
@ToString(callSuper = true)
public class Deposit extends GenericResponse {
    private String depositId;
    private Double amount;
    private Long completedAt;
    private Long createdAt;
    private Integer confirmations;
    private String currency;
    private Double fee;
    private String fromAddress;
    private Integer requiredConfirmations;
    private String status;
    private String txHash;
    private String userId;

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        JSONObject jsonObject = jsonNode.getObject().getJSONObject("result").getJSONObject("deposit");
        this.fieldsFromJsonObject(jsonObject);
    }

    public void fieldsFromJsonObject(JSONObject jsonObject){
        this.setAmount(jsonObject.getDouble("amount"));
        this.setCurrency(jsonObject.getString("currency"));
        this.setCompletedAt(jsonObject.getLong("completed_at"));
        this.setCreatedAt(jsonObject.getLong("created_at"));
        this.setConfirmations(jsonObject.getInt("confirmations"));
        this.setDepositId(jsonObject.getString("deposit_id"));
        this.setFee(jsonObject.getDouble("fee"));
        this.setFromAddress(jsonObject.getString("from_address"));
        this.setRequiredConfirmations(jsonObject.getInt("required_confirmations"));
        this.setStatus(jsonObject.getString("status"));
        this.setTxHash(jsonObject.getString("txhash"));
        this.setUserId(jsonObject.getString("user_id"));
    }
}






