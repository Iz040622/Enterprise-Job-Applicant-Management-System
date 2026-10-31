/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Chin Jia Jun

package boundary;

import adt.*;
import entity.*;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author chinj
 */
public class ApplicationMaintenanceUI {
    static Scanner scanner = new Scanner(System.in);
    
    public static void displayApplicationListHeader(){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tApplication List" + "\033[0m\n\n\n\n\n\n");
    }
    
    //Display application list for applicant to view
    public static void applicantViewApplications(SetInterface<Application> applicationList, int startIndex, int endIndex){
        System.out.print("\033[11;0H\033[0J");      //Clear screen from row 11 column 0
        SetInterface<Application> pageApplication = applicationList.subset(applicationList.get(startIndex - 1), applicationList.get(endIndex - 1));     //To get a portion of the applications to display
                        
        System.out.printf("\t\t\t%s\t%-21s%-26s%s", "No.", "Company Name", "Job Title", "Status");
        System.out.println("\n\t\t\t--------------------------------------------------------------------------");
        
        Iterator<Application> iterator = pageApplication.getIterator();
        int count = startIndex;
        
        while (iterator.hasNext()){
            Application application = iterator.next();
            System.out.printf("\t\t\t%d.\t%-21s%-26s%s\n", count++, application.getMatching().getJob().getCompany(), application.getMatching().getJob().getJobTitle(), application.getStatus());
        }
    }
    
    //Display application list for employer to view
    public static void employerViewApplications(SetInterface<Application> applicationList, int startIndex, int endIndex){
        System.out.print("\033[11;0H\033[0J");      //Clear screen from row 11 column 0
        SetInterface<Application> pageApplication = applicationList.subset(applicationList.get(startIndex - 1), applicationList.get(endIndex - 1));     //To get a portion of the applications to display
                
        System.out.printf("\t\t\t%s\t%-21s%-26s%s", "No.", "Applicant Name", "Matching Score", "Status");
        System.out.println("\n\t\t\t--------------------------------------------------------------------------");
        
        Iterator<Application> iterator = pageApplication.getIterator();
        int count = startIndex;
        
        while (iterator.hasNext()){
            Application application = iterator.next();
            System.out.printf("\t\t\t%d.\t%-21s%-26s%s\n", count++, application.getMatching().getApplicant().getName(), String.format("%,.2f%%", application.getMatching().getScore()), application.getStatus());
        }
    }
    
    public static int getFilterChoice(int row, boolean valid){
        System.out.print(String.format("\033[%d;0H\033[0J", row + 15));
        System.out.println("\t\t\tFilter by (Based on your detail):");
        System.out.println("\t\t\t1. Field");
        System.out.println("\t\t\t2. Job Title");
        System.out.println("\t\t\t3. Address");
        System.out.println("\t\t\t4. Skill Requirements");
        System.out.println("\t\t\t5. Back");
        
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", row + 22));
                
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
    
    public static int getMaintenanceChoice(int row, boolean valid){
        System.out.print(String.format("\033[%d;0H\033[0J", row + 15));
        System.out.println("\t\t\t1. View Application Details");
        System.out.println("\t\t\t2. Next 10 Matchings");
        System.out.println("\t\t\t3. Previous 10 Matchings");
        System.out.println("\t\t\t4. Filter Matchings");
        System.out.println("\t\t\t5. Search Matching");
        System.out.println("\t\t\t6. Clear Filter/Search");
        System.out.println("\t\t\t7. Back");
        
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", row + 23));
                
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
    
    public static int selectApplication(int row, boolean valid){
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", row + 15));
                
                if (!valid) {                    
                    System.out.println("\033[31m" + "\t\t\tInvalid input! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tChoose Application: ");
                
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
    
    public static void displayApplicationDetailHeader(){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tApplication List" + "\033[0m\n\n\n\n\n\n");
    }
    
    public static void applicantViewApplicationDetail(Application application){
        System.out.print("\033[11;0H\033[0J");      //Clear screen from row 20 column 0
        System.out.println(application.toString() + "\n");
        JobMatchingServiceUI.displayMatchingDetail(application.getMatching().getApplicant(), application.getMatching());
    }
    
    public static void employerViewApplicationDetail(Application application){
        System.out.print("\033[11;0H\033[0J");      //Clear screen from row 20 column 0
        System.out.println(application.toString() + "\n");
        System.out.println("\t\t\tMatching Score: " + String.format("%,.2f%%", application.getMatching().getScore()));
        System.out.println("\n" + application.getMatching().getApplicant().toString());
    }
    
    public static int getApplicantApplicationDetailAction(Application application, boolean valid){
        System.out.print(String.format("\033[%d;0H\033[0J", application.getMatching().getJob().getMinSkillRequirement().size() + 26));
        System.out.println("\t\t\t1. Cancel Application");
        System.out.println("\t\t\t2. Back");
        
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", application.getMatching().getJob().getMinSkillRequirement().size() + 29));
                
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
    
    public static int getEmployerApplicationDetailAction(boolean valid){
        System.out.print(String.format("\033[%d;0H\033[0J", 38));
        System.out.println("\t\t\t1. Approve Application");
        System.out.println("\t\t\t2. Reject Application");
        System.out.println("\t\t\t3. Back");
        
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", 42));
                
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
    
    public static void getApplicationDetailProceedInput(){
        System.out.print("\n\n\n\t\t\tPress Enter to go back... ");
        scanner.nextLine();
    }
    
    public static char confirmAction(boolean valid, String action, int row) {
        System.out.print(String.format("\033[%d;0H\033[0J", row));

        if (!valid) {
            System.out.println("\033[31m" + "\t\t\tInvalid input! Please try again!" + "\033[0m");
        }
        System.out.print("\t\t\tAre you sure to " + action + " application? (Y/N): ");
        char confirm = scanner.nextLine().charAt(0);
        return Character.toUpperCase(confirm);
    }
}
