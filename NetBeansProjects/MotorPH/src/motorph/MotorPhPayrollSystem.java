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


        vv

        
    }
    
}
