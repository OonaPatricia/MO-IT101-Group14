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


public class MotorPhPayrollSystem {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        
        username = username.toLowerCase();
        
        //Credentials Validation
        if (!(username.equals("employee") || username.equals("payroll_staff")) 
                || !password.equals("12345")) {
            
           System.out.print("Incorrect username and/or password. "); 
           return;
        }
        
        //Login Successful
        System.out.println("Login successful!");
        
        sc.close();


        package motorph;

        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.IOException;
        import java.util.Scanner;

        public class ProcessPayRoll {
            static Scanner sc = new Scanner(System.in);

            public static void main(String[] args) {

                while (true) {

                    System.out.println("\nDisplay options:");
                    System.out.printil("1. Process Payroll");
                    System.out.printil("2. Exit the program");
                    System.out.print("Enter choice: ");

                    int choice = sc.nextInt();
                    sc.nextLine(); // clear buffer

                    if (choice ==1) {
                        processPayrollMenu();
                    } else if (choice == 2) }
                System.out.println("Program exited.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        static void processPayrollMenu() {

            while (true) {

                System.out.print("\nEnter employee number: ");
                String empInput = sc.nextLine();

                boolean found = false;

                try {
                    BufferedReader br = getReader();
                    String line; 
                    br.readLine(); // skip header

                    while ((line = br.readLine()) !=null) {

                        String[] data = splitCSV(line);

                        if (data[0].equals(empInput)){
                            displayPayroll(data);
                            found = true;
                            break;
                        }
                    }     

                    if (!found) {
                        System.out.printIn("Employee number does not exist.");
                    }

                } catch (IOException e) { 
                    System.out.println("Error: " + e.getMessege());
                }
            }

            static void processAllEmployees(){

                try {
                    BufferedReader br = getReader();
                    String line;
                    br.readLine(); // skip header

                    while ((line = br.readLine()) != null) {

                        String[] data = splitCSV(line);
                        displayPayroll(data);
                        System.out.println("--------------------------------------");
                    }

                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessege());
                }
            }
            static BufferedReader getReader() throws IOException {

                InputStream is = ProcessPayroll.class
                .getClassLoader()
                .getResourceAsStream("MotorPh_EmployeeData.csv");

            if (is == null) { 
                throw new IOException("CSV file not found.");
            }

            return new BufferedReader(new InputStreamReader(is));
        }

        static String[] splitCSV(String line) {
            return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    }

    static void displayPayroll(String[] data) {

        String empNumber = data[0];
        String lastName = data[1];
        String firstName = data [2];
        String birthday = data [3];    
        double hourlyRate = Double.parseDouble(data[18].replace("\"",""));

        double firstCutoffHours = 80;
        double secondCutoffHours = 85;

        double firstGross = firstCutoffHours * hourlyRate;
        double secondGross = secondCutoffHours * hourlyRate;

        double sss = 1000;
        double philhealth = 500;
        double pagibig = 200;
        double tax = 1500;

        double totalDeductions = sss + philhealth + pagibig + tax
        double secondNet = secondGross - totalDeductions

        System.out.println("\nEmployee #: " + empNumber);
        


            

        
    }
    
}
