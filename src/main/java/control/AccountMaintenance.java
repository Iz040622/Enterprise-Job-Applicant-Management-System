/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Lau Aik Hung (Employer), Isaac Lim Tian Le (Applicant)

package control;

import adt.*;
import boundary.*;
import entity.*;
import java.util.Iterator;
import utility.*;

/**
 *
 * @author chinj
 */
public class AccountMaintenance {
    
    private User user;
    private SetInterface<Applicant> applicantList;
    private SetInterface<Employer> employerList;
    private SetInterface<Course> courses;
    private SetInterface<String> softskills;
    private Authentication authentication;      //To access the methods

    //For applicant
    public AccountMaintenance(User user, SetInterface<Course> courses, SetInterface<Applicant> applicantList, SetInterface<String> softskills, Authentication authentication) {
        this.user = user;
        this.courses = courses;
        this.applicantList = applicantList;
        this.softskills = softskills;
        this.authentication = authentication;
    }

    //For employer
    public AccountMaintenance(User user, SetInterface<Course> courses, SetInterface<Employer> employerList, Authentication authentication) {
        this.user = user;
        this.courses = courses;
        this.employerList = employerList;
        this.authentication = authentication;
    }

    public User runAccountMaintenance(){
        while (true){
            //Display account details
            AccountMaintenanceUI.viewAccount(user);
            
            //Obtain maintenance option
            boolean actionValid = true;
            int actionOption;
            do{
                actionOption = AccountMaintenanceUI.getAccountAction(actionValid, user instanceof Applicant ? 25 + ((Applicant)user).getSkillSet().size() : 12);
                actionValid = validateAccountAction(actionOption);
            }while (!actionValid);  
            
            //Back
            if (actionOption == 3)
                return user;
            //Edit account
            else if (actionOption == 1)
                editAccount();
            //Delete account
            else{
                boolean deleteValid = true;
                char confirmDelete;

                do {
                    confirmDelete = AccountMaintenanceUI.confirmAction(deleteValid, user instanceof Applicant ? 25 + ((Applicant) user).getSkillSet().size() : 12);
                    deleteValid = validateConfirm(confirmDelete);
                } while (!deleteValid);

                if (confirmDelete == 'Y') {
                    deleteAccount();
                }
                return null;
            }
        }
    }
    
    //Method to validate maintenance option
    public boolean validateAccountAction(int option){
        return option > 0 && option <= 3;
    }
    
    //Method to edit account
    public void editAccount(){
        if (user instanceof Applicant){
            //Choose info to edit
            int editOption;
            boolean valid = true;
            do{
                editOption = AccountMaintenanceUI.getApplicantEditChoice(valid, 25 + ((Applicant)user).getSkillSet().size());
                valid = validateApplicantEditChoice(editOption);
            } while(!valid);
            
            if (editOption == 9)
                return;
            
            editApplicantAccount(editOption);
        }
        else{
            //Choose info to edit
            int editOption;
            boolean valid = true;
            do{
                editOption = AccountMaintenanceUI.getEmployerEditChoice(user, valid, 12);
                valid = validateEmployerEditChoice(editOption);
            } while(!valid);
            
            if (editOption == 3)
                return;
            
            editEmployerAccount(editOption);
        }
    }
    
    //Method to edit applicant account
    public void editApplicantAccount(int option){
        switch (option){
            case 1:
                boolean usernameValid = true;
                String username;
                do{
                    username = AuthenticationUI.getRegisterUsername(usernameValid, 30);
                    usernameValid = authentication.validateRegisterUsername(username, 1);
                } while(!usernameValid);
                
                ((Applicant)user).setUsername(username);
                break;
                
            case 2:
                boolean passwordValid = true;
                String password;
                do{
                    password = AuthenticationUI.getRegisterUsername(passwordValid, 30);
                    passwordValid = authentication.validateRegisterPassword(password);
                } while(!passwordValid);
                
                ((Applicant)user).setPassword(password);
                break;
                
            case 3:
                String name;
                do{
                    name = AuthenticationUI.getNewApplicantName(30);
                } while (!StringValidation.validateString(name));
                
                ((Applicant)user).setName(name);
                break;
                
            case 4:
                Address address;
                do{
                    address = AuthenticationUI.getNewApplicantAddress(30);
                } while (!StringValidation.validateString(address.getStreetAddress()) || !StringValidation.validateString(address.getCity()) || !StringValidation.validateString(address.getState()));
                ((Applicant)user).setAddress(address);
                break;
                
            case 5:
                String faculty = authentication.selectFaculty(30);
                Course course = authentication.selectCourse(faculty, 30);
                
                ((Applicant)user).setCourse(course);
                //Continue to skillset
                
            case 7:
                MapInterface<String, Integer> skillSet = authentication.rateSkillSet(((Applicant)user).getCourse().getSkillSet(), 30);
                ((Applicant)user).setSkillSet(skillSet);
                break;
                
            case 6:
                MapInterface<String, Integer> softskill = authentication.rateSoftskill(30);
                ((Applicant)user).setSoftskill(softskill);
                break;
                
            case 8:
                boolean validCgpa = true;
                double cgpa; 
                do{
                    cgpa = AuthenticationUI.getNewApplicantCgpa(validCgpa, 30);
                    validCgpa = authentication.validateCgpa(cgpa);
                } while(!validCgpa); 
                
                ((Applicant)user).setCgpa(cgpa);
                break;
        }
        
        //Update application score
        ApplicationMaintenance applicationMaintenance = new ApplicationMaintenance(((Applicant)user).getApplicationList());
        applicationMaintenance.updateApplicationScores();
    }
    
    //Method to edit employer account
    public void editEmployerAccount(int option){
        switch (option){
            case 1:
                boolean usernameValid = true;
                String username;
                do{
                    username = AuthenticationUI.getRegisterUsername(usernameValid, 30);
                    usernameValid = authentication.validateRegisterUsername(username, 1);
                } while(!usernameValid);
                
                ((Employer)user).setUsername(username);
                break;
                
            case 2:
                boolean passwordValid = true;
                String password;
                do{
                    password = AuthenticationUI.getRegisterUsername(passwordValid, 30);
                    passwordValid = authentication.validateRegisterPassword(password);
                } while(!passwordValid);
                
                ((Employer)user).setPassword(password);
                break;
        }
    }
    
    //Method to validate applicant input for choosing info to edit
    public boolean validateApplicantEditChoice(int option){
        return option > 0 && option <= 9;
    }
    
    //Method to validate employer input for choosing info to edit
    public boolean validateEmployerEditChoice(int option){
        return option > 0 && option <= 3;
    }
    
    //Method to delete account
    public void deleteAccount(){        
        if (user instanceof Applicant){
            applicantList.remove((Applicant) user);     //Remove applicant from appicantList
            ApplicationMaintenance applicationMaintenance = new ApplicationMaintenance(((Applicant) user).getApplicationList());
            applicationMaintenance.removeApplicantApplications();       //Remove applicant's applications
        }
        else{
            employerList.remove((Employer) user);       //Remove employer from appicantList
            Iterator<Job> jobIterator = ((Employer) user).getJobList().getIterator();

            //Remove job's applications in applicant applicationLists
            while (jobIterator.hasNext()) {
                ApplicationMaintenance applicationMaintenance = new ApplicationMaintenance(jobIterator.next().getApplicationList());
                applicationMaintenance.removeApplicantApplications();
            }
        }
                
        SuccessMessageUI.successMessage("Delete");
    }
    
    //Method to validate confirmation input
    public boolean validateConfirm(char confirm){
        return confirm == 'Y' || confirm == 'N';
    }
}
