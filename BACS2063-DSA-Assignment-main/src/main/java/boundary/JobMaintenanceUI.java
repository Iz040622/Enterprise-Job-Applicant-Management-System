/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Lau Aik Hung

package boundary;

import adt.*;
import entity.*;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author chinj
 */
public class JobMaintenanceUI {
    static Scanner scanner = new Scanner(System.in);
    
    public static void displayJobsHeader(){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tJobs Created" + "\033[0m\n\n\n\n\n\n");
    }
    
    //Display all jobs
    public static void displayJobs(SetInterface<Job> jobList){
        System.out.print("\033[11;0H\033[0J");      //Clear screen from row 20 column 0

        System.out.printf("\t\t\t%s\t%-21s%-26s%s", "No.", "Company Name", "Field", "Job Title");
        System.out.println("\n\t\t\t--------------------------------------------------------------------------");
        
        Iterator<Job> jobIterator = jobList.getIterator();
        int count = 1;
        while (jobIterator.hasNext()){
            Job job = jobIterator.next();
            System.out.printf("\t\t\t%s\t%-21s%-26s%s%n", count++ + ". ", job.getCompany(), job.getField(), job.getJobTitle());
        } 
    }
    
    //Get job maintenance choice
    public static int getMaintenanceChoice(SetInterface<Job> jobList, boolean valid){
        System.out.print(String.format("\033[%d;0H\033[0J", jobList.size() + 15));
        System.out.println("\t\t\t1. View applications");
        System.out.println("\t\t\t2. View job details");
        System.out.println("\t\t\t3. Remove job");
        System.out.println("\t\t\t4. Back");

        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", jobList.size() + 20));
                
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
    
    //Choose job
    public static int getJobChoice(SetInterface<Job> jobList, boolean valid){
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", jobList.size() + 15));
                
                if (!valid) {                    
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tChoose job: ");
                
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
    
    public static void displaySpecificJobHeader(){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tJob Details" + "\033[0m\n\n\n\n\n\n");
    }
    
    //Display job details
    public static void displayJobDetails(Job job){
        System.out.print("\033[08;0H\033[0J");      //Clear screen from row 8 column 0
        System.out.println(job.toString());
    }
    
    //Get specific maintenance choice
    public static int getSpecificMaintenanceChoice(Job job, boolean valid){
        System.out.print(String.format("\033[%d;0H\033[0J", job.getMinSkillRequirement().size() + 18));
        System.out.println("\t\t\t1. View applications");
        System.out.println("\t\t\t2. Edit job");
        System.out.println("\t\t\t3. Remove job");
        System.out.println("\t\t\t4. Back");

        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", job.getMinSkillRequirement().size() + 23));
                
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
    
    //Get edit choice
    public static int getEditChoice(Job job, boolean valid){
        System.out.print(String.format("\033[%d;0H\033[0J", job.getMinSkillRequirement().size() + 18));
        System.out.println("\t\t\t1. Company Name");
        System.out.println("\t\t\t2. Address");
        System.out.println("\t\t\t3. Field");
        System.out.println("\t\t\t4. Job Title");
        System.out.println("\t\t\t5. Minimum Skill Requirement");
        System.out.println("\t\t\t6. Job Description");
        System.out.println("\t\t\t7. Salary Range");
        System.out.println("\t\t\t8. Cancel");

        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", job.getMinSkillRequirement().size() + 27));
                
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
    
    public static void displayCreateJobHeader(){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tCreate New Job" + "\033[0m\n\n\n\n\n\n");    
    }
    
    //Get new company name (for edit)
    public static String getNewCompany(){
        System.out.print("\t\t\tEnter company name (999 to cancel): ");
        String company = scanner.nextLine();

        return company;
    }
    
    //Get company name (for creating job)
    public static String getCompany(){
        System.out.print("\t\t\tEnter company name: ");
        String company = scanner.nextLine();

        return company;
    }
    
    //Display company name
    public static void displayCompany(String company){
        System.out.print("\033[08;0H\033[0J");      //Clear screen from row 8 column 0
        System.out.println("\t\t\tCompany: " + company + "\n\n\n");
    }
    
    //Get address
    public static Address getAddress(int row){
        System.out.print("\t\t\tEnter street address: ");
        String streetAddress = scanner.nextLine();
        
        System.out.print("\t\t\tEnter city: ");
        String city = scanner.nextLine();

        boolean validPostalCode = true;
        String postalCode;
        do{
            if (!validPostalCode){
                System.out.print(String.format("\033[%d;0H\033[0J", row));
                System.out.println("\033[31m" + "\t\t\tInvalid postal code! Please try again!" + "\033[0m");
            }
            
            System.out.print("\t\t\tEnter postal code: ");
            postalCode = scanner.nextLine();
            
            validPostalCode = postalCode.matches("^\\d{5}$");
        } while(!validPostalCode);
        
        System.out.print("\t\t\tEnter state: ");
        String state = scanner.nextLine();
        
        return new Address(streetAddress, city, postalCode, state);
    }
    
    //Display address
    public static void displayAddress(Address address){
        System.out.print("\033[09;0H\033[0J");      //Clear screen from row 20 column 0
        System.out.println("\t\t\tAddress: " + address + "\n\n\n");
    }
    
    //Get field
    public static int getField(SetInterface<String> fields, boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        
        Iterator<String> fieldIterator = fields.getIterator();
        int count = 1;
        while (fieldIterator.hasNext()){
            System.out.println("\t\t\t" + count++ + ". " + fieldIterator.next());
        } 
        
        System.out.println("");
        
        int option = 0;
        do{
            try{                
                if (!valid) {
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tChoose field: ");
                
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
    
    //Display field
    public static void displayField(String field){
        System.out.print("\033[10;0H\033[0J");      //Clear screen from row 20 column 0
        System.out.println("\t\t\tField: " + field + "\n\n\n");
    }
    
    //Get jobtitle
    public static int getJobTitle(SetInterface<String> jobTitles, boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        
        Iterator<String> jobTitlesIterator = jobTitles.getIterator();
        int count = 1;
        while (jobTitlesIterator.hasNext()){
            System.out.println("\t\t\t" + count++ + ". " + jobTitlesIterator.next());
        } 
        
        System.out.println("");
        
        int option = 0;
        do{
            try{
                if (!valid) {                    
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tChoose job title: ");
                
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
    
    //Dislay jobtitle
    public static void displayJobTitle(String jobTitle){
        System.out.print("\033[11;0H\033[0J");      //Clear screen from row 20 column 0
        System.out.println("\t\t\tJob Title: " + jobTitle + "\n\n\n");
    }
    
    //Display available skill requirement
    public static void displayAvailableSkillRequirement(SetInterface<String> skillSets, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", row));
        Iterator<String> skillSetsIterator = skillSets.getIterator();
        int count = 1;
        while (skillSetsIterator.hasNext()){
            System.out.println("\t\t\t" + count++ + ". " + skillSetsIterator.next());
        } 
    }
    
    //Get skill requirement
    public static int getSkillRequirement(SetInterface<String> skillSets, boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", skillSets.size() + row));
        
        int option = 0;
        do{
            try{
                if (!valid) {                    
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tChoose skill requirement: ");
                
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
    
    //Display selected skill requirement
    public static void displaySelectedSkill(String skill, SetInterface<String> skillSets, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", skillSets.size() + row));
        System.out.println("\t\t\tSelected skill: " + skill);
    }
    
    //Get minimum score for skill
    public static int getMinScore(SetInterface<String> skillSets, String skill, boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", skillSets.size() + row));
        
        int score = 0;
        do{
            try{
                if (!valid) {                    
                    System.out.println("\033[31m" + "\t\t\tInvalid score! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tEnter minimum score (1-5): ");
                
                valid = true;
                score = scanner.nextInt();
            }
            catch(Exception ex){
                scanner.nextLine();
                valid = false;
            }
        }while (!valid);
        
        scanner.nextLine();
        return score;
    }
    
    //Display entered score
    public static void displayEnteredScore(int score, SetInterface<String> skillSets, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", skillSets.size() + row));
        System.out.println("\t\t\tScore entered: " + score);
    }
    
    //Input for whether to choose more requirement
    public static char chooseMoreRequirement(SetInterface<String> skillSets, boolean valid, int row){
        System.out.print(String.format("\033[%d;0H\033[0J", skillSets.size() + row));
        if (!valid) {            
            System.out.println("\033[31m" + "\t\t\tInvalid input! Please try again!" + "\033[0m");
        }
        System.out.print("\t\t\tDo you want to specify more skill requirement? (Y/N): ");
        char confirm = scanner.nextLine().charAt(0);
        return confirm;
    }
    
    //Display minimum skill requirement
    public static void displayMinSkillRequirement(MapInterface<String, Integer> minSkillRequirement){
        System.out.print("\033[12;0H\033[0J");      //Clear screen from row 20 column 0
        System.out.println("\t\t\t[Minimum Skill Requirement]");
        
        Iterator<MapInterface.Entry<String, Integer>> iterator = minSkillRequirement.getIterator();
        int count = 1;
        while(iterator.hasNext()){
            MapInterface.Entry<String, Integer> pair = iterator.next();
            System.out.println("\t\t\t" + count++ + ". " + pair.getKey() + ": " + pair.getValue());
        }
        System.out.print("\n\n\n");
    }
    
    //Get description
    public static String getDescription(){
        System.out.print("\t\t\tEnter job description: ");
        String description = scanner.nextLine();
        return description;
    }
    
    //Display description
    public static void displayDescription(String description, MapInterface<String, Integer> minSkillRequirement){
        System.out.print(String.format("\033[%d;0H\033[0J", 13 + minSkillRequirement.size()));
        System.out.println("\t\t\tJob Description: " + description + "\n\n\n");
    }
    
    //Get minimum salary
    public static double getMinSalary(boolean valid, int row){
        double minSalary = 0;
        do{
            try{
                if (!valid) {
                    System.out.print(String.format("\033[%d;0H\033[0J", row));
                    System.out.println("\033[31m" + "\t\t\tInvalid minimum salary! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tEnter minimum salary: RM");
                
                valid = true;
                minSalary = scanner.nextDouble();
            }
            catch(Exception ex){
                scanner.nextLine();
                valid = false;
            }
        }while (!valid);
        
        scanner.nextLine();
        return minSalary;
    }
    
    //Get maximum salary
    public static double getMaxSalary(boolean valid, int row){
        double maxSalary = 0;
        do{
            try{
                if (!valid) {
                    System.out.print(String.format("\033[%d;0H\033[0J", row));
                    System.out.println("\033[31m" + "\t\t\tInvalid maximum salary! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tEnter maximum salary: RM");
                
                valid = true;
                maxSalary = scanner.nextDouble();
            }
            catch(Exception ex){
                scanner.nextLine();
                valid = false;
            }
        }while (!valid);
        
        scanner.nextLine();
        return maxSalary;
    }
    
    //Display salary range
    public static void displaySalaryRange(double[] salaryRange, MapInterface<String, Integer> minSkillRequirement){
        System.out.print(String.format("\033[%d;0H\033[0J", 14 + minSkillRequirement.size()));
        System.out.println("\t\t\tSalary Range: RM" + String.format("%,.2f", salaryRange[0]) + " ~ RM" + String.format("%,.2f", salaryRange[1]));
    }
    
    //Input for confirming action
    public static char confirmAction(Job job, boolean valid) {
        System.out.print(String.format("\033[%d;0H\033[0J", job.getMinSkillRequirement().size() + 18));

        if (!valid) {
            System.out.println("\033[31m" + "\t\t\tInvalid input! Please try again!" + "\033[0m");
        }
        System.out.print("\t\t\tAre you sure to remove job? (Y/N): ");
        char confirm = scanner.nextLine().charAt(0);
        return Character.toUpperCase(confirm);
    }
}
