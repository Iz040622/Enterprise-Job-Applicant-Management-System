/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Lau Aik Hung (Employer), Isaac Lim Tian Le (Applicant)

package boundary;

import java.util.Scanner;
import entity.*;
import adt.*;
import java.util.Iterator;

/**
 *
 * @author chinj
 */
public class AuthenticationUI {
    static Scanner scanner = new Scanner(System.in);
    
    public static int getMenuChoice() {
        boolean inputValid = true;
        int option = 0;
        
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\tTARUMT Internship Application System" + "\033[0m\n\n\n\n\n\n\n");
        
        System.out.println("\n\n\t\t\t1. Login" 
                            + "\n\t\t\t2. Register"
                            + "\n\t\t\t3. Exit Program");
        
        do{
            try{
                System.out.print("\033[20;0H\033[0J");      //Clear screen from row 20 column 0
                
                if(!inputValid)
                    System.out.println("\033[31m" + "\t\t\tInvalid input! Must be 1-3 only!" + "\033[0m");      //MessageUI.displayInvalidChoiceMessage()
                System.out.print("\t\t\tEnter your option (1-3): ");
            
                inputValid = true;
            
                option = scanner.nextInt();
                if (option < 1 || option > 3)
                    inputValid = false;           
            }
            
            catch(Exception ex){
                scanner.nextLine();
                inputValid = false;
            }

        }while (!inputValid);
        
        scanner.nextLine();
        
        return option;
    }
    
    public static void displayLoginHeader(){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tLogin" + "\033[0m\n\n\n\n\n\n\n");    
    }
    
    //Get login username
    public static String getLoginUsername(boolean valid){
        System.out.print("\033[15;0H\033[0J");      //Clear screen from row 20 column 0
        
        if (!valid){
            System.out.print("\033[14;0H\033[0J");
            System.out.println("\033[31m" + "\t\t\tInvalid username! Please try again!" + "\033[0m");
        }
        System.out.print("\t\t\tEnter username (999 to cancel): ");
        String username = scanner.nextLine();
        
        System.out.print("\033[14;0H\033[2K"); //clear the line
        
        return username;
    }
    
    //Get login password
    public static String getLoginPassword(boolean valid){
        System.out.print("\033[18;0H\033[0J");      //Clear screen from row 23 column 0
        
        if (!valid){
            System.out.print("\033[17;0H\033[0J");
            System.out.println("\033[31m" + "\t\t\tInvalid password! Please try again!" + "\033[0m");
        }
        System.out.print("\t\t\tEnter password: ");
        String password = scanner.nextLine();
        
        System.out.print("\033[17;0H\033[2K"); //clear the line
        
        return password;
    }
    
    public static void displayRegisterHeader(){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tRegister" + "\033[0m\n\n\n\n\n\n\n");    
    }
    
    //Get role
    public static int selectRole(boolean valid){
        int option = 0;
        do {
            try {
                System.out.print("\033[15;0H\033[0J");      //Clear screen from row 15 column 0

                if (!valid) {
                    System.out.print("\033[14;0H\033[0J");
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }

                System.out.println("\t\t\t1. Applicant");
                System.out.println("\t\t\t2. Employer");
                System.out.print("\n\t\t\tChoose your role (999 to cancel): ");
                
                valid = true;
                option = scanner.nextInt();

                System.out.print("\033[14;0H\033[2K"); //clear the line
            } 
            
            catch (Exception ex) {
                scanner.nextLine();
                valid = false;
            }
        } while (!valid);
        
        scanner.nextLine();
        
        return option;
    }
    
    //Display chosen role
    public static void displaySelectedRole(int option){
        System.out.print("\033[09;0H\033[0J");      //Clear screen from row 9 column 0
        
        System.out.print("\t\t\tSelected Role: ");
        if (option == 1)
            System.out.println("Applicant");
        else
            System.out.println("Employer");
    }
    
    //Get register username
    public static String getRegisterUsername(boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        
        if (!valid){
            System.out.print(String.format("\033[%d;0H\033[0J", row - 1));
            System.out.println("\033[31m" + "\t\t\tInvalid username or Username already chosen! Please try again!" + "\033[0m");
        }
        System.out.print("\t\t\tEnter username (Must include uppercase, number and symbol): ");
        String username = scanner.nextLine();
        
        System.out.print(String.format("\033[%d;0H\033[2K", row - 1));
        
        return username;
    }
    
    //Display register username
    public static void displayRegisterUsername(String username){
        System.out.print("\033[11;0H\033[0J");      //Clear screen from row 11 column 0
        
        System.out.print("\t\t\tUsername: " + username);
    }
    
    //Get register password
    public static String getRegisterPassword(boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        
        if (!valid){
            System.out.print(String.format("\033[%d;0H\033[0J", row - 1));
            System.out.println("\033[31m" + "\t\t\tInvalid password! Please try again!" + "\033[0m");
        }
        System.out.print("\t\t\tEnter password (Must include uppercase, number and symbol): ");
        String password = scanner.nextLine();
        
        System.out.print(String.format("\033[%d;0H\033[2K", row - 1));
        
        return password;
    }
    
    //Display register password
    public static void displayRegisterPassword(String password){
        System.out.print("\033[12;0H\033[0J");      //Clear screen from row 12 column 0
        
        System.out.print("\t\t\tPassword: " + password);
    }
    
    //Get name
    public static String getNewApplicantName(int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        
        System.out.print("\t\t\tEnter your name: ");
        String name = scanner.nextLine();
        
        return name;
    }
    
    //Display name
    public static void displayName(String name){
        System.out.print("\033[13;0H\033[0J");      //Clear screen from row 13 column 0    
        System.out.print("\t\t\tName: " + name);
    }
    
    //Get address
    public static Address getNewApplicantAddress(int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        System.out.print("\t\t\tEnter your street address: ");
        String streetAddress = scanner.nextLine();
        
        System.out.print("\t\t\tEnter your city: ");
        String city = scanner.nextLine();

        boolean validPostalCode = true;
        String postalCode;
        do{
            System.out.print(String.format("\033[%d;0H\033[0J", row + 2));

            if (!validPostalCode){
                System.out.println("\033[31m" + "\t\t\tInvalid postal code! Please try again!" + "\033[0m");
            }
            
            System.out.print("\t\t\tEnter your postal code: ");
            postalCode = scanner.nextLine();
            
            validPostalCode = postalCode.matches("^\\d{5}$");
        } while(!validPostalCode);
        
        System.out.print("\t\t\tEnter your state: ");
        String state = scanner.nextLine();
        
        return new Address(streetAddress, city, postalCode, state);
    }
    
    //Display address
    public static void displayAddress(Address address){
        System.out.print("\033[14;0H\033[0J");      //Clear screen from row 14 column 0    
        System.out.print("\t\t\tAddress: " + address);
    }
    
    //Get faculty
    public static int getNewApplicantFaculty(SetInterface<String> faculties, boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        
        Iterator<String> facultiesIterator = faculties.getIterator();
        int count = 1;
        while (facultiesIterator.hasNext()){
            System.out.println("\t\t\t" + count++ + ". " + facultiesIterator.next());
        }
        
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", row + 1 + faculties.size()));
                
                if (!valid) {
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tEnter your faculty: ");
                
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
    
    //Display chosen faculty
    public static void displayFaculty(String faculty){
        System.out.print("\033[15;0H\033[0J");      //Clear screen from row 20 column 0    
        System.out.print("\t\t\tFaculty: " + faculty);
    }
    
    //Get course
    public static int getNewApplicantCourse(SetInterface<Course> filteredCourses, boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row)); 
        
        Iterator<Course> coursesIterator = filteredCourses.getIterator();
        int count = 1;
        while (coursesIterator.hasNext()){
            System.out.println("\t\t\t" + count++ + ". " + coursesIterator.next().getCourseName());
        }
        
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", row + 1 + filteredCourses.size()));
                
                if (!valid) {
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tEnter your course: ");
                
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
    
    //Display chosen course
    public static void displayCourse(Course course){
        System.out.print("\033[16;0H\033[0J");      //Clear screen from row 20 column 0    
        System.out.print("\t\t\tCourse: " + course.getCourseName());
    }
    
    //Get cgpa
    public static double getNewApplicantCgpa(boolean valid, int row){
        double cgpa = 0;

        do {
            try {
                System.out.print(String.format("\033[%d;0H\033[0J", row));
                
                if (!valid)
                    System.out.println("\033[31m" + "\t\t\tInvalid CGPA! Please try again!" + "\033[0m");
                System.out.print("\t\t\tEnter your CGPA: ");

                valid = true;
                cgpa = scanner.nextDouble();
            } catch (Exception ex) {
                scanner.nextLine();
                valid = false;
            }
        } while (!valid);

        scanner.nextLine();

        return cgpa;
    }
    
    //Display cgpa
    public static void displayCgpa(double cgpa){
        System.out.print("\033[17;0H\033[0J");      //Clear screen from row 20 column 0    
        System.out.print("\t\t\tCGPA: " + cgpa);
    }
    
    //Get rate for softskiil
    public static int getNewApplicantSoftskill(String softskill, int num, boolean valid, int row){ 
        int score = 0;
        
        if (num == 1){
            System.out.print(String.format("\033[%d;0H\033[0J", row));
            System.out.println("\t\t\t[Softskill] How would you rate your");
        }
        
        do {
            try {
                if (!valid) {
                    System.out.print(String.format("\033[%d;0H\033[0J", row + num));
                    System.out.print("\033[31m" + "\t\t\tInvalid option! " + "\033[0m");
                }
                else
                    System.out.print("\t\t\t");

                System.out.print(num + ". " + softskill + " (1-5): ");

                valid = true;
                score = scanner.nextInt();
            } catch (Exception ex) {
                scanner.nextLine();
                valid = false;
            }
        } while (!valid);
        
        scanner.nextLine();
        
        return score;
    }
    
    //Get rate for skillset
    public static int getNewApplicantSkillSet(String skillSet, int num, boolean valid, int row){
        int score = 0;
        
        if (num == 1){
            System.out.print(String.format("\033[%d;0H\033[0J", row));
            System.out.println("\t\t\t[Skill Set] How would you rate your");
        }
        
        do {
            try {
                if (!valid) {
                    System.out.print(String.format("\033[%d;0H\033[0J", row + num));
                    System.out.print("\033[31m" + "\t\t\tInvalid option! " + "\033[0m");
                }
                else
                    System.out.print("\t\t\t");

                System.out.print(num + ". " + skillSet + " (1-5): ");

                valid = true;
                score = scanner.nextInt();
            } catch (Exception ex) {
                scanner.nextLine();
                valid = false;
            }
        } while (!valid);
        
        scanner.nextLine();
        
        return score;
    }
}
