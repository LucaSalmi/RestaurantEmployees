package com.khystudent.restaurantemployees;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText nameField;
    EditText idField;
    EditText jobPositionField;
    EditText salaryField;
    EditText dateOfEmplField;

    ListView employeeList;

    Button saveBtn;

    TextView numberOfEmp;
    TextView medianSalary;

    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.name_field);
        idField = findViewById(R.id.id_number_field);
        jobPositionField = findViewById(R.id.position_field);
        salaryField = findViewById(R.id.salary_field);
        dateOfEmplField = findViewById(R.id.empl_date_field);
        saveBtn = findViewById(R.id.save_btn);

        numberOfEmp = findViewById(R.id.num_of_empl);
        medianSalary = findViewById(R.id.median_salary);

        employeeList = findViewById(R.id.employee_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        employeeList.setAdapter(adapter);

        ReaderWriter.loadArchive(ReaderWriter.getFile(MainActivity.this), list);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getText();
                adapter.notifyDataSetChanged();


            }
        });

    }

    protected void getText() {

        String name = nameField.getText().toString();
        String id = idField.getText().toString();
        Log.d(TAG, "getText: "+id);
        String job = jobPositionField.getText().toString();
        String salary = salaryField.getText().toString();
        String date = dateOfEmplField.getText().toString();

        if(name.isEmpty() || id.isEmpty() || job.isEmpty() || salary.isEmpty() || date.isEmpty()){

            Toast.makeText(MainActivity.this, "No empty fields allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        String print = name + ", " + id + ", " + job + ", " + salary + ", " + date;
        list.add(print);

        Employee saved = new Employee(name, Integer.parseInt(id), job, Integer.parseInt(salary), date);

        numberOfEmp.setText(String.valueOf(Employee.numberOfEmployees));
        medianSalary.setText(String.valueOf(Employee.sumOfSalaries/Employee.numberOfEmployees));

        clearFields();


    }

    protected void clearFields(){

        nameField.setText("");
        idField.setText("");
        jobPositionField.setText("");
        salaryField.setText("");
        dateOfEmplField.setText("");

    }

    @Override
    protected void onPause() {
        super.onPause();
        ReaderWriter.saveFile(ReaderWriter.getFile(MainActivity.this), list);


    }

    protected void saveData(){
        ReaderWriter.saveFile(ReaderWriter.getFile(MainActivity.this), list);
    }
}