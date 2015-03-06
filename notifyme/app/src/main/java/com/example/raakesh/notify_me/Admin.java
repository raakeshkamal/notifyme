package com.example.raakesh.notify_me;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class Admin extends ActionBarActivity implements AdapterView.OnItemClickListener{
    private ArrayList<Notification> posts;
    ListView listview;
    // public static ArrayAdapter<Notification> adapter = null;
    private NotificationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout back=(LinearLayout)findViewById(R.id.back);
        back.setBackgroundResource(R.drawable.back8);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }
        listview=(ListView)findViewById(R.id.list);
        posts = new ArrayList<>();
        // ArrayAdapter<Notification> adapter = new ArrayAdapter<Notification>(this,R.layout.list_item_layout, posts);
        adapter=new NotificationAdapter(this,posts,R.drawable.post);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        refreshPostList();
    }
    private void loadLoginView() {
        Intent intent = new Intent(this, Loginactivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
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
    private void refreshPostList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("status", "active");

        setSupportProgressBarIndeterminateVisibility(true);

        query.findInBackground(new FindCallback<ParseObject>() {

            @SuppressWarnings("unchecked")
            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                setSupportProgressBarIndeterminateVisibility(false);
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    posts.clear();
                    for (ParseObject post : postList) {
                            Notification note = new Notification(post.getObjectId(), post
                                    .getString("postman"), post.getString("title"), post.getString("content"), post.getString("type"), post.getString("status"));
                            posts.add(note);
                                            }
                    listview=(ListView)findViewById(R.id.list);
                    adapter=new NotificationAdapter(Admin.this,posts,R.drawable.post);
                    listview.setAdapter(adapter);
                } else {

                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }

            }

        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Notification note = posts.get(position);
        Intent intent = new Intent(this, Approval.class);
        intent.putExtra("noteId", note.getId());
        intent.putExtra("noteAuthor",note.getAuthor());
        intent.putExtra("noteTitle", note.getTitle());
        intent.putExtra("noteContent", note.getContent());
        intent.putExtra("type",note.getType());
        intent.putExtra("status",note.getStatus());
        intent.putExtra("image",R.drawable.ic_launcher);
        startActivity(intent);
    }
}
