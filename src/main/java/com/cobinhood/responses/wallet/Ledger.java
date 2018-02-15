package com.cobinhood.responses.wallet;

import com.cobinhood.responses.GenericListResponse;
import com.cobinhood.responses.entity.DepositCryptoAddress;
import com.cobinhood.responses.entity.LedgerEntry;
import org.json.JSONObject;

public class Ledger extends GenericListResponse<LedgerEntry> implements Iterable<LedgerEntry>{

    public Ledger(){
        super();
        this.listJsonKey = "ledger";
    }

    @Override
    protected LedgerEntry fromJsonObject(JSONObject jsonObject) {
        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setTimestamp(jsonObject.getString("timestamp"));
        ledgerEntry.setCurrency(jsonObject.getString("currency"));
        ledgerEntry.setType(jsonObject.getString("type"));
        ledgerEntry.setAction(jsonObject.getString("action"));
        ledgerEntry.setAmount(jsonObject.getDouble("amount"));
        ledgerEntry.setBalance(jsonObject.getDouble("balance"));

        if(jsonObject.get("trade_id").equals(null)){
            ledgerEntry.setTradeId(null);
        }
        else {
            ledgerEntry.setTradeId(jsonObject.getString("trade_id"));
        }

        if(jsonObject.get("deposit_id").equals(null)){
            ledgerEntry.setDepositId(null);
        }
        else {
            ledgerEntry.setDepositId(jsonObject.getString("deposit_id"));
        }

        if(jsonObject.get("withdrawal_id").equals(null)){
            ledgerEntry.setWithdrawalId(null);
        }
        else {
            ledgerEntry.setWithdrawalId(jsonObject.getString("withdrawal_id"));
        }

        if(jsonObject.get("fiat_deposit_id").equals(null)){
            ledgerEntry.setFiatDepositId(null);
        }
        else {
            ledgerEntry.setFiatDepositId(jsonObject.getString("fiat_deposit_id"));
        }

        if(jsonObject.get("fiat_withdrawal_id").equals(null)){
            ledgerEntry.setFiatWithdrawalId(null);
        }
        else {
            ledgerEntry.setFiatWithdrawalId(jsonObject.getString("fiat_withdrawal_id"));
        }
        return ledgerEntry;
    }
}
