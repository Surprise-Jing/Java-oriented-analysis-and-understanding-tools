package com.nju.boot.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;
import java.util.List;

/**
 * 把xml格式的数据转换为可做显示处理的json对象
 */
public class JsonDataModifier {
    JSONObject src,result = null;

    public JsonDataModifier(String xml) {
        try {
            src = XML.toJSONObject(xml);
        } catch (JSONException e) {
            throw new RuntimeException("wrong xml format",e);
        }
    }
    public void modify(){
        JSONArray jsonArray = src.names();
        try {
            String root = src.names().get(0).toString();
            Object rootObj = src.getJSONObject(root);
            result = reconstruct2JsonObject(root,rootObj);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    private JSONObject reconstruct2JsonObject(String key,JSONObject object) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("label",key);
        if(object == null) return result;
        List<JSONObject> childrenObjects = new ArrayList<>();
        JSONArray objectNames =  object.names();
        for (int i = 0; i<objectNames.length();i++){
            Object obj = object.get(objectNames.getString(i));
            JSONObject child = reconstruct2JsonObject(objectNames.getString(i),obj);
            if(child!=null)childrenObjects.add(child);
        }
        result.put("children",new JSONArray(childrenObjects));
        return result;
    }
    private JSONObject reconstruct2JsonObject(String key,JSONArray object) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("label",key);
        List<JSONObject> childrenObjects = new ArrayList<>();
        for(int i =0;i< object.length();i++){
            Object obj = object.get(i);
            JSONObject child = reconstruct2JsonObject(String.valueOf(i),obj);
            if(child!=null)childrenObjects.add(child);
        }
        result.put("children",new JSONArray(childrenObjects));
        return result;
    }
    private JSONObject reconstruct2JsonObject(String key,Boolean object) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("label",key);
        List<JSONObject> childrenObjects = new ArrayList<>();
        JSONObject child = new JSONObject();
        child.put("label",String.valueOf(object));
        childrenObjects.add(child);
        result.put("children",new JSONArray(childrenObjects));
        return result;
    }
    private JSONObject reconstruct2JsonObject(String key,Number object) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("label",key);
        List<JSONObject> childrenObjects = new ArrayList<>();
        JSONObject child = new JSONObject();
        child.put("label",String.valueOf(object));
        childrenObjects.add(child);
        result.put("children",new JSONArray(childrenObjects));
        return result;
    }
    private JSONObject reconstruct2JsonObject(String key,String object) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("label",key);
        List<JSONObject> childrenObjects = new ArrayList<>();
        JSONObject child = new JSONObject();
        child.put("label",String.valueOf(object));
        childrenObjects.add(child);
        result.put("children",new JSONArray(childrenObjects));
        return result;
    }
    private JSONObject reconstruct2JsonObject(String key,Object object) throws JSONException {
        if(object instanceof String){
            return  reconstruct2JsonObject(key,(String) object);
        }
        else if (object instanceof Boolean){
            return  reconstruct2JsonObject(key,(Boolean) object);
        }
        else if (object instanceof Number){
            return  reconstruct2JsonObject(key,(Number) object);
        }
        else if (object instanceof JSONArray){
            return  reconstruct2JsonObject(key,(JSONArray) object);
        }
        else if (object instanceof JSONObject){
            return  reconstruct2JsonObject(key,(JSONObject) object);
        }
        return null;
    }



    public JSONObject getResult() {

        return result;
    }

}
