package com.weiyi.reader.view;


import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

	SharedPreferences sp;
	SharedPreferences.Editor editor;
	Context context;

	public PreferencesHelper(Context c, String name) {
		context = c;
		sp = context.getSharedPreferences(name, 0);
		editor = sp.edit();
	}

	public void setValue(String key, String value) {
		editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getValue(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	public String getValue(String key) {
		return sp.getString(key, null);
	}

	public void remove(String name) {
		editor.remove(name);

	}

}
