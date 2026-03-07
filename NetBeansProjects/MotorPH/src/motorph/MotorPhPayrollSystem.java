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

        //if username, equal employee
        if (username.equals("employee")) {

            System.out.println("\n1. Enter Employee Number");
            System.out.println("2. Exit");
            int option = sc.nextInt();
            sc.nextLine();

            if (option == 2) return;

            System.out.print("Enter Employee Number: ");
            String empInput = sc.nextLine();

            showEmployeeInfo(empInput, empFile);

        }

        else if (username.equals("payroll_staff")) {

            System.out.println("\n1. Process Payroll");
            System.out.println("2. Exit");
            int option = sc.nextInt();
            sc.nextLine();

            if (option == 2) return;

            System.out.println("\n1. One Employee");
            System.out.println("2. All Employees");
            System.out.println("3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 3) return;

            if (choice == 1) {
                System.out.print("Enter Employee Number: ");
                String empInput = sc.nextLine();
                processEmployee(empInput, empFile, attFile);
            } else if (choice == 2) {
                processAllEmployees(empFile, attFile);
            }
        }
        static void showEmployeeInfo(String empNo, String empFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(empFile))) {

            String header = br.readLine();
            if (header == null) {
                System.out.println("Employee file is empty.");
                return;
            }

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line);
                if (data.length > 0 && safeGet(data, 0).equals(empNo)) {
                    found = true;
                    System.out.println("\nEmployee Number: " + safeGet(data, 0));
                    System.out.println("Employee Name: " + safeGet(data, 1) + ", " + safeGet(data, 2));
                    System.out.println("Birthday: " + safeGet(data, 3));
                    break;
                }
            }

            if (!found) {
                System.out.println("Employee number does not exist.");
            }

        } catch (Exception e) {
            System.out.println("Error reading employee file.");
        }
    }

        
    }

        
}
