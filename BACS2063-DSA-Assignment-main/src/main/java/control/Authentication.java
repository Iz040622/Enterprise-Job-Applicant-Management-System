/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Lau Aik Hung (Employer), Isaac Lim Tian Le (Applicant)

package control;

import adt.*;
import entity.*;
import boundary.*;
import utility.*;
import java.util.Iterator;

/**
 *
 * @author chinj
 */
public class Authentication {
    
    private SetInterface<Applicant> applicantList;
    private SetInterface<Employer> employerList;
    private SetInterface<Course> courses;
    private SetInterface<String> softskills;

    public Authentication(SetInterface<Applicant> applicantList, SetInterface<Employer> employerList, SetInterface<Course> courses, SetInterface<String> softskills) {
        this.applicantList = applicantList;
        this.employerList = employerList;
        this.courses = courses;
        this.softskills = softskills;
    }
    
    public User runAuthentication() {
        
        while (true){
            int option = AuthenticationUI.getMenuChoice();          //Main menu
            
            switch (option) {
                case 1:             //Login
                    User user = login();
                    if (user != null)
                        return user;
                    break;
                    
                case 2:             //Register
                    register();
                    break;
                default:            //Exit Program
                    return null;
            }
        }
    }
    
    //Method for login
    public User login(){
        User user = null;
        boolean validUsername = true;
        boolean validPassword = true;

        AuthenticationUI.displayLoginHeader();
        
        //Get and check username
        do {
            String username = AuthenticationUI.getLoginUsername(validUsername);

            if (username.equals("999")) //User choose to cancel login
                return null;

            user = validateLoginUsername(username);  
            validUsername = (user != null);
            
        } while (!validUsername);
        
        //Get and check password
        do {
            String password = AuthenticationUI.getLoginPassword(validPassword);

            validPassword = validateLoginPassword(user, password);     
        } while (!validPassword);
        
        System.out.println("");
        SuccessMessageUI.successMessage("Login");
        return user;
    }
    
    //Method to validate login username 
    public User validateLoginUsername(String username){
        User user;
        
        Iterator<Applicant> applicantIterator = applicantList.getIterator();
        while (applicantIterator.hasNext()){
            user = applicantIterator.next();
            if (username.equals(user.getUsername())) {
                return user;  
            }
        }

        Iterator<Employer> employerIterator = employerList.getIterator();
        while (employerIterator.hasNext()){
            user = employerIterator.next();
            if (username.equals(user.getUsername())) {
                return user;  
            }
        }
        return null;
    }
    
    //Method to validate login password 
    public boolean validateLoginPassword(User user, String password){
        return password.equals(user.getPassword());
    }
    
    //Method for register
    public void register(){
        AuthenticationUI.displayRegisterHeader();
        
        //Choose role
        boolean roleValid = true;
        int roleOption;
        
        do{
            roleOption = AuthenticationUI.selectRole(roleValid);
            
            if (roleOption == 999)
                return;
            else if (roleOption != 1 && roleOption != 2)
                roleValid = false;
            
        } while(!roleValid);
        
        AuthenticationUI.displaySelectedRole(roleOption);

        String username;
        String password;
        
        //Get and valdate username
        boolean validUsername = true;
        do {       
            username = AuthenticationUI.getRegisterUsername(validUsername, 15);
            validUsername = validateRegisterUsername(username, roleOption);
        } while (!validUsername);
        
        AuthenticationUI.displayRegisterUsername(username);
        
        //Get and valdate password
        boolean validPassword = true;
        do {
            password = AuthenticationUI.getRegisterPassword(validPassword, 15);
            validPassword = validateRegisterPassword(password);
        } while (!validPassword);
        
        AuthenticationUI.displayRegisterPassword(password);
        
        switch(roleOption){
            //For applicant
            case 1:
                //Get name
                String name;
                do{
                    name = AuthenticationUI.getNewApplicantName(15);
                } while (!StringValidation.validateString(name));
                AuthenticationUI.displayName(name);
                
                //Get address
                Address address;
                do{
                    address = AuthenticationUI.getNewApplicantAddress(15);
                } while (!StringValidation.validateString(address.getStreetAddress()) || !StringValidation.validateString(address.getCity()) || !StringValidation.validateString(address.getState()));

                AuthenticationUI.displayAddress(address);
                
                //Get faculty
                String faculty = selectFaculty(17);
                AuthenticationUI.displayFaculty(faculty);
                
                //Get course
                Course course = selectCourse(faculty, 17);
                AuthenticationUI.displayCourse(course);
                
                //Get cgpa
                boolean validCgpa = true;
                double cgpa; 
                do{
                    cgpa = AuthenticationUI.getNewApplicantCgpa(validCgpa, 18);
                    validCgpa = validateCgpa(cgpa);
                } while(!validCgpa);
                AuthenticationUI.displayCgpa(cgpa);
                
                //Get softskill
                MapInterface<String, Integer> softskill = rateSoftskill(19);
                
                //Get skillset
                MapInterface<String, Integer> skillSet = rateSkillSet(course.getSkillSet(), 26);
                
                //Create applicant and add into applicantList
                Applicant applicant = new Applicant(username, password, name, address, course, softskill, skillSet, cgpa);
                applicantList.add(applicant);
                break;
                
            case 2:
                //Create employer and add into employerList
                Employer employer = new Employer(username, password);
                employerList.add(employer);
                break;
        }
        
        SuccessMessageUI.successMessage("Register");
    }
    
