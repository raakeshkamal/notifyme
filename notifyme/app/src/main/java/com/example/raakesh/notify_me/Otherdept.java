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
public class Otherdept  extends ActionBarActivity implements AdapterView.OnItemClickListener{
    public static final ArrayList<String> items = new ArrayList<String>();
    private String department;
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
        //department="electrical";
        if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("ee"))
            department="Electrical Engineering";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("cs"))
            department="Computer Science and Engineering";
        else if (ParseUser.getCurrentUser().getUsername().substring(0,2).equals("me"))
            department="Mechanical Engineering";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("ep"))
            department="Physics";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("ce"))
            department="Civil Engineering";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("be")||ParseUser.getCurrentUser().getUsername().substring(0,2).equals("bs"))
            department="Biotechnology";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("na"))
            department="Ocean Engineering";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("am"))
            department="Applied Mechanics";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("ch"))
            department="Chemical Engineering";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("ae"))
            department="Aerospace Engineering";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("ed"))
            department="Engineering Design";
        else if(ParseUser.getCurrentUser().getUsername().substring(0,2).equals("mm"))
            department="Metallurgical and Materials Engineering";
        ArrayList<String> depts=new ArrayList<>();
        depts.add("Aerospace Engineering");
        depts.add("Applied Mechanics");
        depts.add("Biotechnology");
        depts.add("Chemical Engineering");
        depts.add("Civil Engineering");
        depts.add("Computer Science and Engineering");
        depts.add("Electrical Engineering");
        depts.add("Engineering Design");
        depts.add("Mechanical Engineering");
        depts.add("Metallurgical and Materials Engineering");
        depts.add("Ocean Engineering");
        depts.add("Physics");
        for(String depart:depts ) {
            if(!depart.equals(department))
            items.add(depart);
        }
      /*  items.add("My Hostel");
        items.add("My Department");
        items.add("Other Departments");
        items.add("Buy and Sell");
        items.add("Lost and Found");
        items.add("My Posts");*/
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
        if(currentItem.equals("Aerospace Engineering")){
            Intent intent=new Intent(this,Aero.class);
            Log.i("fuck","shit");
            startActivity(intent);
        }
        else if(currentItem.equals("Applied Mechanics")){
            Intent intent=new Intent(this,AppMech.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Biotechnology")){
            Intent intent=new Intent(this,BioTech.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Chemical Engineering")){
            Intent intent=new Intent(this,Chem.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Civil Engineering")){
            Intent intent=new Intent(this,Civil.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Computer Science and Engineering")){
            Intent intent=new Intent(this,Comp.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Electrical Engineering")){
            Intent intent=new Intent(this,Elec.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Engineering Design")){
            Intent intent=new Intent(this,Ed.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Mechanical Engineering")){
            Intent intent=new Intent(this,Mech.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Metallurgical and Materials Engineering")){
            Intent intent=new Intent(this,Meta.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Ocean Engineering")) {
            Intent intent=new Intent(this,Ocean.class);
            startActivity(intent);
        }
        else if(currentItem.equals("Physics")) {
            Intent intent=new Intent(this,Physics.class);
            startActivity(intent);
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



