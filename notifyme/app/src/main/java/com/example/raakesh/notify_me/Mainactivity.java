package com.example.raakesh.notify_me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.Parse;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by raakesh on 16/1/15.
 */
public class Mainactivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public static final ArrayList<String> items = new ArrayList<String>();
    public static ArrayAdapter<String> adapter = null;
    ListView listView;
    String  APPLICATION_ID="b0tUyz02ZJefQhhCFBD4Fo8yvlz2iOvMO0qQilPJ";
    String CLIENT_KEY="1Erpwgd8zTsw2j3X03ZHdwTWAnobPnnG0E8oOjRa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items.clear();
        setContentView(R.layout.activity_main);
        LinearLayout back=(LinearLayout)findViewById(R.id.back);
        back.setBackgroundResource(R.drawable.back12);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }
        items.add("My Hostel");
        items.add("My Department");
        items.add("Other Departments");
        items.add("Buy and Sell");
        items.add("Lost and Found");
        items.add("My Posts");
        Log.i("fucker",currentUser.getString("type"));
        if(currentUser.getString("type").equals("admin"))
        items.add("Post Requests");
        listView = (ListView)findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
    private void loadLoginView() {
        Intent intent = new Intent(this, Loginactivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String currentItem = listView.getItemAtPosition(position).toString();
        if(currentItem.equals("My Hostel")){
            Intent intent=new Intent(this,Myhostel.class);
            Log.i("fuck","shit");
            startActivity(intent);
        }
        else if(currentItem.equals("My Department")){
            Intent intent=new Intent(this,Mydepartmentactivity.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Other Departments")){
            Intent intent=new Intent(this,Otherdept.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Buy and Sell")){
            Intent intent=new Intent(this,Buyandsell.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Lost and Found")){
            Intent intent=new Intent(this,Lostandfound.class);
            startActivity(intent);
        }
        else if(currentItem.equals("My Posts")){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Post Requests")){
            Intent intent=new Intent(this,Admin.class);
            startActivity(intent);
        }

        else{

        }
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
                //refreshPostList();
                break;
            }

            case R.id.action_new: {
                Intent intent = new Intent(this, Editnotification.class);
                intent.putExtra("type",ParseUser.getCurrentUser().getString("hostel"));
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
            case R.id.action_profile:{
                Intent intent=new Intent(this,ccw.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
