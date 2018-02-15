package com.cobinhood;

import com.cobinhood.responses.GenericResponse;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.json.JSONObject;

public class CobinhoodHttpRequest extends HttpRequestWithBody {

    private static final String LIVE_API = "https://api.cobinhood.com";

    private String endpoint;
    private JSONObject jsonBody = new JSONObject();

    public  CobinhoodHttpRequest(HttpMethod method, String endpoint) {
        super(method, LIVE_API + endpoint);
        this.endpoint = endpoint;
    }

    public CobinhoodHttpRequest routeParam(String name,String value){
        if(value == null)
            return this;
        return (CobinhoodHttpRequest) super.routeParam(name,value);
    }
    public CobinhoodHttpRequest queryString(String name,String value){
        if(value == null)
            return this;
        return (CobinhoodHttpRequest) super.queryString(name,value);
    }
    public CobinhoodHttpRequest queryString(String name,Object value){
        if(value == null)
            return this;
        return (CobinhoodHttpRequest) super.queryString(name,value);
    }

    public CobinhoodHttpRequest withJsonField(String name,Object value) {
        if (value == null || this.jsonBody == null)
            return this;
        this.jsonBody.put(name,value);
        return this;
    }
    public CobinhoodHttpRequest asJsonBody(){
        this.body(this.jsonBody);
        return this;
    }

    public CobinhoodHttpRequest withApiKey(String key){
        return (CobinhoodHttpRequest) super.header("authorization",key);
    }
    public CobinhoodHttpRequest withNonceProvided(){
        Long nonce = System.currentTimeMillis() * 1000000;
        return (CobinhoodHttpRequest) super.header("nonce", nonce.toString());
    }

    public <T extends GenericResponse> T build(Class<T> clazz) {
        try {
            T instance = clazz.newInstance();
            HttpResponse<JsonNode> jsonNodeHttpResponse = this.asJson();
            JsonNode body = jsonNodeHttpResponse.getBody();
            instance.fromJsonNode(body);
            return instance;
        }
        catch (UnirestException | IllegalAccessException | InstantiationException e){
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
