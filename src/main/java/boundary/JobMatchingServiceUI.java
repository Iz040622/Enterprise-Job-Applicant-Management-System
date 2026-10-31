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
public class JobMatchingServiceUI {
    static Scanner scanner = new Scanner(System.in);
    
    public static void displayJobsHeader(){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tMatchings" + "\033[0m\n\n\n\n\n\n");
    }
    
    //Display matching list
    public static void displayMatches(SetInterface<Matching> matchings, int startIndex, int endIndex){
        System.out.print("\033[11;0H\033[0J");      //Clear screen from row 11 column 0
        SetInterface<Matching> pageMatching = matchings.subset(matchings.get(startIndex - 1), matchings.get(endIndex - 1));
        
        System.out.printf("\t\t\t%s\t%-21s%-26s%s", "No.", "Company Name", "Job Title", "Matching Percentage");
        System.out.println("\n\t\t\t--------------------------------------------------------------------------");
        
        Iterator<Matching> iterator = pageMatching.getIterator();
        int count = startIndex;
        while(iterator.hasNext()){
            Matching matching = iterator.next();
            System.out.printf("\t\t\t%d.\t%-21s%-26s%8.2f%%%n", count++, matching.getJob().getCompany(), matching.getJob().getJobTitle(), matching.getScore());
        }
    }
    
    //Get matching service option
    public static int getMatchingServiceOption(int row, boolean valid){
        System.out.print(String.format("\033[%d;0H\033[0J", row + 15));
        System.out.println("\t\t\t1. View Matching Details");
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
    
    //Choose matching
    public static int getMatchingChoice(int row, boolean valid){
        int option = 0;
        do{
            try{
                System.out.print(String.format("\033[%d;0H\033[0J", row + 15));
                
                if (!valid) {                    
                    System.out.println("\033[31m" + "\t\t\tInvalid option! Please try again!" + "\033[0m");
                }
                
                System.out.print("\t\t\tChoose matching: ");
                
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
    
    public static void displayMatchingDetailHeader(){
        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\t\t\t\tMatching Details" + "\033[0m\n\n\n\n\n\n");
    }
    
    //Display matching details (compare job requirement with applicant detail, display whether it matches)
    public static void displayMatchingDetail(Applicant applicant, Matching matching){
        System.out.println("\t\t\tMatching Score: " + String.format("%,.2f%%", matching.getScore()));
        
        System.out.println("\n\t\t\tCompany: " + matching.getJob().getCompany());
        
        Address address = matching.getJob().getAddress();
        System.out.printf("\t\t\t%-60s", "Address: " + address);
        if (address.getCity().equals(applicant.getAddress().getCity()) && address.getState().equals(applicant.getAddress().getState()))
            System.out.print("\033[32m" + "\tMatch" + "\033[0m");
        else if (address.getCity().equals(applicant.getAddress().getCity()))
            System.out.print("\033[32m" + "\tMatch City" + "\033[0m");
        else if (address.getState().equals(applicant.getAddress().getState()))
            System.out.print("\033[32m" + "\tMatch State" + "\033[0m");
        else
            System.out.print("\033[31m" + "\tNot Match" + "\033[0m");
        
        System.out.printf("\n\t\t\t%-60s", "Field: " + matching.getJob().getField());
        if (matching.getJob().getField().equals(applicant.getCourse().getField()))
            System.out.print("\033[32m" + "\tMatch" + "\033[0m");
        else
            System.out.print("\033[31m" + "\tNot Match" + "\033[0m");
        
        System.out.printf("\n\t\t\t%-60s", "Job Title: " + matching.getJob().getJobTitle());
        if (applicant.getCourse().getPotentialJob().contains(matching.getJob().getJobTitle()))
            System.out.print("\033[32m" + "\tMatch" + "\033[0m");
        else
            System.out.print("\033[31m" + "\tNot Match" + "\033[0m");

        System.out.print("\n\t\t\t[Minimum Skill Requirements]");
        MapInterface<String, Integer> minSkillRequirement = matching.getJob().getMinSkillRequirement();
        SetInterface<String> skills = minSkillRequirement.getKeys();
        int count = 1;
        
        Iterator<String> skillsIterator = skills.getIterator();
        while (skillsIterator.hasNext()) {
            String skill = skillsIterator.next();
            
            System.out.printf("\n\t\t\t%-60s", count++ + ". " + skill + ": " + minSkillRequirement.get(skill));
            if (applicant.getSkillSet().containsKey(skill)){
                if (applicant.getSkillSet().get(skill) >= minSkillRequirement.get(skill))
                    System.out.print("\033[32m" + "\tMatch" + "\033[0m");
                else
                    System.out.print("\033[31m" + "\tNot Match" + "\033[0m");
            }
        }

        System.out.println("\n\t\t\tJob Description: " + matching.getJob().getDescription());
        
        System.out.println("\t\t\tSalary Range: RM" + String.format("%,.2f", matching.getJob().getSalaryRange()[0]) + " ~ RM" + String.format("%,.2f", matching.getJob().getSalaryRange()[1]));
    }
    
    //Get detail matching option
    public static int getDetailMatchingOption(Matching matching, boolean valid){
        System.out.print(String.format("\033[%d;0H\033[0J", 24 + matching.getJob().getMinSkillRequirement().size()));
        
        System.out.println("\t\t\t1. Apply Job");
        System.out.println("\t\t\t2. Back\n");
        
        int option = 0;
        do{
            try{
                if (!valid) {        
                    System.out.print(String.format("\033[%d;0H\033[0J", 26 + matching.getJob().getMinSkillRequirement().size()));
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
    
    //GEt filter choice
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
}
