package com.cobinhood.responses.system;

import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;
import com.cobinhood.responses.GenericResponse;

@Getter
@ToString(callSuper = true)
public class SystemTime extends GenericResponse {
    private Long time;

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        JSONObject jsonObject = jsonNode.getObject().getJSONObject("result");
        this.time = jsonObject.getLong("time");
    }
}
