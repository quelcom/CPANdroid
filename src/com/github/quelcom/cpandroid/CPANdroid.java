package com.github.quelcom.cpandroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.quelcom.adapters.AuthorAdapter;
import com.github.quelcom.model.json.AuthorSearch;
import com.github.quelcom.net.Get;
import com.google.gson.Gson;

public class CPANdroid extends ListActivity {
    public static final String TAG = CPANdroid.class.getSimpleName();
    
    // Dialog box to show when fetching data from the server
    public static ProgressDialog dialog = null;
    Gson gson = null;
    TextView text;
    EditText queryTxt;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        queryTxt = (EditText) findViewById(R.id.queryTxt);
        
        searchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(CPANdroid.this, "", "Searching...", true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(queryTxt.getWindowToken(), 0);
                text = (TextView) findViewById(R.id.text);

                RequesterTask task = new RequesterTask();
                String query = queryTxt.getText().toString().toUpperCase();
                Log.d(TAG, "Requested: " + query);
                task.execute("/author/_search?q=" + query + "*");
            }
        });
    }

    public class RequesterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Get get = new Get();
            return get.fetch(params[0]);
        }

        protected void onPostExecute(String result) {
            CPANdroid.dialog.dismiss();

            if ( validateData(result) ) {
                Log.d(TAG, result);
                ArrayList<AuthorSearch> allAuths = new ArrayList<AuthorSearch>();
                allAuths = prepareData(result);
                AuthorAdapter aa = new AuthorAdapter(CPANdroid.this, allAuths);
                text.setText("");
                setListAdapter(aa);

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
            return authorArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}