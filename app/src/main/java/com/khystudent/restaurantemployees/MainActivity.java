package com.khystudent.restaurantemployees;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
    SharedPreferences sharedPreferences;

    String name;
    String print;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFields();
         sharedPreferences = getSharedPreferences("com.khystudent.restaurantemployees.MyPrefs", MODE_PRIVATE);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        employeeList.setAdapter(adapter);

        ReaderWriter.loadArchive(ReaderWriter.getFolder(MainActivity.this), list);
        Employee.loadData(sharedPreferences);

        updateField();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getText();
                adapter.notifyDataSetChanged();
                saveData();

            }
        });

        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                list.remove(i);
                adapter.notifyDataSetChanged();

            }
        });

    }

    protected void getText() {

        name = nameField.getText().toString();
        String id = idField.getText().toString();
        String job = jobPositionField.getText().toString();
        String salary = salaryField.getText().toString();
        String date = dateOfEmplField.getText().toString();

        if(name.isEmpty() || id.isEmpty() || job.isEmpty() || salary.isEmpty() || date.isEmpty()){

            Toast.makeText(MainActivity.this, "No empty fields allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        print = name + ", " + id + ", " + job + ", " + salary + ", " + date;
        list.add(print);

        Employee saved = new Employee(name, Integer.parseInt(id), job, Integer.parseInt(salary), date);

        updateField();
        clearFields();


    }

    protected void updateField(){

        numberOfEmp.setText(String.valueOf(Employee.numberOfEmployees));
        medianSalary.setText(String.valueOf(Employee.sumOfSalaries/Employee.numberOfEmployees));
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
        ReaderWriter.saveFile(ReaderWriter.getFolder(MainActivity.this), name, print);
        ReaderWriter.saveToShared(sharedPreferences);
    }

    protected void saveData(){
        ReaderWriter.saveFile(ReaderWriter.getFolder(MainActivity.this), name, print);
        ReaderWriter.saveToShared(sharedPreferences);

    }

    protected void setFields(){

        nameField = findViewById(R.id.name_field);
        idField = findViewById(R.id.id_number_field);
        jobPositionField = findViewById(R.id.position_field);
        salaryField = findViewById(R.id.salary_field);
        dateOfEmplField = findViewById(R.id.empl_date_field);
        saveBtn = findViewById(R.id.save_btn);

        numberOfEmp = findViewById(R.id.num_of_empl);
        medianSalary = findViewById(R.id.median_salary);

        employeeList = findViewById(R.id.employee_list);
    }
}