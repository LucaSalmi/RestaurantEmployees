package com.khystudent.restaurantemployees;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class ReaderWriter {


    protected static File getFolder(AppCompatActivity context) {

        File folder = new File(context.getFilesDir(), "saveState");
        if (!folder.exists()) {
            folder.mkdir();
        }

        return folder;
    }

    protected static void saveFile(File folder, String name, String print) {

        try {
            File emp = new File(folder, name + ".txt");
            PrintWriter writer = new PrintWriter(emp);

            writer.write(print);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void deleteEmp(File folder, String name){

        String s = name + ".txt";
        File file = new File(folder, s);
        file.delete();

    }

    public static void reduceEmployeeStaticData(int salary){

        Employee.sumOfSalaries -= salary;
        Employee.numberOfEmployees --;
    }


    public static ArrayList<String> loadArchive(File folder, ArrayList<String> list) {

        File[] folderContent = folder.listFiles();

        if (folderContent != null) {

            for (File file : folderContent) {
                if (file.isFile()) {

                    try {
                        String temp = file.getName();
                        File readFile = new File(folder, temp);
                        Scanner sc = new Scanner(readFile);
                        list.add(sc.nextLine());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return list;
    }

    public static void saveToShared(SharedPreferences sharedPreferences) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("numberOfEmp", Employee.numberOfEmployees);
        editor.putInt("sumOfSalaries", Employee.sumOfSalaries);
        editor.apply();
    }

    public static int loadNumberOfEmp(SharedPreferences sharedPreferences) {

        int numberOfEmp = sharedPreferences.getInt("numberOfEmp", 0);
        return numberOfEmp;
    }

    public static int loadSumOfSalaries(SharedPreferences sharedPreferences) {

        int sumOfSalaries = sharedPreferences.getInt("sumOfSalaries", 0);
        return sumOfSalaries;
    }

    public static void loadData(SharedPreferences sharedPreferences) {

        Employee.numberOfEmployees = ReaderWriter.loadNumberOfEmp(sharedPreferences);
        Employee.sumOfSalaries = ReaderWriter.loadSumOfSalaries(sharedPreferences);
    }

    protected static String nameSubstringMaker(String empData) {

        String nameSubstring = (empData.substring((empData.indexOf(':') + 2), empData.indexOf(',')));
        return nameSubstring;
    }

    protected static int salarySubstringMaker(String empData) {

        String salarySubstringStepOne = empData.substring(empData.lastIndexOf("Salary"), empData.lastIndexOf("Date"));
        String salarySubstringFinal = salarySubstringStepOne.substring((salarySubstringStepOne.indexOf(':') + 2), salarySubstringStepOne.indexOf('k'));
        return Integer.parseInt(salarySubstringFinal);
    }

    protected static String idSubstringMaker(String empData) {

        String idSubstringStepOne = empData.substring(empData.lastIndexOf("ID"), empData.lastIndexOf("Position"));
        String idSubstringFinal = idSubstringStepOne.substring((idSubstringStepOne.indexOf(':') + 2), idSubstringStepOne.indexOf(','));
        return idSubstringFinal;
    }

    protected static String jobSubstringMaker(String empData) {

        String jobSubstringStepOne = empData.substring(empData.lastIndexOf("Position"), empData.lastIndexOf("Salary"));
        String jobSubstringFinal = jobSubstringStepOne.substring((jobSubstringStepOne.indexOf(':') + 2), jobSubstringStepOne.indexOf(','));
        return jobSubstringFinal;
    }

    protected static String dateSubstringMaker(String empData) {

        String dateSubstringStepOne = empData.substring(empData.lastIndexOf("Date"));
        String dateSubstringFinal = dateSubstringStepOne.substring((dateSubstringStepOne.indexOf(':') + 2));
        return dateSubstringFinal;
    }


}
