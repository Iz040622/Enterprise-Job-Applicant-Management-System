/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Lau Aik Hung

package control;

import adt.*;
import boundary.*;
import entity.*;
import utility.*;
import java.util.Iterator;

/**
 *
 * @author chinj
 */
public class JobMaintenance {
    private SetInterface<Job> jobList;
    private SetInterface<Course> courses;

    public JobMaintenance(SetInterface<Job> jobList, SetInterface<Course> courses) {
        this.jobList = jobList;
        this.courses = courses;
    }
    
    public Job runJobMaintenance(){
        while(true){
            JobMaintenanceUI.displayJobsHeader();
            
            //For empty jobList
            if (jobList.isEmpty()){
                EmptyADTHandling.displayEmptyMessage("Job List");
                return null;
            }
            
            //Display jobs
            JobMaintenanceUI.displayJobs(jobList);
            
            //Obtain maintenance option
            boolean valid = true;
            int option;
            do{
                option = JobMaintenanceUI.getMaintenanceChoice(jobList, valid);
                valid = validateMaintenanceOption(option);
            } while(!valid);
            
            //Back
            if (option == 4)
                return null;
            
            //Select job
            Job job = selectJob();
            
            switch(option){
                //View applications
                case 1:
                    return job;
                
                //View job details
                case 2:
                OUTER:
                while (true) {
                    JobMaintenanceUI.displaySpecificJobHeader();
                    //Display job details
                    JobMaintenanceUI.displayJobDetails(job);
                    
                    //Obtain specific maintenance option
                    boolean specificValid = true;
                    int specificOption;
                    do {
                        specificOption = JobMaintenanceUI.getSpecificMaintenanceChoice(job, specificValid);
                        specificValid = validateMaintenanceOption(specificOption);
                    } while (!specificValid);
                    
                    switch (specificOption) {
                        //Back
                        case 4:
                            break OUTER;
                        //View application
                        case 1:
                            return job;
                        //Edit job
                        case 2:
                            //Obtain edit choice
                            boolean editValid = true;
                            int editOption;
                            do {
                                editOption = JobMaintenanceUI.getEditChoice(job, editValid);
                                editValid = validateEditOption(editOption);
                            } while (!editValid);
                            if (editOption == 8) {
                                break;
                            }
                            editJob(job, editOption);
                            break;
                        //Delete job
                        default:
                            boolean deleteValid = true;
                            char confirmDelete;

                            do {
                                confirmDelete = JobMaintenanceUI.confirmAction(job, deleteValid);
                                deleteValid = validateConfirm(confirmDelete);
                            } while (!deleteValid);

                            if (confirmDelete == 'Y') {
                                deleteJob(job);
                                break OUTER;
                            }
                            break;
                    }
                }
                    break;

                //Delete job
                case 3:                    
                    boolean deleteValid = true;
                    char confirmDelete;

                    do {
                        confirmDelete = JobMaintenanceUI.confirmAction(job, deleteValid);
                        deleteValid = validateConfirm(confirmDelete);
                    } while (!deleteValid);
                    
                    if (confirmDelete == 'Y')
                        deleteJob(job);
                    break;
            }
        }
    }
    
    //Validate maintenance option
    public boolean validateMaintenanceOption(int option){ 
        return option > 0 && option <= 4;
    }
    
    //Select job
    public Job selectJob(){
        int option;
        boolean valid = true;
        do{
            option = JobMaintenanceUI.getJobChoice(jobList, valid);
            valid = true;
            
            if (option == 999)
                return null;
            
            if (option < 1 || option > jobList.size())
                valid = false;
        } while(!valid);
        
        return jobList.get(option - 1);
    }
    
    //Validate specific maintenance option
    public boolean validateSpecificMaintenanceOption(int option){ 
        return option > 0 && option <= 3;
    }
    
    //Validate edit option
    public boolean validateEditOption(int option){ 
        return option > 0 && option <= 8;
    }
    
