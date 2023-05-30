package com.gamboSupermarket.application.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HelpersUtil {
	public static Object getValueForKeyPath(Map map, List<String> keys) {
	    if (keys.size() == 1) {
	      return map.get(keys.get(0));
	    } else {
	      return getValueForKeyPath((Map) map.get(keys.get(0)), keys.subList(1, keys.size()));
	    }
	  }
	public static Map<String, Object> getMapByJson(String pinfo)
			throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		Map<String, Object> map = new HashMap<String, Object>();
		map = mapper.readValue(pinfo, new TypeReference<Map<String, Object>>() {
		});
		return map;
	}
}
