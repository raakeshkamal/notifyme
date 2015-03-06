package com.example.raakesh.notify_me;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


public class Approval extends ActionBarActivity {
    Notification note;
    TextView title;
    TextView content;
    TextView author;
    Button button;
    String  APPLICATION_ID="b0tUyz02ZJefQhhCFBD4Fo8yvlz2iOvMO0qQilPJ";
    String CLIENT_KEY="1Erpwgd8zTsw2j3X03ZHdwTWAnobPnnG0E8oOjRa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
       // supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=this.getIntent();
        title=(TextView)findViewById(R.id.title);
        content=(TextView)findViewById(R.id.content);
        author=(TextView)findViewById(R.id.author);
        button=(Button)findViewById(R.id.approve);
        if (intent.getExtras() != null) {
            if(intent.getStringExtra("noteId")!=null){
                note = new Notification(intent.getStringExtra("noteId"), intent.getStringExtra("noteAuthor"), intent.getStringExtra("noteTitle"),intent.getStringExtra("noteContent"),intent.getStringExtra("type"),intent.getStringExtra("status"));

                //Log.i("edit","save");
                title.setText(note.getTitle());
                content.setText(note.getContent());
            author.setText(note.getAuthor());}
            // Log.i("edit",note.getTitle());
            else
                note=null;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update post

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");

                // Retrieve the object by id
                query.getInBackground(note.getId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject post, com.parse.ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data.
                            //post.put("title", );
                            //post.put("content", postContent);
                            post.put("status","approved");
                            setSupportProgressBarIndeterminateVisibility(true);
                            post.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(com.parse.ParseException e) {
                                    setSupportProgressBarIndeterminateVisibility(false);
                                    if (e == null) {
                                        // Saved successfully.
                                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // The save failed.
                                        Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                                        Log.d(getClass().getSimpleName(), "User update error: " + e);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_approval, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
