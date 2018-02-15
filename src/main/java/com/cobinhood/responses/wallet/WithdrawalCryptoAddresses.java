package com.cobinhood.responses.wallet;

import com.cobinhood.responses.GenericListResponse;
import com.cobinhood.responses.entity.WithdrawalCryptoAddress;
import org.json.JSONObject;

public class WithdrawalCryptoAddresses extends GenericListResponse<WithdrawalCryptoAddress> implements Iterable<WithdrawalCryptoAddress> {

    public WithdrawalCryptoAddresses(){
        super();
        this.listJsonKey = "withdrawal_addresses";
    }

    @Override
    protected WithdrawalCryptoAddress fromJsonObject(JSONObject jsonObject) {
        WithdrawalCryptoAddress cryptoAddress = new WithdrawalCryptoAddress();
        cryptoAddress.setAddress(jsonObject.getString("address"));
        cryptoAddress.setCreatedAt(jsonObject.getLong("created_at"));
        cryptoAddress.setCurrency(jsonObject.getString("currency"));
        cryptoAddress.setType(jsonObject.getString("type"));
        cryptoAddress.setName(jsonObject.getString("name"));
        cryptoAddress.setUuid(jsonObject.getString("id"));
        return cryptoAddress;
    }
}