    //Validate register username
    public boolean validateRegisterUsername(String username, int roleOption){
        String usernamePattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).+$";
        if (!username.matches(usernamePattern))
            return false;
        
        if (roleOption == 1 && applicantList.contains(new Applicant(username, null, null, null, null, null, null, -1))) {
            return false;
        }
        else if (roleOption == 2 && employerList.contains(new Employer(username, null, null))) {
            return false;
        }

        return true;
    }
    
    //Validate register password
    public boolean validateRegisterPassword(String password){
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).+$";
        return password.matches(passwordPattern);
    }
    
    //Choose faculty
    public String selectFaculty(int row) {
        SetInterface<String> faculties = new TreeSet<>();
        Iterator<Course> coursesIterator = courses.getIterator();
        while (coursesIterator.hasNext()) {
            faculties.add(coursesIterator.next().getFaculty());
        }

        boolean facultyValid = true;
        int facultyOption;

        do {
            facultyOption = AuthenticationUI.getNewApplicantFaculty(faculties, facultyValid, row);
            facultyValid = true;

            if (facultyOption < 1 || facultyOption > faculties.size()) {
                facultyValid = false;
            }

        } while (!facultyValid);
        
        return faculties.get(facultyOption - 1);
    }
    
    //Choose course
    public Course selectCourse(String faculty, int row){
        SetInterface<Course> filteredCourse = new TreeSet<>();

        Iterator<Course> courseIterator = courses.getIterator();
        while (courseIterator.hasNext()){
            Course nextCourse = courseIterator.next();
            if (nextCourse.getFaculty().equals(faculty))
                filteredCourse.add(nextCourse);
            else if (!filteredCourse.isEmpty() && !nextCourse.getField().equals(faculty))
                break;
        }
        boolean courseValid = true;
        int courseOption;

        do {
            courseOption = AuthenticationUI.getNewApplicantCourse(filteredCourse, courseValid, row);
            courseValid = true;

            if (courseOption < 1 || courseOption > filteredCourse.size()) {
                courseValid = false;
            }

        } while (!courseValid);
        
        return filteredCourse.get(courseOption - 1);
    }
    
    //Validate cgpa
    public boolean validateCgpa(double cgpa){
        return (cgpa >= 0 && cgpa <= 4);
    }
    
    //Get softskill
    public MapInterface<String, Integer> rateSoftskill(int row){
        MapInterface<String, Integer> softskill = new HashMap<>();
        
        Iterator<String> softskillIterator = softskills.getIterator();
        int num = 1;
        while (softskillIterator.hasNext()) {
            String softskillName = softskillIterator.next();
            int score;
            boolean valid = true;
            
            do{
                score = AuthenticationUI.getNewApplicantSoftskill(softskillName, num, valid, row);    
                valid = true;
                
                if (score < 1 || score > 5)
                    valid = false;
            } while(!valid);
            
            softskill.put(softskillName, score);
            num++;
        }
        
        return softskill;
    }
    
    //Get skillset based on course
    public MapInterface<String, Integer> rateSkillSet(SetInterface<String> skillSet, int row){
        MapInterface<String, Integer> skillSetMap = new HashMap<>();
        
        Iterator<String> skillSetIterator = skillSet.getIterator();
        int num = 1;
        while (skillSetIterator.hasNext()) {
            String skillSetName = skillSetIterator.next();
            int score;
            boolean valid = true;
            
            do{
                score = AuthenticationUI.getNewApplicantSkillSet(skillSetName, num, valid, row);    
                valid = true;
                
                if (score < 1 || score > 5)
                    valid = false;
            } while(!valid);
            
            skillSetMap.put(skillSetName, score);
            num++;
        }
        
        return skillSetMap;
    }
}