    //Create job
    public void createJob(){
        JobMaintenanceUI.displayCreateJobHeader();
        
        //Get company name
        String company;
        do{
            company = JobMaintenanceUI.getNewCompany();
        } while (!StringValidation.validateString(company));
        
        if (company.equals("999"))
            return;
        JobMaintenanceUI.displayCompany(company);
        
        //Get address
        Address address;
        do{
            address = JobMaintenanceUI.getAddress(14);
        } while (!StringValidation.validateString(address.getStreetAddress()) || !StringValidation.validateString(address.getCity()) || !StringValidation.validateString(address.getState()));
        JobMaintenanceUI.displayAddress(address);
        
        //Get field
        String field = selectField(courses, 15);
        JobMaintenanceUI.displayField(field);
        
        //Get jobtitle
        String jobTitle = selectJobTitle(field, 15);
        JobMaintenanceUI.displayJobTitle(jobTitle);
        
        //Get minimum skill requirement
        MapInterface<String, Integer> minSkillRequirement = getMinSkillRequirements(field, jobTitle, 14);
        JobMaintenanceUI.displayMinSkillRequirement(minSkillRequirement);
       
        //Get description
        String description;
        do {
            description = JobMaintenanceUI.getDescription();
        } while (!StringValidation.validateString(description));
        JobMaintenanceUI.displayDescription(description, minSkillRequirement);

        //Get salary range
        double[] salaryRange = getSalaryRangeArray(20 + minSkillRequirement.size());
        JobMaintenanceUI.displaySalaryRange(salaryRange, minSkillRequirement);
        
        //Create job and add into jobList
        Job job = new Job(company, address, field, jobTitle, minSkillRequirement, description, salaryRange);
        jobList.add(job);
    } 
    
    //Edit job
    public void editJob(Job job, int editOption){
        System.out.print(String.format("\033[%d;0H\033[0J", job.getMinSkillRequirement().size() + 18));
        
        switch(editOption){
            //Edit company name
            case 1:
                String company;
                do{
                    company = JobMaintenanceUI.getCompany();
                } while (!StringValidation.validateString(company));
                
                job.setCompany(company);
                break;
                
            //Edit address
            case 2:
                Address address;
                do{
                    address = JobMaintenanceUI.getAddress(22);
                } while (!StringValidation.validateString(address.getStreetAddress()) || !StringValidation.validateString(address.getCity()) || !StringValidation.validateString(address.getState()));

                job.setAddress(address);
                break;
                
            //Edit field, jobtitle & minimum skill requirement
            case 3:
                String field = selectField(courses, job.getMinSkillRequirement().size() + 18);
                job.setField(field);
                //Continue asking for job title
                
            //Edit jobtitle & minimum skill requirement
            case 4:
                String jobTitle = selectJobTitle(job.getField(), job.getMinSkillRequirement().size() + 18);
                job.setJobTitle(jobTitle);
                //Continue asking for skill requirements
                
            //Edit minimum skill requirement
            case 5:
                MapInterface<String, Integer> minSkillRequirement = getMinSkillRequirements(job.getField(), job.getJobTitle(), job.getMinSkillRequirement().size() + 18);
                job.setMinSkillRequirement(minSkillRequirement);
                break;
                
            //Edit description
            case 6:
                String description;
                do{
                    description = JobMaintenanceUI.getDescription();
                } while (!StringValidation.validateString(description));
                
                job.setDescription(description);
                break;
                
            //Edit salary range
            case 7:
                double[] salaryRange = getSalaryRangeArray(job.getMinSkillRequirement().size() + 18);
                job.setSalaryRange(salaryRange);
                break;                
        }
        
        //Update score
        ApplicationMaintenance applicationMaintenance = new ApplicationMaintenance(job.getApplicationList());
        applicationMaintenance.updateApplicationScores();
    }
    
    //Select field
    public String selectField(SetInterface<Course> courses, int row){
        //Get all existing field from courses
        Iterator<Course> courseIterator = courses.getIterator();
        SetInterface<String> fields = new TreeSet<>();
        while (courseIterator.hasNext()){
            fields.add(courseIterator.next().getField());
        } 
        
        boolean valid = true;
        int option;
        do{
            option = JobMaintenanceUI.getField(fields, valid, row);
            valid = true;
            
            if (option < 1 || option > fields.size())
                valid = false;
        } while(!valid);
        
        return fields.get(option - 1);
    }
    
