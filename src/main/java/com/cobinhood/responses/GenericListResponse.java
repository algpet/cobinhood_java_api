package com.cobinhood.responses;

import com.mashape.unirest.http.JsonNode;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
public abstract class GenericListResponse<E> extends GenericResponse implements Iterable<E>{
    private List<E> items = new ArrayList<>();
    protected String listJsonKey;

    @Override
    public void childFieldsFromJsonNode(JsonNode jsonNode) {
        JSONArray jsonArray = jsonNode.getObject().getJSONObject("result").getJSONArray(listJsonKey);
        for (int i = 0 ; i < jsonArray.length(); i++) {
            JSONObject itemJson = jsonArray.getJSONObject(i);
            E item = fromJsonObject(itemJson);
            this.items.add(item);
        }
    }
    protected abstract <E> E fromJsonObject(JSONObject jsonObject);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "number of items=" + items.size() +
                "} " + super.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return this.items.iterator();
    }

}
