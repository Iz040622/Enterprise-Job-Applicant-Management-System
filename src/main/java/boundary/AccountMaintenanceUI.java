/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Lau Aik Hung (Employer), Isaac Lim Tian Le (Applicant)

package boundary;

import entity.*;
import java.util.Scanner;

/**
 *
 * @author chinj
 */
public class AccountMaintenanceUI {
    
    static Scanner scanner = new Scanner(System.in);
    
    //Method to display account details
    public static void viewAccount(User user){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tAccount Details" + "\033[0m\n\n\n");
        
        if (user instanceof Applicant applicant) {
            System.out.println(applicant.toString());
        } 
        else if (user instanceof Employer employer) {
            System.out.println(employer.toString());
        }
    }
    
    //Method to get maintenance option
    public static int getAccountAction(boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        System.out.println("\t\t\t1. Edit Account");
        System.out.println("\t\t\t2. Delete Account");
        System.out.println("\t\t\t3. Back");

        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", row + 4));
                
                if (!valid) {                    
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tEnter option: ");
                
                valid = true;
                option = scanner.nextInt();
            }
            catch(Exception ex){
                scanner.nextLine();
                valid = false;
            }
        }while (!valid);
        
        scanner.nextLine();
        return option;
    }
    
    //Method to obtain info choice applicant want to edit
    public static int getApplicantEditChoice(boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        System.out.println("\t\t\t1. Username");
        System.out.println("\t\t\t2. Password");
        System.out.println("\t\t\t3. Name");
        System.out.println("\t\t\t4. Address");
        System.out.println("\t\t\t5. Course");
        System.out.println("\t\t\t6. Softskill");
        System.out.println("\t\t\t7. Skill Set");
        System.out.println("\t\t\t8. CGPA");
        System.out.println("\t\t\t9. Cancel\n");
        
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", row + 10));
                
                if (!valid) {                    
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tChoose edit option: ");
                
                valid = true;
                option = scanner.nextInt();
            }
            catch(Exception ex){
                scanner.nextLine();
                valid = false;
            }
        }while (!valid);
        
        scanner.nextLine();
        return option;
    }
    
    //Method to obtain info choice employer want to edit
    public static int getEmployerEditChoice(User user, boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        System.out.println("\t\t\t1. Username");
        System.out.println("\t\t\t2. Password");
        System.out.println("\t\t\t3. Cancel");
        
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", row + 4));
                
                if (!valid) {                    
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tChoose edit option: ");
                
                valid = true;
                option = scanner.nextInt();
            }
            catch(Exception ex){
                scanner.nextLine();
                valid = false;
            }
        }while (!valid);
        
        scanner.nextLine();
        return option;
    }
    
    //Method to get confirmation input
    public static char confirmAction(boolean valid, int row) {
        System.out.print(String.format("\033[%d;0H\033[0J", row));

        if (!valid) {
            System.out.println("\033[31m" + "\t\t\tInvalid input! Please try again!" + "\033[0m");
        }
        System.out.print("\t\t\tAre you sure to delete account? (Y/N): ");
        char confirm = scanner.nextLine().charAt(0);
        return Character.toUpperCase(confirm);
    }
}
