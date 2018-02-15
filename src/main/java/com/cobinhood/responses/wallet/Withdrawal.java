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
public class Withdrawal extends GenericResponse{
    private String withdrawalId;
    private String userId;
    private String status;
    private Integer confirmations;
    private Integer requiredConfirmations;
    private Long createdAt;
    private Long sentAt;
    private Long completedAt;
    private Long updatedAt;
    private String toAddress;
    private String txHash;
    private String currency;
    private Double amount;
    private Double fee;

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        JSONObject jsonObject = jsonNode.getObject().getJSONObject("result").getJSONObject("withdrawal");
        this.fieldsFromJsonObject(jsonObject);
    }

    public void fieldsFromJsonObject(JSONObject jsonObject){
        this.setWithdrawalId(jsonObject.getString("withdrawal_id"));
        this.setUserId(jsonObject.getString("user_id"));
        this.setStatus(jsonObject.getString("status"));
        this.setConfirmations(jsonObject.getInt("confirmations"));
        this.setRequiredConfirmations(jsonObject.getInt("required_confirmations"));
        this.setCreatedAt(jsonObject.getLong("created_at"));
        this.setSentAt(jsonObject.getLong("sent_at"));
        this.setCompletedAt(jsonObject.getLong("completed_at"));
        this.setUpdatedAt(jsonObject.getLong("updated_at"));
        this.setToAddress(jsonObject.getString("to_address"));
        this.setTxHash(jsonObject.getString("txhash"));
        this.setCurrency(jsonObject.getString("currency"));
        this.setAmount(jsonObject.getDouble("amount"));
        this.setFee(jsonObject.getDouble("fee"));
    }
}


