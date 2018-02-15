package com.cobinhood;

import com.cobinhood.responses.GenericResponse;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CobinhoodHttpRequest extends HttpRequestWithBody {

    private static Logger log;
    static {
        InputStream stream = CobinhoodHttpRequest.class.getClassLoader().
                getResourceAsStream("cobinhood.logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            log = Logger.getLogger(CobinhoodHttpRequest.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            if(this.jsonBody.length() > 0)
                log.info(this.getHttpMethod() + " " + this.getUrl() + " " + this.jsonBody);
            else
                log.info(this.getHttpMethod() + " " + this.getUrl());

            HttpResponse<JsonNode> jsonNodeHttpResponse = this.asJson();
            JsonNode body = jsonNodeHttpResponse.getBody();

            T instance = clazz.newInstance();
            instance.fromJsonNode(body);
            log.info(instance.toString());
            return instance;
        }
        catch (UnirestException | IllegalAccessException | InstantiationException e){
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
