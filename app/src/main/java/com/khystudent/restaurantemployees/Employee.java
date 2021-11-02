package com.khystudent.restaurantemployees;

public class Employee {

    private String name;
    private String position;
    private int id;
    private int salary;
    private String dateOfEmploy;

    protected static int numberOfEmployees;
    protected static int sumOfSalaries;


    public Employee(String name, int id, String position, int salary, String dateOfEmploy){

        this.name = name;
        this.id = id;
        this.position = position;
        this.salary = salary;
        this.dateOfEmploy = dateOfEmploy;
        numberOfEmployees ++;
        sumOfSalaries += salary;

    }

}


