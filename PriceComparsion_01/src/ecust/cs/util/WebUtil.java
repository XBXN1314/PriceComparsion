package ecust.cs.util;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class WebUtil {
	public static final JSONObject getJson(int total, Map<String, Object>[] rows){
		Map<String, Object> map = new LinkedHashMap<String, Object>(2);
		map.put("total", total);
		map.put("rows", rows);
		
		JSONObject json = JSONObject.fromObject(map);
		
		return json;
	}
}
