package com.khystudent.restaurantemployees;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class ReaderWriter{


    protected static File getFile(AppCompatActivity context){

        File folder = new File(context.getFilesDir(), "saveState");
        if (!folder.exists()) {
            folder.mkdir();
        }

        File[] folderContent = folder.listFiles();
        if (folderContent.length == 1){

            return folderContent[0];
        }

        File archive = new File(folder, "Employees.txt");

        return archive;
    }

    protected static void saveFile(File archive, ArrayList<String> list){


        for (String s : list) {

            try {

                PrintWriter writer = new PrintWriter(archive);
                writer.write(s + "\n");
                writer.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

    }

    public static ArrayList<String> loadArchive(File archive, ArrayList<String> list) {


        try {
            Scanner sc = new Scanner(archive);
            while (sc.hasNext()) {

                list.add(sc.nextLine());
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }


}
