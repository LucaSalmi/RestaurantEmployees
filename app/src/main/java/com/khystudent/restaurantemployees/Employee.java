package com.khystudent.restaurantemployees;

public class Employee {

    private String name;
    private String position;
    private int id;
    private int salary;
    private String dateOfEmploy;

    protected static int numberOfEmployees;
    protected static int sumOfSalaries;


    private void Employee(String name, String position, int id, int salary, String dateOfEmploy){

        this.name = name;
        this.position = position;
        this.id = id;
        this.salary = salary;
        this.dateOfEmploy = dateOfEmploy;
        numberOfEmployees ++;
        sumOfSalaries += salary;

    }

}


