package com.ismyblue.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonConvertUtil {
	
	public static JSONObject getJsonObject(String name, Object value){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(name, value);
		return jsonObject;
	}
	
	public static JSONArray getJsonArray(Object values){		
		JSONArray jsonArray = JSONArray.fromObject(values);
		return jsonArray;
	}
	
	
}
