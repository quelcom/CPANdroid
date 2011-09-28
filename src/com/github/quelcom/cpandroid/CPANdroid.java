package com.github.quelcom.cpandroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.quelcom.model.json.AuthorSearch;
import com.github.quelcom.net.Get;
import com.google.gson.Gson;

public class CPANdroid extends Activity {
    public static final String TAG = CPANdroid.class.getSimpleName();
    
    // Dialog box to show when fetching data from the server
    public static ProgressDialog dialog = null;
    Gson gson = null;
    TextView text;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        text = (TextView) findViewById(R.id.text);
        dialog = ProgressDialog.show(CPANdroid.this, "", "Searching...");
        RequesterTask task = new RequesterTask();
        task.execute("/author/_search?q=QUE*");
        //task.execute("/author/QUELCOM");
    }

    public class RequesterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Get get = new Get();
            Log.e(TAG, "foo");
            return get.fetch(params[0]);
        }

        protected void onPostExecute(String result) {
            CPANdroid.dialog.dismiss();
            if ( validateData(result) ) {
                Log.d(TAG, result);
                ArrayList<AuthorSearch> allAuths = new ArrayList<AuthorSearch>();
                allAuths = prepareData(result);
                for ( AuthorSearch as : allAuths ) {
                    text.setText(text.getText() + "\n" + as.getName());
                }
            } else {
                text.setText("validate data has failed");
            }
        }
    }
    
    private Boolean validateData(String result) {
        return true;
    }

    private ArrayList<AuthorSearch> prepareData(String result) {
        JSONObject json;
        try {
            gson = new Gson();
            ArrayList<AuthorSearch> authorArray = new ArrayList<AuthorSearch>();
            json = new JSONObject(result);
            json = new JSONObject(json.optString("hits"));
            JSONArray jsonA = json.optJSONArray("hits");
            Log.e(TAG, jsonA.toString());
            JSONArray nou = new JSONArray();
            int nouLength = jsonA.length();
            for ( int i = 0; i < nouLength; i++) {
                AuthorSearch myAuth = new AuthorSearch();
                JSONObject part =  new JSONObject(jsonA.get(i).toString());
                nou.put(part.optJSONObject("_source").toString());
                myAuth = gson.fromJson(part.optJSONObject("_source").toString(), AuthorSearch.class);
                authorArray.add(myAuth);
            }
            return authorArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}