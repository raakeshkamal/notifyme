package com.example.raakesh.notify_me;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseUser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class ccw extends ActionBarActivity {
    private String[] prof;
    private String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccw);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }
        else
            text=currentUser.getUsername();

        //prof=getDetails(currentUser.getUsername());

        student_details prof1=new student_details("","","","","12","","12");
        student_details prof2=new student_details("","","","","13","","13");
        student_details prof = (student_details) new profile().doInBackground(new student_details[]{prof1, prof2});
        TextView name = (TextView) findViewById(R.id.name);
        TextView gender = (TextView) findViewById(R.id.gender);
        TextView room = (TextView) findViewById(R.id.room);
        TextView program = (TextView) findViewById(R.id.program);
        TextView sem = (TextView) findViewById(R.id.sem);
        TextView hostel = (TextView) findViewById(R.id.host);
        TextView dept = (TextView) findViewById(R.id.dept);

        name.setText(name.getText() + prof.name);
        program.setText(program.getText() + prof.program);
        gender.setText(gender.getText() + prof.gender);
        room.setText(room.getText() + Integer.toString(prof.room_number));
        sem.setText(sem.getText() + Integer.toString(prof.cur_sem));
        hostel.setText(hostel.getText() + prof.hostel);
        dept.setText(dept.getText() + prof.dept);

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
        getMenuInflater().inflate(R.menu.menu_ccw, menu);
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

    private static String getUrlSource(String str_url) throws IOException {
        URL url = new URL(str_url);
        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
        URLConnection yc = url.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder src_url = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            src_url.append(inputLine);
        in.close();
        return src_url.toString();

    }
    public class student_details {

        public String name;
        public String gender;
        public String program;
        public String dept;
        public int cur_sem;
        public String hostel;
        public int room_number;

        public student_details(String name, String gender, String program, String dept, String cur_sem, String hostel, String room_number) {
            this.name = name;
            this.gender = gender;
            this.program = program;
            this.dept = dept;
            this.cur_sem = Integer.parseInt(cur_sem);
            this.hostel = hostel;
            this.room_number = Integer.parseInt(room_number);
        }

    }


    private student_details getDetails(String text) {
        String src;
        try {
            URL url = new URL("https://ccw.iitm.ac.in/?q=IITMHostels/sinfo/" + text.toUpperCase());
            System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            URLConnection yc = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder src_url = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                src_url.append(inputLine);
            in.close();
           // src = getUrlSource("https://ccw.iitm.ac.in/?q=IITMHostels/sinfo/" + text.toUpperCase());
            src=src_url.toString();
            Document doc = Jsoup.parse(src);
            Element prof_data = doc.getElementById("content").getElementsByClass("section").first().getElementsByTag("table").first();
            String analyse_data = prof_data.text();

            analyse_data = analyse_data.replace(" Login to view photograph ", "/");
            analyse_data = analyse_data.replace("Gender ", "");
            analyse_data = analyse_data.replace(" Program Name ", "/");
            analyse_data = analyse_data.replace(" Branch ", "/");
            analyse_data = analyse_data.replace(" Date of Joining ", "/");
            analyse_data = analyse_data.replace(" Department ", "/");
            analyse_data = analyse_data.replace(" Current semester ", "/");

            String[] stud_details = analyse_data.split("/");
            String[] num_ext = stud_details[6].split(" ");

            System.out.println(stud_details[0]);
            System.out.println(stud_details[1]);
            System.out.println(stud_details[2]);
            System.out.println(stud_details[3]);
            System.out.println(stud_details[5]);
            System.out.println(num_ext[0]);


            src = getUrlSource("https://ccw.iitm.ac.in/?q=search/IITMHostels/" + text.toLowerCase());
            doc = Jsoup.parse(src);
            prof_data = doc.getElementById("content").getElementsByClass("section").first();
            analyse_data = prof_data.text();

            analyse_data = analyse_data.substring(analyse_data.indexOf("/"));
            analyse_data = analyse_data.substring(analyse_data.indexOf(")"));

            String[] hostel_raw = analyse_data.split(" ");
            System.out.println(hostel_raw[3]);
            System.out.println(hostel_raw[6].replace("#", ""));

            return new student_details(stud_details[0], stud_details[1], stud_details[2], stud_details[5], num_ext[0], hostel_raw[3], hostel_raw[6].replace("#", ""));
            //return stud_details;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }



    }
    class profile extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            String src;
            try {
                URL url = new URL("https://ccw.iitm.ac.in/?q=IITMHostels/sinfo/" + text.toUpperCase());
                System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
                URLConnection yc = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuilder src_url = new StringBuilder();
                while ((inputLine = in.readLine()) != null)
                    src_url.append(inputLine);
                in.close();
                // src = getUrlSource("https://ccw.iitm.ac.in/?q=IITMHostels/sinfo/" + text.toUpperCase());
                src=src_url.toString();
                //src = getUrlSource("https://ccw.iitm.ac.in/?q=IITMHostels/sinfo/" + text.toUpperCase());
                Document doc = Jsoup.parse(src);
                Element prof_data = doc.getElementById("content").getElementsByClass("section").first().getElementsByTag("table").first();
                String analyse_data = prof_data.text();

                analyse_data = analyse_data.replace(" Login to view photograph ", "/");
                analyse_data = analyse_data.replace("Gender ", "");
                analyse_data = analyse_data.replace(" Program Name ", "/");
                analyse_data = analyse_data.replace(" Branch ", "/");
                analyse_data = analyse_data.replace(" Date of Joining ", "/");
                analyse_data = analyse_data.replace(" Department ", "/");
                analyse_data = analyse_data.replace(" Current semester ", "/");

                String[] stud_details = analyse_data.split("/");
                String[] num_ext = stud_details[6].split(" ");

                System.out.println(stud_details[0]);
                System.out.println(stud_details[1]);
                System.out.println(stud_details[2]);
                System.out.println(stud_details[3]);
                System.out.println(stud_details[5]);
                System.out.println(num_ext[0]);


                src = getUrlSource("https://ccw.iitm.ac.in/?q=search/IITMHostels/" + text.toLowerCase());
                doc = Jsoup.parse(src);
                prof_data = doc.getElementById("content").getElementsByClass("section").first();
                analyse_data = prof_data.text();

                analyse_data = analyse_data.substring(analyse_data.indexOf("/"));
                analyse_data = analyse_data.substring(analyse_data.indexOf(")"));

                String[] hostel_raw = analyse_data.split(" ");
                System.out.println(hostel_raw[3]);
                System.out.println(hostel_raw[6].replace("#", ""));

                return new student_details(stud_details[0], stud_details[1], stud_details[2], stud_details[5], num_ext[0], hostel_raw[3], hostel_raw[6].replace("#", ""));
                //return stud_details;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }



        }
    }
}