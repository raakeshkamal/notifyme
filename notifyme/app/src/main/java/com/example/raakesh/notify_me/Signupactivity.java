package com.example.raakesh.notify_me;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by raakesh on 15/1/15.
 */
public class Signupactivity extends ActionBarActivity {
    EditText rollno;
    EditText hostel;
    EditText email;
    Button signUpButton;
    String  APPLICATION_ID="b0tUyz02ZJefQhhCFBD4Fo8yvlz2iOvMO0qQilPJ";
    String CLIENT_KEY="1Erpwgd8zTsw2j3X03ZHdwTWAnobPnnG0E8oOjRa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sign_up);
        LinearLayout back=(LinearLayout)findViewById(R.id.back);
        back.setBackgroundResource(R.drawable.back8);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        rollno = (EditText)findViewById(R.id.usernameField);
        hostel=(EditText)findViewById(R.id.passwordField);
        email=(EditText)findViewById(R.id.emailField);
        signUpButton = (Button)findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rollno.getText().toString().length() == 8){
                    String roll = rollno.getText().toString();
                String hos = hostel.getText().toString();
                String mail = email.getText().toString();
                roll = roll.trim();
                hos = hos.trim();
                mail = mail.trim();
                if (roll.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signupactivity.this);
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    setSupportProgressBarIndeterminateVisibility(true);

                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(roll);
                    newUser.setPassword(roll);
                    newUser.setEmail(mail);
                    Log.i("omg", roll);
                    newUser.put("hostel", hos);
                    newUser.put("type","user");
                  /*  if(roll.equals("ee14b001"))
                    newUser.put("type","admin");
                    else
                    newUser.put("type","user");*/
                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            setSupportProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                // Success!
                                Intent intent = new Intent(Signupactivity.this, Mainactivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Signupactivity.this);
                                builder.setMessage(e.getMessage())
                                        .setTitle(R.string.signup_error_title)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                    // Intent next=new Intent(Signupactivity.this,Mainactivity.this);
                }
            }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signupactivity.this);
                    builder.setMessage("Invalid password")
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
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

}
