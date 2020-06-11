package com.example.registration_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class FacultyActivity extends AppCompatActivity {

    Spinner semSpin, branchSpin;
    Intent display, courseSelection;
    String branch, semester;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_menu_fac, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logoutFacID) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Faculty Activity");

        semSpin = (Spinner) findViewById(R.id.semSpinId2);
        branchSpin = (Spinner) findViewById(R.id.branchSpinId2);

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

        display = new Intent(FacultyActivity.this, ListDataActivity.class);
    }

    public void feeList(View v) {
        display.putExtra("type", "FEE");
        branch = (String) branchSpin.getSelectedItem();
        semester = String.valueOf(semSpin.getSelectedItem());
        display.putExtra("branch", branch);
        display.putExtra("semester", semester);
        startActivity(display);
    }

    public void registeredList(View v) {
        display.putExtra("type", "SRC");
        branch = (String) branchSpin.getSelectedItem();
        semester = String.valueOf(semSpin.getSelectedItem());
        display.putExtra("branch", branch);
        display.putExtra("semester", semester);
        startActivity(display);
    }

    public void courseList(View v) {
        courseSelection = new Intent(FacultyActivity.this, FacCourseSelection.class);
        branch = (String) branchSpin.getSelectedItem();
        semester = String.valueOf(semSpin.getSelectedItem());
        courseSelection.putExtra("branch", branch);
        courseSelection.putExtra("semester", semester);
        startActivity(courseSelection);
    }
}
