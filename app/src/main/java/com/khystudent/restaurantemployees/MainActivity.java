package com.khystudent.restaurantemployees;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText nameField;
    EditText idField;
    EditText jobPositionField;
    EditText salaryField;
    EditText dateOfEmpField;

    ListView employeeList;

    Button saveBtn;

    TextView numberOfEmp;
    TextView medianSalary;

    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;

    String name;
    String print;

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFields();

        sharedPreferences = getSharedPreferences("com.khystudent.restaurantemployees.MyPrefs", MODE_PRIVATE);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        employeeList.setAdapter(adapter);
        ReaderWriter.loadArchive(ReaderWriter.getFolder(MainActivity.this), list);
        ReaderWriter.loadData(sharedPreferences);

        setListeners();

    }

    protected void setListeners() {

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getText();
                adapter.notifyDataSetChanged();
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateOfEmpField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        employeeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String empData = adapterView.getItemAtPosition(i).toString();

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.alert_dialog_title))
                        .setMessage(getString(R.string.alert_dialog_message))

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                ReaderWriter.deleteEmp(ReaderWriter.getFolder(MainActivity.this), ReaderWriter.nameSubstringMaker(empData));
                                ReaderWriter.reduceEmployeeStaticData(ReaderWriter.salarySubstringMaker(empData));
                                list.remove(i);
                                adapter.notifyDataSetChanged();
                                updateField();
                                ReaderWriter.saveToShared(sharedPreferences);

                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                return false;
            }
        });
    }

    protected void getText() {

        name = nameField.getText().toString();
        String id = idField.getText().toString();
        String job = jobPositionField.getText().toString();
        String salary = salaryField.getText().toString();
        String date = dateOfEmpField.getText().toString();

        if (name.isEmpty() || id.isEmpty() || job.isEmpty() || salary.isEmpty() || date.isEmpty()) {

            Toast.makeText(MainActivity.this, getString(R.string.toast_text_empty_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        print = getString(R.string.emp_name) + name
                + getString(R.string.emp_id) + id
                + getString(R.string.emp_position) + job
                + getString(R.string.emp_salary) + salary + getString(R.string.money)
                + getString(R.string.emp_date) + date;
        list.add(print);

        Employee saved = new Employee(name, Integer.parseInt(id), job, Integer.parseInt(salary), date);

        updateField();
        clearFields();
        saveData(saved);


    }

    protected void updateField() {

        numberOfEmp.setText(String.valueOf(Employee.numberOfEmployees));
        if (Employee.numberOfEmployees != 0) {
            medianSalary.setText(String.valueOf(Employee.sumOfSalaries / Employee.numberOfEmployees));
        } else {
            medianSalary.setText("0");
        }
    }

    protected void clearFields() {

        nameField.setText(getString(R.string.empty_string));
        idField.setText(getString(R.string.empty_string));
        jobPositionField.setText(getString(R.string.empty_string));
        salaryField.setText(getString(R.string.empty_string));
        dateOfEmpField.setText(getString(R.string.empty_string));

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateField();
    }

    protected void saveData(Employee saved) {
        ReaderWriter.saveFile(ReaderWriter.getFolder(MainActivity.this), name, print);
        ReaderWriter.saveToShared(sharedPreferences);
    }

    protected void setFields() {

        nameField = findViewById(R.id.name_field);
        idField = findViewById(R.id.id_number_field);
        jobPositionField = findViewById(R.id.position_field);
        salaryField = findViewById(R.id.salary_field);
        dateOfEmpField = findViewById(R.id.empl_date_field);
        saveBtn = findViewById(R.id.save_btn);

        numberOfEmp = findViewById(R.id.num_of_empl);
        medianSalary = findViewById(R.id.median_salary);

        employeeList = findViewById(R.id.employee_list);
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALY);
        dateOfEmpField.setText(sdf.format(myCalendar.getTime()));
    }
}