/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package motorph;

/**
 *
 * @author patricesaints
 * @author andrielleanas
 * 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;


public class MotorPhPayrollSystem {
    
    static HashMap<String, String[]> employeeMap = new HashMap<>();
    static HashMap<String, ArrayList<String[]>> attendanceMap = new HashMap<>();

    static int empNoIdx = -1;
    static int firstNameIdx = -1;
    static int lastNameIdx = -1;
    static int birthdayIdx = -1;
    static int hourlyRateIdx = -1;
    static int basicSalaryIdx = -1;
    
    public static void main(String[] args) {

        // Create a scanner to read input
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        String empFile = "resources/MotorPH_Employee Data - Employee Details.csv";
        String attFile = "resources/MotorPH_Employee Data - Attendance Record.csv";
        
        loadEmployees(empFile);
        loadAttendance(attFile);
        
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

            System.out.println("\nPlease choose an option below");
            System.out.println("1. Enter Employee Number");
            System.out.println("2. Exit");

            int option;

            while (true) {
                if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
            }

            if (option == 2) return;

            System.out.print("Enter Employee Number: ");
            String empInput = sc.nextLine();
            showEmployeeInfo(empInput.trim());
        }

        // If the username entered was "payroll_staff"
        else if (username.equals("payroll_staff")) {

            System.out.println("\nPlease choose an option below");
            System.out.println("1. Process Payroll");
            System.out.println("2. Exit");

            int option;

            while (true) {
                if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine();
                break;
            } else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }
            
            if (option == 2) return;

            System.out.println("\n Please choose an option below");
            System.out.println("1. One Employee");
            System.out.println("2. All Employees");
            System.out.println("3. Exit");

            int choice;

            while (true) {
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine();
                }
            }

            if (choice == 3) return;

            if (choice == 1) {
                System.out.print("Enter Employee Number: ");

                String empInput = sc.nextLine().trim();
                processEmployee(empInput);
            } 

            else if (choice == 2) {

                 // Call method to process payroll for every employee in the file
                processAllEmployees();
            }
        }
    }
    
    static void loadEmployees(String empFile) {
    try (BufferedReader br = new BufferedReader(new FileReader(empFile))) {

        String header = br.readLine();
        if (header == null) {
            System.out.println("Employee file is empty.");
            return;
        }

        String[] headers = parseCSVLine(header);

        empNoIdx = indexOf(headers, "Employee #");
        firstNameIdx = indexOf(headers, "First Name");
        lastNameIdx = indexOf(headers, "Last Name");
        birthdayIdx = indexOf(headers, "Birthday");
        hourlyRateIdx = indexOf(headers, "Hourly Rate");
        basicSalaryIdx = indexOf(headers, "Basic Salary");

        if (empNoIdx == -1) empNoIdx = 0;
        if (firstNameIdx == -1) firstNameIdx = 2;
        if (lastNameIdx == -1) lastNameIdx = 1;
        if (birthdayIdx == -1) birthdayIdx = 3;
        if (hourlyRateIdx == -1) hourlyRateIdx = 18;
        if (basicSalaryIdx == -1) basicSalaryIdx = 13;

        String line;
        while ((line = br.readLine()) != null) {
            String[] data = parseCSVLine(line);
            String empNo = safeGet(data, empNoIdx);

            if (!empNo.isEmpty()) {
                employeeMap.put(empNo, data);
            }
        }

    } catch (Exception e) {
        System.out.println("Error reading employee file.");
    }
}
    
    static void loadAttendance(String attFile) {
    try (BufferedReader br = new BufferedReader(new FileReader(attFile))) {

        String header = br.readLine();
        if (header == null) {
            System.out.println("Attendance file is empty.");
            return;
        }

        String line;
        while ((line = br.readLine()) != null) {
            String[] data = parseCSVLine(line);
            String empNo = safeGet(data, 0);

            if (empNo.isEmpty()) continue;

            attendanceMap
                .computeIfAbsent(empNo, k -> new ArrayList<>())
                .add(data);
        }

    } catch (Exception e) {
        System.out.println("Error reading attendance file.");
    }
}
    
    
    
        // Method that displays employee details for employee users
    static void showEmployeeInfo(String empNo) {
        String[] data = employeeMap.get(empNo.trim());

        if (data == null) {
            System.out.println("Employee number does not exist.");
            return;
    }

        System.out.println("\nEmployee Number: " + safeGet(data, empNoIdx));
        System.out.println("Employee Name: " + safeGet(data, firstNameIdx) + ", " + safeGet(data, lastNameIdx));
        System.out.println("Birthday: " + safeGet(data, birthdayIdx));
}
        
   static void processAllEmployees() {
        List<String> sortedKeys = new ArrayList<>(employeeMap.keySet());
        Collections.sort(sortedKeys);

        for (String empNo : sortedKeys) {
            processEmployee(empNo);
    }
}

    static void processEmployee(String inputEmpNo) {

        String[] empData = employeeMap.get(inputEmpNo.trim());

        if (empData == null) {
            System.out.println("Employee number does not exist.");
            return;
    }

        String empNo = safeGet(empData, empNoIdx);
        String firstName = safeGet(empData, firstNameIdx);
        String lastName = safeGet(empData, lastNameIdx);
        String birthday = safeGet(empData, birthdayIdx);
        double rate = parseMoney(safeGet(empData, hourlyRateIdx));
        double basicSalary = parseMoney(safeGet(empData, basicSalaryIdx));

        double[] hours1 = new double[13];
        double[] hours2 = new double[13];

        fillCutoffHours(empNo, hours1, hours2);
        printPayrollReport(empNo, firstName, lastName, birthday, rate, basicSalary, hours1, hours2);
}

    static void fillCutoffHours(String empNo, double[] hours1, double[] hours2) {
        ArrayList<String[]> records = attendanceMap.get(empNo);

        if (records == null) {
            return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("H:mm");

        for (String[] data : records) {
            LocalDate date = parseDate(safeGet(data, 3));
            LocalTime timeIn = parseTime(safeGet(data, 4), fmt);
            LocalTime timeOut = parseTime(safeGet(data, 5), fmt);

        if (date == null || timeIn == null || timeOut == null) {
            continue;
        }

        int month = date.getMonthValue();
        if (month < 6 || month > 12) {
            continue;
        }

        double hoursWorked = computeHours(timeIn, timeOut);

        if (date.getDayOfMonth() <= 15) {
            hours1[month] += hoursWorked;
        } else {
            hours2[month] += hoursWorked;
        }
    }
}
    
    static void printPayrollReport(String empNo, String firstName, String lastName,
                               String birthday, double rate, double basicSalary,
                               double[] hours1, double[] hours2) {

    System.out.println("\n=====================================");
    System.out.println("Employee Number: " + empNo);
    System.out.println("Employee Name: " + firstName + ", " + lastName);
    System.out.println("Birthday: " + birthday);

    for (int month = 6; month <= 12; month++) {

        double gross1 = hours1[month] * rate;
        double gross2 = hours2[month] * rate;
        double totalGross = gross1 + gross2;

        double sss = computeSSS(totalGross);
        double philHealth = computePhilHealth(basicSalary);
        double pagIBIG = computePagIBIG(totalGross);
        double withholdingTax = computeWithholdingTax(totalGross);

        double deductions = sss + philHealth + pagIBIG + withholdingTax;
        double netSalary2 = gross2 - deductions;

        int lastDay = YearMonth.of(2024, month).lengthOfMonth();

        System.out.println("\n----- " + monthName(month) + " -----");

        System.out.println("\nCutoff Date: " + monthName(month) + " 1 to 15");
        System.out.println("Total Hours Worked: " + hours1[month]);
        System.out.println("Gross Salary: " + gross1);
        System.out.println("Net Salary: " + gross1);

        System.out.println("\nCutoff Date: " + monthName(month) + " 16 to " + lastDay);
        System.out.println("Total Hours Worked: " + hours2[month]);
        System.out.println("Gross Salary: " + gross2);
        System.out.println("SSS: " + sss);
        System.out.println("PhilHealth: " + philHealth);
        System.out.println("Pag-IBIG: " + pagIBIG);
        System.out.println("Withholding Tax: " + withholdingTax);
        System.out.println("Total Deductions: " + deductions);
        System.out.println("Net Salary: " + netSalary2);
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

    
