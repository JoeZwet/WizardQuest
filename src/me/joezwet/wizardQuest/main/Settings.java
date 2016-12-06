package me.joezwet.wizardQuest.main;

import org.json.JSONObject;

public class Settings {
	
	private String settings = new String(getClass().getResourceAsStream("/Config/config.json").toString());
	
	JSONObject json = new JSONObject(settings);
	
	public String get(String key) {
		return json.get(key).toString();
	}
	
	public void set(String key, String value) {
		json.put(key, value);
	}
	
	public void remove(String key) {
		json.remove(key);
	}
}
