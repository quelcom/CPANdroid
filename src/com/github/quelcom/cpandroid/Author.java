package com.github.quelcom.cpandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.quelcom.model.json.AuthorSearch;

public class Author extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author);
        
        TextView author = new TextView(this);
        author = (TextView) findViewById(R.id.author);
        AuthorSearch sAuthor = (AuthorSearch) getIntent().getSerializableExtra("author");
        author.setText(sAuthor.getPauseId());
    }
}
