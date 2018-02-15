package com.cobinhood.responses;

import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class GenericResponse {
    private  boolean success;
    private  String message;
    private String error;

    public void fromJsonNode(JsonNode jsonNode){
        this.success = jsonNode.getObject().getBoolean("success");
        if(jsonNode.getObject().has("message"))
            this.message = jsonNode.getObject().getString("message");
        if(jsonNode.getObject().has("error"))
            this.error = jsonNode.getObject().getJSONObject("error").getString("error_code");
        if(this.success)
            this.childFieldsFromJsonNode(jsonNode);
    }
    public abstract void childFieldsFromJsonNode(JsonNode jsonNode);
}
