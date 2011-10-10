package com.github.quelcom.cpandroid;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.quelcom.adapters.AuthorAdapter;
import com.github.quelcom.adapters.ReleaseAdapter;
import com.github.quelcom.model.json.AuthorSearch;
import com.github.quelcom.model.json.ReleaseSearch;
import com.github.quelcom.model.json.ResponseParser;
import com.github.quelcom.net.Get;
import com.google.gson.Gson;

public class CPANdroid extends ListActivity {
    public static final String TAG = CPANdroid.class.getSimpleName();
    
    // Default
    public static String select = null;

    // Dialog box to show when fetching data from the server
    public static ProgressDialog dialog = null;
    Gson gson = null;
    EditText queryTxt;
    Spinner s1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button searchBtn = (Button) findViewById(R.id.searchBtn);

        queryTxt = (EditText) findViewById(R.id.queryTxt);
        s1 = (Spinner) findViewById(R.id.spinner);

        final String[] features = getResources().getStringArray(R.array.features);
        select = features[0];

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_item, features);

        s1.setAdapter(adapter);
        s1.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = s1.getSelectedItemPosition();
                select = features[index];
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        
        searchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                executeRequest(select);
            }
        });
    }

    public void executeRequest(String type) {
        dialog = ProgressDialog.show(CPANdroid.this, "", "Searching...", true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(queryTxt.getWindowToken(), 0);

        RequesterTask task = new RequesterTask(type);
        String query = queryTxt.getText().toString().toUpperCase();
        Log.d(TAG, "Requested: " + query);

        if ( type.equals("author") ) {
            task.execute("/author/_search?q=" + query + "*");
        } else if ( type.equals("release") ) {
            task.execute("/release/_search?q=" + query);
        } else {
            Toast.makeText(getBaseContext(),  "Unknown type. Nothing to request", Toast.LENGTH_SHORT).show();
        }
    }

    public class RequesterTask extends AsyncTask<String, Void, String> {
        String type;
        public RequesterTask(String string) {
            type = string;
        }

        @Override
        protected String doInBackground(String... params) {
            Get get = new Get();
            return get.fetch(params[0]);
        }

        protected void onPostExecute(String result) {
            CPANdroid.dialog.dismiss();

            if ( ResponseParser.validateData(result) ) {
                Log.d(TAG, result);
                if ( type.equals("author") ) {
                    ArrayList<AuthorSearch> allAuths = new ArrayList<AuthorSearch>();
                    allAuths = ResponseParser.getAuthors(result);
                    AuthorAdapter aa = new AuthorAdapter(CPANdroid.this, allAuths);
                    setListAdapter(aa);
                } else if ( type.equals("release") ) {
                    ArrayList<ReleaseSearch> allAuths = new ArrayList<ReleaseSearch>();
                    allAuths = ResponseParser.getReleases(result);
                    ReleaseAdapter aa = new ReleaseAdapter(CPANdroid.this, allAuths);
                    setListAdapter(aa);
                }
            } else {
                Toast.makeText(getBaseContext(),  "Unknown type", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void onListItemClick( ListView l, View v, int position, long id ) {
        if ( select.equals("author") ) {
            AuthorSearch myAuth = new AuthorSearch();
            myAuth = (AuthorSearch) l.getItemAtPosition(position);

            if ( myAuth != null ) {
                Intent authorActivity = new Intent(getBaseContext(), Author.class);
                authorActivity.putExtra("author", myAuth);
                startActivity(authorActivity);
            } else {
                Toast.makeText(getBaseContext(),  "Ops! Something wrong", Toast.LENGTH_SHORT).show();
            }
        } else {
            // TODO
            Toast.makeText(getBaseContext(),  "Not yet implemented", Toast.LENGTH_SHORT).show();
        }
    }
}