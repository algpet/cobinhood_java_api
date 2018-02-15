package com.cobinhood.responses.wallet;

import com.cobinhood.responses.GenericListResponse;
import com.cobinhood.responses.entity.DepositCryptoAddress;
import org.json.JSONObject;

public class DepositCryptoAddresses extends GenericListResponse<DepositCryptoAddress> implements Iterable<DepositCryptoAddress> {

    public DepositCryptoAddresses(){
        super();
        this.listJsonKey = "deposit_addresses";
    }

    @Override
    protected DepositCryptoAddress fromJsonObject(JSONObject jsonObject) {
        DepositCryptoAddress cryptoAddress = new DepositCryptoAddress();
        cryptoAddress.setAddress(jsonObject.getString("address"));
        cryptoAddress.setCreatedAt(jsonObject.getLong("created_at"));
        cryptoAddress.setCurrency(jsonObject.getString("currency"));
        cryptoAddress.setType(jsonObject.getString("type"));
        return cryptoAddress;
    }

}
