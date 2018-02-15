package com.cobinhood.responses.system;

import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;
import com.cobinhood.responses.GenericResponse;

@Getter
@ToString(callSuper = true)
public class SystemInfo extends GenericResponse {
    private String phase;
    private String revision;

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        JSONObject jsonObject = jsonNode.getObject().getJSONObject("result").getJSONObject("info");
        this.phase = jsonObject.getString("phase");
        this.revision = jsonObject.getString("revision");
    }
}
