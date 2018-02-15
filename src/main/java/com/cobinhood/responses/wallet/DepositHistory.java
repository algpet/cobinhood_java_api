package com.cobinhood.responses.wallet;

import com.cobinhood.responses.GenericListResponse;
import org.json.JSONObject;

public class DepositHistory extends GenericListResponse<Deposit> implements Iterable<Deposit> {
    public DepositHistory(){
        this.listJsonKey = "deposits";
    }

    @Override
    protected Deposit fromJsonObject(JSONObject jsonObject) {
        Deposit deposit = new Deposit();
        deposit.fieldsFromJsonObject(jsonObject);
        return deposit;
    }
}
