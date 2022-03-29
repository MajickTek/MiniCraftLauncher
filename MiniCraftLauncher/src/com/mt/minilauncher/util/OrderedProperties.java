package com.mt.minilauncher.util;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

public class OrderedProperties extends Properties{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Object, Object> linkMap = new LinkedHashMap<>();
	
	@Override
	public synchronized Object put(Object key, Object value) {
		return linkMap.put(key, value);
	}
	
	
	@Override
	public boolean contains(Object value) {
		return linkMap.containsValue(value);
	}
	
	@Override
	public boolean containsKey(Object key) {
		return linkMap.containsKey(key);
	}
	
	@Override
	public boolean containsValue(Object value) {
		return linkMap.containsValue(value);
	}
	
	@Override
	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return linkMap.entrySet();
	}
	
	@Override
	public synchronized void clear() {
		linkMap.clear();
	}


}
