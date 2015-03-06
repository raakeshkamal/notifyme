package com.example.raakesh.notify_me;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;

/**
 * Created by raakesh on 15/1/15.
 */
public class Editnotification extends ActionBarActivity {
    private Notification note;
    private EditText titleEditText;
    private EditText contentEditText;
    private String postTitle;
    private String postContent;
    private Button saveNoteButton;
    private Button deleteNoteButton;
    private Bitmap bitmap;
    private Intent intent;
    String APPLICATION_ID = "b0tUyz02ZJefQhhCFBD4Fo8yvlz2iOvMO0qQilPJ";
    String CLIENT_KEY = "1Erpwgd8zTsw2j3X03ZHdwTWAnobPnnG0E8oOjRa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_edit_note);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
        bitmap = ((BitmapDrawable) drawable).getBitmap();
        intent = this.getIntent();

        titleEditText = (EditText) findViewById(R.id.noteTitle);
        contentEditText = (EditText) findViewById(R.id.noteContent);

        if (intent.getExtras() != null) {
            if (intent.getStringExtra("noteId") != null) {
                note = new Notification(intent.getStringExtra("noteId"), intent.getStringExtra("noteAuthor"), intent.getStringExtra("noteTitle"), intent.getStringExtra("noteContent"), intent.getStringExtra("type"), intent.getStringExtra("status"));

                //Log.i("edit","save");
                titleEditText.setText(note.getTitle());
                contentEditText.setText(note.getContent());
            }
            // Log.i("edit",note.getTitle());
            else
                note = null;
        }
        deleteNoteButton=(Button) findViewById(R.id.del);
        saveNoteButton = (Button) findViewById(R.id.saveNote);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
        deleteNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void deleteNote(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");

        // Retrieve the object by id
        query.getInBackground(note.getId(), new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject post, com.parse.ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data.
                /*    post.put("title", postTitle);
                    post.put("content", postContent);*/
                    setSupportProgressBarIndeterminateVisibility(true);
                    post.deleteInBackground();
                    Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();}
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                    Log.d(getClass().getSimpleName(), "User delete error: " + e);
                }
            }});
    }


    private void saveNote() {

        postTitle = titleEditText.getText().toString();
        postContent = contentEditText.getText().toString();

        postTitle = postTitle.trim();
        postContent = postContent.trim();

        // If user doesn't enter a title or content, do nothing
        // If user enters title, but no content, save
        // If user enters content with no title, give warning
        // If user enters both title and content, save

        if (!postTitle.isEmpty()) {

            // Check if post is being created or edited


            if (note == null) {
                // create new post
                //res = getResources();
                Log.i("edit", "save");
                final ParseObject post = new ParseObject("Post");

                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                //byte[] data = stream.toByteArray();

                //ParseFile file = new ParseFile("resume.txt", data);
                //file.saveInBackground();


                //post.put("mediatype", data);
                post.put("title", postTitle);
                post.put("content", postContent);
                post.put("author", ParseUser.getCurrentUser());
                Log.i("asshole", ParseUser.getCurrentUser().getUsername());
                post.put("postman", ParseUser.getCurrentUser().getUsername());
                if (ParseUser.getCurrentUser().getString("type").equals("user"))
                    post.put("status", "active");
                else
                    post.put("status", "approved");
                Log.i("o", intent.getStringExtra("type"));
                if (!intent.getStringExtra("type").isEmpty()) {

                    post.put("type", intent.getStringExtra("type"));
                }
                setSupportProgressBarIndeterminateVisibility(true);
                post.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(com.parse.ParseException e) {

                        setSupportProgressBarIndeterminateVisibility(false);
                        if (e == null) {
                            // Saved successfully.

                            note = new Notification(post.getObjectId(), ParseUser.getCurrentUser().getUsername(), postTitle, postContent, intent.getStringExtra("type"), intent.getStringExtra("status"));
                            Log.i("ahole", ParseUser.getCurrentUser().getUsername());
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        } else {
                            // The save failed.
                            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                            Log.d(getClass().getSimpleName(), "User update error: " + e);
                        }
                    }
                });

            } else {
                // update post

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");

                // Retrieve the object by id
                query.getInBackground(note.getId(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject post, com.parse.ParseException e) {
                        if (e == null) {
                            // Now let's update it with some new data.
                            post.put("title", postTitle);
                            post.put("content", postContent);
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
                //NavUtils.navigateUpFromSameTask(this);

            }
        }
            else if (postTitle.isEmpty() && !postContent.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Editnotification.this);
                builder.setMessage(R.string.edit_error_message)
                        .setTitle(R.string.edit_error_title)
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

