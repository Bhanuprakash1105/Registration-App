package com.example.registration_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ListDataActivity extends AppCompatActivity {

    DataBase dataBase;
    String type, branch, semester, course;
    Integer semesterInt;

    ListView lv;
    ArrayAdapter <String> arrayAdapter;
    ArrayList < Map <String, String> > feeDetails, srcDetails, pcDetails;
    ArrayList <String> finalList, registeredCourses;

    private boolean matched(String a, String b) {
        String c = a.replaceAll("\\s", "");
        String d = b.replaceAll("\\s", "");
        return c.equals(d);
    }

    private String decorateString(String s) {
        StringBuilder decString = new StringBuilder();
        String[] str = s.split(", ");
        for(String x: str) {
            decString.append(x).append("\n\n");
        }
        return decString.toString();
    }

    private boolean courseIsPresent(String checkCourse, String allCoursesReg) {
        String temp = "";
        for(int i = 0; i < allCoursesReg.length(); ++i) {
            if(allCoursesReg.charAt(i) != ',') {
                temp += allCoursesReg.charAt(i);
            } else {
                if(matched(temp, checkCourse)) {
                    return true;
                }
                temp = ""; ++i;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_menu_fac, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logoutFacID) {
            Intent tmp = new Intent(ListDataActivity.this, MainActivity.class);
            startActivity(tmp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        dataBase = new DataBase(getApplicationContext());

        type = getIntent().getStringExtra("type");
        branch = getIntent().getStringExtra("branch");
        semester = getIntent().getStringExtra("semester");
        assert semester != null;
        semesterInt = Integer.parseInt(semester);

        lv = (ListView) findViewById(R.id.listDetailsID);

        String s;
        assert type != null;
        switch (type) {
            case "FEE":
                finalList = new ArrayList<String>();
                //finalList.add("Email-ID\t\tName\t\tFee Status");
                feeDetails = dataBase.giveAllAsked(branch, semesterInt);
                for(Map<String, String> temp: feeDetails) {
                    s = "\nEmail: " + temp.get("email") + "\n\nName: " + temp.get("name") + "\n\nFee Status: " + temp.get("fee") + "\n";
                    finalList.add(s);
                }
                break;
            case "SRC":
                finalList = new ArrayList<String>();
                registeredCourses = new ArrayList<String>();
                //finalList.add("Email-ID\t\tName\t\tCourses Registered");
                srcDetails = dataBase.giveAllAsked(branch, semesterInt);
                for(Map<String, String> temp: srcDetails) {
                    assert temp.get("courses") != null;
                    s = "\nEmail: " + temp.get("email") + "\n\nName: " + temp.get("name") + "\n";
                    registeredCourses.add(decorateString(temp.get("courses")));
                    finalList.add(s);
                    finalList.add(">> Tap here for Courses");
                }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(position%2 != 0) {
                            new AlertDialog.Builder(ListDataActivity.this).setTitle("Registered Courses")
                                    .setMessage(registeredCourses.get((int)position/2))
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }).show();
                        }
                    }
                });
                break;
            case "PC":
                finalList = new ArrayList<String>();
                //finalList.add("Email-ID\t\tName");
                course = getIntent().getStringExtra("course");
                pcDetails = dataBase.giveAllAsked(branch, semesterInt);
                for(Map<String, String> temp: pcDetails) {
                    //Log.e("Strings", "The details are: " + temp.get("email") + " courses " + temp.get("courses"));
                    assert temp.get("courses") != null;
                    if(courseIsPresent(course, temp.get("courses"))) {
                        s = "\nEmail: " + temp.get("email") + "\n\nName: " + temp.get("name") + "\n";
                        finalList.add(s);
                    }
                }
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error in database read", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        if(finalList.size() == 0) {
            //Toast.makeText(ListDataActivity.this, "No data has be feeded yet", Toast.LENGTH_LONG).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("No data has be feeded yet");
            dialog.setTitle("Message");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 , finalList);
        lv.setAdapter(arrayAdapter);
    }
}