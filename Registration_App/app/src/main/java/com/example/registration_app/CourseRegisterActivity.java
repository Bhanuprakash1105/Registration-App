package com.example.registration_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseRegisterActivity extends AppCompatActivity {

    DataBase dataBase;

    long id;

    Spinner semSpin, branchSpin;
    ListView lv;
    Integer semValue;
    String branchValue;
    ArrayList <String> choosenCourses;
    ArrayAdapter<String> arrAdap;

    static Map< String, Integer > m = new HashMap< String, Integer >();
    static ArrayList <ArrayList<String>> arr = new ArrayList< ArrayList <String> >();

    private static void process() {
        for(int i = 1, x = 0; i <= 8; ++i, ++x) {
            m.put(i + "CSE", x);
            m.put(i + "ECE", ++x);
        }

        arr.add(new ArrayList<String>(Arrays.asList("MAL 101: Mathematics-I (4)","BEL 102: Elements of Electrical Engineering (4)", "BSL 101: Applied Sciences (4)",
                "CSL 101: Computer Programming (4)", "ECL 101: Analog Electronics (4)",
                "HUL 102: Environmental Studies (2)", "SAP 101: Health Sports and Safety (0)")));
        arr.add(new ArrayList<String>(Arrays.asList("MAL 101: Mathematics-I (4)", "BEL 102: Elements of Electrical Engineering (4)",
                "CSL 101: Computer Programming (4)", "ECL 101: Analog Electronics (4)",
                "HUL 101: Communication Skills (3)", "BEL 101: Mechanics and Graphics (4)")));
        arr.add(new ArrayList<String>(Arrays.asList("MAL 102: Mathematics-II (4)", "ECL 102: Digital Electronics (4)",
                "CSL 102: Data Structures (4)", "CSL 103: Application Programming (4)",
                "HUL 101: Communication Skills (3)", "BEL 101: Mechanics and Graphics (4)")));
        arr.add(new ArrayList<String>(Arrays.asList("MAL 102: Mathematics-II (4)",
                "ECL 102: Digital Electronics (4)", "CSL 102: Data Structures (4)",
                "CSL 103: Application Programming (4)", "BSL 101: Applied Sciences (4)",
                "HUL 102: Environmental Studies (2)", "SAP 101: Health Sports and Safety (0)")));
        arr.add(new ArrayList<String>(Arrays.asList("MAL 201: Mathematics-III (4)", "CSL 210: Data Structures with Applications (3)",
                "CSL 202: Object Oriented Programming (4)", "CSL 203: Computer System Organisation (3)",
                "CSP 201: IT Workshop-I (2)", "ECL 202: Microprocessors and Interfacing (4)")));
        arr.add(new ArrayList<String>(Arrays.asList("MAL 201: Mathematics-III (4)","ECL 201: Signals and Systems (4)", "ECL 202: Microprocessors and Interfacing (4)",
                "ECL 203: Analog ICs (4)", "ECL 204: Network Theory (4)", "CSP 201: IT Workshop-I (2)")));
        arr.add(new ArrayList<String>(Arrays.asList("CSL 205: Design and Analysis of Algorithms (4)", "CSL 206: Software Engineering (3)",
                "CSL 207: Operating Systems (4)", "CSL 208: Design Principles of Programming Languages (4)",
                "CSL 204: Discrete Maths and Graph Theory (4)", "CSP 202: IT Workshop-II (2)")));
        arr.add(new ArrayList<String>(Arrays.asList("ECL 301: Digital Signal Processing (4)", "ECL 303: Hardware Description Languages (4)",
                "ECL 304: Control Systems (4)", "ECL 305: Electromagnetics (3)",
                "ECL 306: Computer Architecture % Organisation (3)", "CSP 202: IT Workshop-II (2)")));
    }

    private void displayCourses() {
        semValue = (Integer) semSpin.getSelectedItem();
        branchValue = (String) branchSpin.getSelectedItem();
        arrAdap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, arr.get(m.get(semValue + branchValue)));
        lv.setAdapter(arrAdap);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_register);
        process();

        dataBase = new DataBase(getApplicationContext());

        id = getIntent().getLongExtra("User_ID", -1);

        choosenCourses = new ArrayList<String>();
        lv = (ListView) findViewById(R.id.coursesDisplayId);
        semSpin = (Spinner) findViewById(R.id.semSpinId);
        branchSpin = (Spinner) findViewById(R.id.branchSpinId);

        // Spinner Drop down elements
        List<Integer> semesters = new ArrayList<Integer>();
        semesters.add(1);
        semesters.add(2);
        semesters.add(3);
        semesters.add(4);
        // Creating adapter for spinner
        ArrayAdapter<Integer> semDataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, semesters);
        // Drop down layout style - list view with radio button
        semDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        semSpin.setAdapter(semDataAdapter);

        // Spinner Drop down elements
        List<String> branches = new ArrayList<String>();
        branches.add("CSE");
        branches.add("ECE");
        // Creating adapter for spinner
        ArrayAdapter<String> branchDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branches);
        // Drop down layout style - list view with radio button
        branchDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        branchSpin.setAdapter(branchDataAdapter);

        semSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayCourses();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        branchSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayCourses();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv.setOnItemClickListener((parent, view, position, id) -> {

            CheckedTextView checkedTextView = (CheckedTextView) view;

            if(checkedTextView.isChecked()) {
                choosenCourses.add(arrAdap.getItem(position));
                //Toast.makeText(getApplicationContext(), "Registered " + arrAdap.getItem(position), Toast.LENGTH_SHORT).show();
            } else {
                choosenCourses.remove(arrAdap.getItem(position));
                //Toast.makeText(getApplicationContext(), "Unregistered " + arrAdap.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void regiseterButton(View v) {
        //Log.i("Choosen_Courses: ", Arrays.toString(choosenCourses.toArray()));

        if(choosenCourses.size() < 4) {
            Toast.makeText(getApplicationContext(), "Warning: Minimum 4 courses should be selected", Toast.LENGTH_LONG).show();
            return;
        }

        String result = "";
        for(String x: choosenCourses) {
            result += (x + ", ");
        }
        //Log.e("Courses Registered= ", result);
        ContentValues contentValues = new ContentValues();
        contentValues.put("courses", result);
        contentValues.put("semester", semValue);
        contentValues.put("branch", branchValue);
        dataBase.update(String.valueOf(id), contentValues);
        setResult(1);
        finish();
    }
}
