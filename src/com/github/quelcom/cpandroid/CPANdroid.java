package com.github.quelcom.cpandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.quelcom.model.json.Author;
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
        task.execute("/author/_search?q=QUELC*&fields=name&size=100");
        //task.execute("/author/QUELCOM");
    }

    public class RequesterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String response = null;
            for (String url : params ) {
                Get get = new Get();
                return get.fetch(url);
            }
            return response;
        }

        protected void onPostExecute(String result) {
            CPANdroid.dialog.dismiss();
            gson = new Gson();
            Author auth = new Author();
            Log.d(TAG, result);
            auth = gson.fromJson(result, Author.class);
            text.setText(auth.getName());
        }

    }
}