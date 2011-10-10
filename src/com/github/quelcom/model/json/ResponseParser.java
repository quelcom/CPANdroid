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
        // TODO
        return true;
    }

    final public static JSONArray prepareData(String result) {
        JSONArray jsonA = null;
        try {
            JSONObject json = new JSONObject(result);
            json = new JSONObject(json.optString("hits"));
            jsonA = json.optJSONArray("hits");
            Log.v(TAG, jsonA.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonA;
    }

    final public static ArrayList<AuthorSearch> getAuthors(String result) {
        ArrayList<AuthorSearch> myArray = new ArrayList<AuthorSearch>();
        JSONArray myJson = prepareData(result);
        Gson gson = new Gson();
        int len = myJson.length();
        for ( int i = 0; i < len; i++ ) {
            AuthorSearch myAuth = new AuthorSearch();
            try {
                JSONObject part = new JSONObject(myJson.get(i).toString());
                myJson.put(part.optJSONObject("_source").toString());
                myAuth = gson.fromJson(part.optJSONObject("_source").toString(), AuthorSearch.class);
                myArray.add(myAuth);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        gson = null;
        return myArray;
    }

    final public static ArrayList<ReleaseSearch> getReleases(String result) {
        ArrayList<ReleaseSearch> myArray = new ArrayList<ReleaseSearch>();
        JSONArray myJson = prepareData(result);
        Gson gson = new Gson();
        int len = myJson.length();
        for ( int i = 0; i < len; i++ ) {
            ReleaseSearch myAuth = new ReleaseSearch();
            try {
                JSONObject part = new JSONObject(myJson.get(i).toString());
                myJson.put(part.optJSONObject("_source").toString());
                myAuth = gson.fromJson(part.optJSONObject("_source").toString(), ReleaseSearch.class);
                myArray.add(myAuth);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        gson = null;
        return myArray;
    }
}
