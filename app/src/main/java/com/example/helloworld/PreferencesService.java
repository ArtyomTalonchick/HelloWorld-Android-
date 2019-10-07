package com.example.helloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesService {
    private SharedPreferences sharedPreferences;
    private final String PREFERENСES = "preferences";

    public PreferencesService(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENСES, Context.MODE_PRIVATE);
    }

    public void putBoolean(String name, Boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public boolean getBoolean(String name, Boolean defaultValue) {
        return sharedPreferences.getBoolean(name, defaultValue);
    }
}