    //Select job title
    public String selectJobTitle(String field, int row){
        SetInterface<Course> filteredCourse = new TreeSet<>();
        
        //Extract course for the relevant field
        Iterator<Course> courseIterator = courses.getIterator();
        while (courseIterator.hasNext()){
            Course nextCourse = courseIterator.next();
            if (nextCourse.getField().equals(field))
                filteredCourse.add(nextCourse);
            else if (!filteredCourse.isEmpty() && !nextCourse.getField().equals(field))
                break;
        }
        
        //Extract jobtitle from the course
        Iterator<Course> filteredCourseIterator = filteredCourse.getIterator();
        SetInterface<String> jobTitles = new TreeSet<>();
        while (filteredCourseIterator.hasNext()){
            jobTitles = (TreeSet<String>) jobTitles.union(filteredCourseIterator.next().getPotentialJob());
        }
        
        boolean valid = true;
        int option;

        do {
            option = JobMaintenanceUI.getJobTitle(jobTitles, valid, row);
            valid = true;

            if (option < 1 || option > jobTitles.size()) {
                valid = false;
            }

        } while (!valid);
        
        return jobTitles.get(option - 1);
    }
    
    //Get minimum skill requirement
    public MapInterface<String, Integer> getMinSkillRequirements(String field, String jobTitle, int row){
        MapInterface<String, Integer> minSkillRequirement = new HashMap<>();
        
        //Extract course for the relevant field
        SetInterface<Course> filteredCourse = new TreeSet<>();
        Iterator<Course> courseIterator = courses.getIterator();
        while (courseIterator.hasNext()){
            Course nextCourse = courseIterator.next();
            if (nextCourse.getField().equals(field))
                filteredCourse.add(nextCourse);
            else if (!filteredCourse.isEmpty() && !nextCourse.getField().equals(field))
                break;
        }
        
        //Extract skillsets from the course
        Iterator<Course> filteredCourseIterator = filteredCourse.getIterator();
        SetInterface<String> skillSets = new TreeSet<>();
        while (filteredCourseIterator.hasNext()){
            Course nextCourse = filteredCourseIterator.next();
            if (nextCourse.getPotentialJob().contains(jobTitle))
                skillSets = (TreeSet<String>) skillSets.union(nextCourse.getSkillSet());
        }
        
        int count = 1;
        char confirm = 'Y';
        while (count <= 5 && confirm == 'Y'){
            JobMaintenanceUI.displayAvailableSkillRequirement(skillSets, row);
            
            int option;
            boolean valid = true;
            do{
                option = JobMaintenanceUI.getSkillRequirement(skillSets, valid, row + 1);
                valid = true;
                
                if (option < 1 || option > skillSets.size())
                    valid = false;
            } while(!valid);
            
            String skill = skillSets.get(option - 1);
            JobMaintenanceUI.displaySelectedSkill(skill, skillSets, row + 1);
            
            int score;
            do{
                score = JobMaintenanceUI.getMinScore(skillSets, skill, valid, row + 2);
                valid = true;
                
                if (score < 1 || score > 5)
                    valid = false;
            } while(!valid);
            
            JobMaintenanceUI.displayEnteredScore(score, skillSets, row + 2);
            minSkillRequirement.put(skill, score);
            
            do{
                confirm = Character.toUpperCase(JobMaintenanceUI.chooseMoreRequirement(skillSets, valid, row + 3));
                valid = true;
                
                if (confirm != 'Y' && confirm != 'N')
                    valid = false;
            } while (!valid);
            
            skillSets.remove(skill);
            count++;
        }
        return minSkillRequirement;
    }
    
    //Validate minimum salary
    public boolean validateMinSalary(double minSalary){
        return minSalary > 0;
    }
    
    //Validate maximum salary
    public boolean validateMaxSalary(double minSalary, double maxSalary){
        return maxSalary > minSalary;
    }
    
    //Get salary range
    public double[] getSalaryRangeArray(int row){
        boolean valid = true;
        double minSalary;
        double maxSalary;
        do{
            minSalary = JobMaintenanceUI.getMinSalary(valid, row);
            valid = validateMinSalary(minSalary);
        }while (!valid);
        
        do{
            maxSalary = JobMaintenanceUI.getMaxSalary(valid, row);
            valid = validateMaxSalary(minSalary, maxSalary);
        }while (!valid);
        
        return new double[]{minSalary, maxSalary};
    }
    
    //Delete job
    public void deleteJob(Job job){
        jobList.remove(job);    //Remove job from joblist
        ApplicationMaintenance applicationMaintenance = new ApplicationMaintenance(job.getApplicationList());
        applicationMaintenance.removeJobApplications();     //Remove application from aplicant application
        SuccessMessageUI.successMessage("Delete");
    }
    
    //Validate confimation input
    public boolean validateConfirm(char confirm){
        return confirm == 'Y' || confirm == 'N';
    }
}
