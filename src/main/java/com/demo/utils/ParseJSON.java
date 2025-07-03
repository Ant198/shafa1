package com.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;

public class ParseJSON {

    private static JSONArray parseJSON(String jsonLocation) throws Exception {
        JSONParser jsonParser = new JSONParser();
        return (JSONArray) jsonParser.parse(new FileReader(jsonLocation));
    }

    private static JSONObject getData(String name, String jsonLocation) throws Exception {
        JSONArray array = parseJSON(jsonLocation);
        for (Object jsonObj : array) {
            JSONObject object = (JSONObject) jsonObj;
            Object key = object.get(name);
            if (key != null) return (JSONObject) key;
        }
        return null;
    }

    public static HashMap<String, Object> convertJSONObjectToHashMap(String name, String jsonLocation) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(ParseJSON.getData(name, jsonLocation).toString(), HashMap.class);
    }
}
