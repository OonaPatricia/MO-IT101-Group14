/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph;

/**
 *
 * @author patricesaints
 * @andrielleanas
 * @diamsecurtney
 * ac
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

        // Create a scanner to read input
        Scanner sc = new Scanner(System.in);

        // Ask the user to enter username
        System.out.print("Enter username: ");
        // Read the username input
        String username = sc.nextLine();

        // Ask the user to enter password
        System.out.print("Enter password: ");
        // Read the password input
        String password = sc.nextLine();

        // File path of MotorPH's Employee Details CSV File
        String empFile = "resources/MotorPH_Employee Data - Employee Details.csv";
        // File path of the MotorPH's Attendance Record CSV File
        String attFile = "resources/MotorPH_Employee Data - Attendance Record.csv";
        
        //Credentials Validation
        if (!(username.equals("employee") || username.equals("payroll_staff")) 
                || !password.equals("12345")) {
           System.out.print("Incorrect username and/or password. "); 
           return;
        }
        
        //Login Successful
        System.out.println("Login successful!");

        // If the username entered was "employee"
        if (username.equals("employee")) {

            // Display the options available
            System.out.println("\nPlease choose an option below");
            System.out.println("1. Enter Employee Number");
            System.out.println("2. Exit");

            // Read the option chosen
            int option = sc.nextInt();

            // Clear the scanner
            sc.nextLine();

            // If the user selected option 2, terminate the program
            if (option == 2) return;

            // Ask the employee to enter their employee number
            System.out.print("Enter Employee Number: ");
             // Read the employee number
            String empInput = sc.nextLine();

            // Call the method that displays employee information
            showEmployeeInfo(empInput, empFile);
        }

        // If the username entered was "payroll_staff"
        else if (username.equals("payroll_staff")) {

            // Display payroll staff options
            System.out.println("\nPlease choose an option below");
            System.out.println("1. Process Payroll");
            System.out.println("2. Exit");

            // Read the selected option
            int option = sc.nextInt();

            // Read the selected option
            sc.nextLine();

            // If option 2 is selected, terminate the program
            if (option == 2) return;

            // Display payroll processing options
            System.out.println("\n Please choose an option below");
            System.out.println("1. One Employee");
            System.out.println("2. All Employees");
            System.out.println("3. Exit");

            // Read the selected payroll processing option
            int choice = sc.nextInt();

            // Read the selected payroll processing option
            sc.nextLine();

            // If option 3 is selected, terminate the program
            if (choice == 3) return;

            // If payroll staff chooses to process payroll for one employee
            if (choice == 1) {
                // Ask for employee number
                System.out.print("Enter Employee Number: ");

                // Ask for employee number
                String empInput = sc.nextLine();

                 // Process payroll for that employee
                processEmployee(empInput, empFile, attFile);
            } 

            // If payroll staff chooses to process payroll for all employees
            else if (choice == 2) {

                 // Call method to process payroll for every employee in the file
                processAllEmployees(empFile, attFile);
            }
        }
    }
        // Method that displays employee details for employee users
        static void showEmployeeInfo(String empNo, String empFile) {

         // Try to open and read the employee CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(empFile))) {

            // Read the first line of the CSV file (header row)
            String header = br.readLine();
            
            // If the file is empty, display error message
            if (header == null) {
                System.out.println("Employee file is empty.");
                return;
            }

            // Variable to store each line read from the CSV file
            String line;
            
            // Boolean flag to check if employee was found
            boolean found = false;

            // Loop through each line of the CSV file
            while ((line = br.readLine()) != null) {

                // Split the CSV row into columns
                String[] data = parseCSVLine(line);

                // Check if the employee number matches the user input
                if (data.length > 0 && safeGet(data, 0).equals(empNo)) {

                    // Employee found
                    found = true;

                    // Display employee information
                    System.out.println("\nEmployee Number: " + safeGet(data, 0));
                    System.out.println("Employee Name: " + safeGet(data, 1) + ", " + safeGet(data, 2));
                    System.out.println("Birthday: " + safeGet(data, 3));
                    
                    break;
                }
            }

            // If employee number was not found in the CSV file
            if (!found) {
                System.out.println("Employee number does not exist.");
            }

        }  
            catch (Exception e) {
                // Display error if the file cannot be read
                System.out.println("Error reading employee file.");
            }
        }
        
    static void processAllEmployees(String empFile, String attFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(empFile))) {
            String header = br.readLine();
            if (header == null) {
                System.out.println("Employee file is empty.");
                return;
                }

        String line;
        while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line);
                String empNo = safeGet(data, 0);

            if (!empNo.isEmpty()) {
                    processEmployee(empNo, empFile, attFile);
                }
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Error reading employee file.");
        }
}

        static void processEmployee(String inputEmpNo, String empFile, String attFile) {

        String empNo = "";
        String firstName = "";
        String lastName = "";
        String birthday = "";
        double rate = 0;
        boolean found = false;

        int hourlyRateIndex = 18;

        try (BufferedReader br = new BufferedReader(new FileReader(empFile))) {

            String header = br.readLine();
            if (header != null) {
                String[] head = parseCSVLine(header);
                int idx = indexOf(head, "Hourly Rate");
                if (idx != -1) {
                    hourlyRateIndex = idx;
                }
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line);

                if (data.length > 0 && safeGet(data, 0).equals(inputEmpNo)) {
                    empNo = safeGet(data, 0);
                    lastName = safeGet(data, 1);
                    firstName = safeGet(data, 2);
                    birthday = safeGet(data, 3);

                    String rateStr = safeGet(data, hourlyRateIndex);
                    rate = parseMoney(rateStr);

                    found = true;
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading employee file.");
            return;
        }

        if (!found) {
            System.out.println("Employee number does not exist.");
            return;
        }

        System.out.println("\n===================================");
        System.out.println("Employee #: " + empNo);
        System.out.println("Employee Name: " + lastName + ", " + firstName);
        System.out.println("Birthday: " + birthday);

        double[] hours1 = new double[13];
        double[] hours2 = new double[13];

        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("H:mm");

        try (BufferedReader br = new BufferedReader(new FileReader(attFile))) {

            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = parseCSVLine(line);

                if (data.length == 0) {
                    continue;
                }

                if (!safeGet(data, 0).equals(empNo)) {
                    continue;
                }

                LocalDate date = parseDate(safeGet(data, 3));
                if (date == null) {
                    continue;
                }

                int month = date.getMonthValue();
                if (month < 6 || month > 12) {
                    continue;
                }

                LocalTime in = parseTime(safeGet(data, 4), timeFmt);
                LocalTime out = parseTime(safeGet(data, 5), timeFmt);

                if (in == null || out == null) {
                    continue;
                }

                double hrs = computeHours(in, out);

                if (date.getDayOfMonth() <= 15) {
                    hours1[month] += hrs;
                } else {
                    hours2[month] += hrs;
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading attendance file.");
            return;
        }

        for (int month = 6; month <= 12; month++) {

            YearMonth ym = YearMonth.of(2026, month);
            int lastDay = ym.lengthOfMonth();

            double gross1 = hours1[month] * rate;
            double gross2 = hours2[month] * rate;

            double combinedGross = gross1 + gross2;

            double sss = computeSSS(combinedGross);
            double phil = computePhilHealth(combinedGross);
            double pagibig = computePagIBIG(combinedGross);
            double tax = computeWithholdingTax(combinedGross);

            double totalDed = sss + phil + pagibig + tax;

            double net1 = gross1;
            double net2 = gross2 - totalDed;

            System.out.println("\n---------- " + monthName(month) + " ----------");

            System.out.println("Cutoff Date: " + monthName(month) + " 1-15");
            System.out.println("Total Hours Worked: " + hours1[month]);
            System.out.println("Gross Pay: " + gross1);
            System.out.println("Net Pay: " + net1);

            System.out.println("\nCutoff Date: " + monthName(month) + " 16-" + lastDay);
            System.out.println("Total Hours Worked: " + hours2[month]);
            System.out.println("Gross Pay: " + gross2);
            System.out.println("SSS: " + sss);
            System.out.println("PhilHealth: " + phil);
            System.out.println("Pag-IBIG: " + pagibig);
            System.out.println("Tax: " + tax);
            System.out.println("Total Deductions: " + totalDed);
            System.out.println("Net Pay: " + net2);
        }
    }

    // Method that calculates the number of working hours for a day
    static double computeHours(LocalTime actualIn, LocalTime actualOut) {
        
        // Official work start time
        LocalTime start = LocalTime.of(8, 0);

        // Official work end time
        LocalTime end = LocalTime.of(17, 0);

        // Grace period end time (8:10 AM)
        LocalTime graceEnd = LocalTime.of(8, 10);
        
        // Variable to store adjusted login time
        LocalTime in;

        // Variable to store adjusted login time
        if (actualIn.isBefore(start) || actualIn.equals(start)) {
            in = start;    
        }
            
        // If employee logs in within the grace period (before 8:10 AM), count as 8:00 AM
        else if (!actualIn.isAfter(graceEnd)) {
            in = start;
        } 

        // Otherwise use the actual login time
        else {
            in = actualIn;
        }

        // If logout time exceeds 5:00 PM, limit it to 5:00 PM
        LocalTime out = actualOut.isAfter(end) ? end : actualOut;

        // If logout happens before login, return 0 hours
        if (out.isBefore(in) || out.equals(in)) {
            return 0;
        }

        // Compute the total minutes worked
        long minutes = java.time.Duration.between(in, out).toMinutes();

         // Deduct 1 hour (60 minutes) for lunch break
        if (minutes > 60) {
            minutes -= 60;
        } else {
            minutes = 0;
        }

        double hours = minutes / 60.0;

        if (hours > 8) {
            hours = 8;
        }

        return hours;
    }
    // Converts a date string from .csv file into a Local Date
    static LocalDate parseDate(String s) {
        try {
            if (s == null) {
                return null;
            }

            String[] p = s.trim().split("/");
            if (p.length < 3) {
                return null;
            }

            int month = Integer.parseInt(p[0].trim());
            int day = Integer.parseInt(p[1].trim());
            int year = Integer.parseInt(p[2].trim());

            return LocalDate.of(year, month, day);

        } catch (Exception e) {
            return null;
        }
    }
    // Converts a time string from .csv file into a local time
    static LocalTime parseTime(String s, DateTimeFormatter fmt) {
        try {
            if (s == null) {
                return null;
            }

            s = s.trim();
            if (s.isEmpty()) {
                return null;
            }

            return LocalTime.parse(s, fmt);

        } catch (Exception e) {
            return null;
        }
    }
    // Converts a month number into its name
    static String monthName(int m) {
        switch (m) {
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "Month " + m;
        }
    }
    // Splits .csv row into columns correctly 
    static String[] parseCSVLine(String line) {
        ArrayList<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                out.add(stripQuotes(cur.toString().trim()));
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }

        out.add(stripQuotes(cur.toString().trim()));
        return out.toArray(new String[0]);
    }
    // Removes quotation marks from the text
    static String stripQuotes(String s) {
        if (s == null) {
            return "";
        }

        s = s.trim();
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }

        return s;
    }

    static String safeGet(String[] arr, int idx) {
        if (arr == null) {
            return "";
        }

        if (idx < 0 || idx >= arr.length) {
            return "";
        }

        return arr[idx].trim();
    }

    static int indexOf(String[] headers, String target) {
        if (headers == null) {
            return -1;
        }

        for (int i = 0; i < headers.length; i++) {
            if (headers[i] != null && headers[i].trim().equalsIgnoreCase(target)) {
                return i;
            }
        }

        return -1;
    }
    // Converts salary text from .csv file into a number
    static double parseMoney(String s) {
        try {
            if (s == null) {
                return 0;
            }

            s = stripQuotes(s).replace(",", "").trim();
            if (s.isEmpty()) {
                return 0;
            }

            return Double.parseDouble(s);

        } catch (Exception e) {
            return 0;
        }
    }
    // Calculates SSS contribution based on gross salary
    static double computeSSS(double gross) {
        if (gross < 3250) {
            return 135;
        } else if (gross >= 24750) {
            return 1125;
        } else {
            return 135 + ((int) ((gross - 3250) / 500) + 1) * 22.5;
        }
    }
    // Calculates Philhealth deduction
    static double computePhilHealth(double gross) {
        if (gross <= 10000) {
            return 150;
        } else if (gross < 60000) {
            return gross * 0.015;
        } else {
            return 900;
        }
    }
    static double computePagIBIG(double gross) {
    double contribution;
    // If salary is 1500 or below → 1% contribution
    if (gross <= 1500) {
        contribution = gross * 0.01;
    } 
    
    // If salary is above 1500 → 2% contribution
    else {
        contribution = gross * 0.02;
    }
        
    // Apply Pag-IBIG maximum contribution cap of ₱100
    if (contribution > 100) {
        contribution = 100;
    }

    return contribution;
}
    // Calculation of income tax deduction
    static double computeWithholdingTax(double gross) {
        if (gross <= 20832) {
            return 0;
        } else if (gross <= 33332) {
            return (gross - 20833) * 0.20;
        } else if (gross <= 66666) {
            return 2500 + (gross - 33333) * 0.25;
        } else if (gross <= 166666) {
            return 10833 + (gross - 66667) * 0.30;
        } else if (gross <= 666666) {
            return 40833.33 + (gross - 166667) * 0.32;
        } else {
            return 200833.33 + (gross - 666667) * 0.35;
        }
    }
}

    
