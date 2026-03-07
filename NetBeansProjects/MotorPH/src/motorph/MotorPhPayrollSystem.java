/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph;

/**
 *
 * @author patricesaints
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MotorPhPayrollSystem {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        
        username = username.toLowerCase();
        
      String empFile = "resources/MotorPH_Employee Data - Employee Details.csv";
      String attFile = "resources/MotorPH_Employee Data - Attendance Record.csv";
        
        //Credentials Validation
        if (!(username.equals("employee") || username.equals("payroll_staff")) 
                || !password.equals("12345")) {
            
           System.out.print("Incorrect username and/or password. "); 
           return;
        }
        
        //Login Successful
        System.out.println("Login successful!");
        
        sc.close();

import java.io.BufferedReader; 
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

public class EmployeeNumber {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String empNumInput = sc.nextLine();

        boolean found = false;

        try {
            InputStream is = EmployeeNumber.class
                .getClassLoader()
                .getResourceAsStream("MotorPh_EmployeeData.csv");

            if (is == null) { 
                System.out.println("File not found!");
                return;
            }
            BufferedReader br = new BuffereReader(new InputStreamReader(is));

            br. readLine(); // skip header

            String line;

            while ((line = br.readline()) !=null) {

                System.out.println("\nEmployee Found:");
                System.out.println("Employee #: " + data[0]);
                System.out.println("Last Name: " + data[1]);
                System.out.println("First Name: " + data[2]);
                System.out.println("Birthday: " + data[3]);
                System.out.println("Position: " + data[11]);
                System.out.println("Basic Salary: " + data[13]);
                System.out.println("Hourly Rate: " + data[18]);

                found = true;
                break;
            }
        }

        if (!found) { 
            System.out.println("Employee does not exist.");
        }

    } catch (IOException e) { 
        System.out.println("Error reading file: " + e.getMessege());
    }

    sc.close(); 
  }
}
