package com.github.quelcom.model.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.github.quelcom.cpandroid.CPANdroid;
import com.google.gson.Gson;

public final class ResponseParser {
    public static final String TAG = CPANdroid.class.getSimpleName();

    final public static Boolean validateData(String result) {
        return true;
    }
    
    final public static ArrayList<AuthorSearch> prepareData(String result) {
        JSONObject json = null;
        Gson gson = new Gson();
        ArrayList<AuthorSearch> authorArray = new ArrayList<AuthorSearch>();
        try {
            json = new JSONObject(result);
            json = new JSONObject(json.optString("hits"));
            JSONArray jsonA = json.optJSONArray("hits");
            Log.v(TAG, jsonA.toString());
            JSONArray nou = new JSONArray();
            int nouLength = jsonA.length();
            for ( int i = 0; i < nouLength; i++) {
                AuthorSearch myAuth = new AuthorSearch();
                JSONObject part =  new JSONObject(jsonA.get(i).toString());
                nou.put(part.optJSONObject("_source").toString());
                myAuth = gson.fromJson(part.optJSONObject("_source").toString(), AuthorSearch.class);
                authorArray.add(myAuth);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            json = null;
            gson = null;
        }
        return authorArray;
    }
}
