package com.example.raakesh.notify_me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raakesh on 15/1/15.
 */
public class Comp extends ActionBarActivity implements AdapterView.OnItemClickListener{
    private ArrayList<Notification> posts;
    private String department="Comp";
    ListView listview;
    NotificationAdapter adapter;
    String  APPLICATION_ID="b0tUyz02ZJefQhhCFBD4Fo8yvlz2iOvMO0qQilPJ";
    String CLIENT_KEY="1Erpwgd8zTsw2j3X03ZHdwTWAnobPnnG0E8oOjRa";

    //public static ArrayAdapter<Notification> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        LinearLayout back=(LinearLayout)findViewById(R.id.back);
        back.setBackgroundResource(R.drawable.back4);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }
        department="electrical";
        if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("ee"))
            department="electrical";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("cs"))
            department="computer";
        else if (ParseUser.getCurrentUser().getUsername().substring(0,2).equals("me"))
            department="mechanical";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("ep"))
            department="engg physics";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("ce"))
            department="civil";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("be"))
            department="biotech";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("na"))
            department="ocean";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("am"))
            department="app mechanics";

        posts = new ArrayList<>();
        listview=(ListView)findViewById(R.id.list);
        // ArrayAdapter<Notification> adapter = new ArrayAdapter<Notification>(this,R.layout.list_item_layout, posts);
        adapter=new NotificationAdapter(this,posts,R.drawable.dept);
        listview.setAdapter(adapter);
        refreshPostList(department);
        // listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_refresh: {
                refreshPostList(department);
                break;
            }

            case R.id.action_new: {
                Intent intent = new Intent(this, Editnotification.class);
                intent.putExtra("ParentClassSource", "com.package.example.YourParentActivity");
                intent.putExtra("type",department);
                startActivity(intent);
                break;
            }

            case R.id.action_logout:
                ParseUser.logOut();
                loadLoginView();
                break;

            case R.id.action_settings: {
                // Do something when user selects Settings from Action Bar overlay
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Notification note = posts.get(position);
        Intent intent = new Intent(this, Editnotification.class);
        intent.putExtra("noteId", note.getId());
        intent.putExtra("noteAuthor",note.getAuthor());
        intent.putExtra("noteTitle", note.getTitle());
        intent.putExtra("noteContent", note.getContent());
        intent.putExtra("type",note.getType());
        intent.putExtra("image",R.drawable.ic_launcher);
        startActivity(intent);

    }
    private void refreshPostList(String spec) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("type",spec);

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
                        if(post.getString("status").equals("approved")){
                            Notification note = new Notification(post.getObjectId(), post
                                    .getString("postman"), post.getString("title"),post.getString("content"),post.getString("type"),post.getString("status"));
                            posts.add(note);}
                    }
                    listview=(ListView)findViewById(R.id.list);
                    adapter=new NotificationAdapter(Comp.this,posts,R.drawable.dept);
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
        if(ParseUser.getCurrentUser().getUsername().equals(note.getAuthor())||ParseUser.getCurrentUser().getString("type").equals("admin")) {
            Intent intent = new Intent(this, Editnotification.class);
            intent.putExtra("noteId", note.getId());
            intent.putExtra("noteAuthor",note.getAuthor());
            intent.putExtra("noteTitle", note.getTitle());
            intent.putExtra("noteContent", note.getContent());
            intent.putExtra("type",note.getType());
            intent.putExtra("status",note.getStatus());
            intent.putExtra("image",R.drawable.ic_launcher);
            startActivity(intent);
        }
        else
        {
            setContentView(R.layout.view_note);
            TextView title=(TextView)findViewById(R.id.title);
            TextView content=(TextView)findViewById(R.id.content);
            TextView author=(TextView)findViewById(R.id.author);
            title.setText(note.getTitle());
            content.setText(note.getContent());
            author.setText(note.getAuthor());
        }}
}
