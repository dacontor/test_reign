package com.daniel.test_reign.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.daniel.test_reign.core.models.HitsObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PreferenceUtils {

    private static final String TAG_DATA = "data_hits";
    private static String DATA_HITS = "list_hits";

    public static List<HitsObject> getListHits(Context actividad) {
        try {

            SharedPreferences prefs = actividad.getSharedPreferences(TAG_DATA, Context.MODE_PRIVATE);

            Gson gson = new Gson();
            String json = prefs.getString(DATA_HITS, "");

            Type type = new TypeToken<List<HitsObject>>() {
            }.getType();
            return gson.fromJson(json, type);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void setDataHits(List<HitsObject> listHits, Context actividad) {

        SharedPreferences prefs = actividad.getSharedPreferences(TAG_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listHits);
        prefsEditor.putString(DATA_HITS, json);
        prefsEditor.apply();

    }

    public static void deleteDataHits(Context mContext) {

        SharedPreferences settings = mContext.getSharedPreferences(TAG_DATA, Context.MODE_PRIVATE);
        settings.edit().remove(DATA_HITS).apply();

    }


}
