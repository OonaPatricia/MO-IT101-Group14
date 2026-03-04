package motorph;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author patricesaints
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;


public class PayrollSystem {
    
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
            
        System.out.println("Incorrect username and/or password.");
        return;
        //Terminate Program
    }    
        //Login Successful
        System.out.println("Login successful!");
        
        sc.close();
        
    }
    
}
