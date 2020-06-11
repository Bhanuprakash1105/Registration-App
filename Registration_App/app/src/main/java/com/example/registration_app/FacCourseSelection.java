package com.example.registration_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FacCourseSelection extends AppCompatActivity {

    ListView lv;

    Intent display;
    String branch, semester, courseChoosen;

    static Map< String, Integer > m = new HashMap< String, Integer >();
    static ArrayList <ArrayList<String>> arr = new ArrayList< ArrayList <String> >();

    ArrayAdapter<String> arrAdap;

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
        arrAdap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, arr.get(m.get(semester + branch)));
        lv.setAdapter(arrAdap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_menu_fac, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logoutFacID) {
            Intent tmp = new Intent(FacCourseSelection.this, MainActivity.class);
            startActivity(tmp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_course_selection);

        lv = (ListView) findViewById(R.id.coursesDisplayId);

        process();

        branch = getIntent().getStringExtra("branch");
        semester = getIntent().getStringExtra("semester");

        lv.setOnItemClickListener((parent, view, position, id) -> {

            CheckedTextView checkedTextView = (CheckedTextView) view;

            if(checkedTextView.isChecked()) {
                courseChoosen = arrAdap.getItem(position);
            } else {
                courseChoosen = "";
            }
        });

        displayCourses();

        display = new Intent(FacCourseSelection.this, ListDataActivity.class);
    }

    public void proceed(View v) {
        if(courseChoosen == null) {
            Toast.makeText(getApplicationContext(), "Choose one course to proceed", Toast.LENGTH_SHORT).show();
            return;
        } else if(courseChoosen.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Choose one course to proceed", Toast.LENGTH_SHORT).show();
            return;
        }
        display.putExtra("type", "PC");
        display.putExtra("course", courseChoosen);
        display.putExtra("branch", branch);
        display.putExtra("semester", semester);
        startActivity(display);
    }
}