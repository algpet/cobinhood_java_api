package com.cobinhood.responses.wallet;

import com.cobinhood.responses.GenericListResponse;
import org.json.JSONObject;

public class WithdrawalHistory extends GenericListResponse<Withdrawal> implements Iterable<Withdrawal> {
    public WithdrawalHistory(){
        this.listJsonKey = "withdrawals";
    }

    @Override
    protected Withdrawal fromJsonObject(JSONObject jsonObject) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.fieldsFromJsonObject(jsonObject);
        return withdrawal;
    }
}
