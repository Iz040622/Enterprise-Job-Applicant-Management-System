/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.*;
import java.util.Iterator;
/**
 *
 * @author chinj
 */
public class Job implements Comparable<Job>{
    private String company;
    private Address address;
    private String field;
    private String jobTitle;
    private MapInterface<String, Integer> minSkillRequirement;
    private String description;
    private double[] salaryRange;
    private SetInterface<Application> applicationList;

    public Job(String company, Address address, String field, String jobTitle, MapInterface<String, Integer> minSkillRequirement, String description, double[] salaryRange) {
        this(company, address, field, jobTitle, minSkillRequirement, description, salaryRange, new TreeSet<>());
    }
    
    public Job(String company, Address address, String field, String jobTitle, MapInterface<String, Integer> minSkillRequirement, String description, double[] salaryRange, SetInterface<Application> applicationList) {
        this.company = company;
        this.address = address;
        this.field = field;
        this.jobTitle = jobTitle;
        this.minSkillRequirement = minSkillRequirement;
        this.description = description;
        this.salaryRange = salaryRange;
        this.applicationList = applicationList;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public MapInterface<String, Integer> getMinSkillRequirement() {
        return minSkillRequirement;
    }

    public void setMinSkillRequirement(MapInterface<String, Integer> minSkillRequirement) {
        this.minSkillRequirement = minSkillRequirement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double[] getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(double[] salaryRange) {
        this.salaryRange = salaryRange;
    }

    public SetInterface<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(SetInterface<Application> applicationList) {
        this.applicationList = applicationList;
    }

    @Override
    public String toString() {
        String strMinSkillRequirement = "";
        Iterator<MapInterface.Entry<String, Integer>> iterator = minSkillRequirement.getIterator();
        int count = 1;
        while(iterator.hasNext()){
            MapInterface.Entry<String, Integer> pair = iterator.next();
            strMinSkillRequirement += ("\n\t\t\t" + count++ + ". " + pair.getKey() + ": " + pair.getValue());
        }
        
        return "\t\t\tCompany: " + company 
                + "\n\t\t\tAddress: " + address 
                + "\n\t\t\tField: " + field 
                + "\n\t\t\tJob Title: " + jobTitle 
                + "\n\t\t\t[Minimum Skill Requirements]" + strMinSkillRequirement 
                + "\n\t\t\tDescription: " + description 
                + "\n\t\t\tMinimum Salary: RM" + String.format("%,.2f", salaryRange[0]) 
                + "\n\t\t\tMaximum Salary: RM" + String.format("%,.2f", salaryRange[1])
                + "\n\t\t\tApplications: " + applicationList.size();
    }
    
    
    
    @Override
    public int compareTo(Job anotherJob) {
        if (field != null && anotherJob.field != null){
            int cmpField = field.compareTo(anotherJob.field);
            if (cmpField != 0)
                return cmpField;
        }
        
        if (jobTitle != null && anotherJob.jobTitle != null){
            int cmpJobTitle = jobTitle.compareTo(anotherJob.jobTitle);
            if (cmpJobTitle != 0)
                return cmpJobTitle;
        }
        
        if (company != null && anotherJob.company != null){
            int cmpCompany = company.compareTo(anotherJob.company);
            if (cmpCompany != 0)
                return cmpCompany;
        }
        
        if (address != null && anotherJob.address != null){
            int cmpAddress = address.compareTo(anotherJob.address);
            if (cmpAddress != 0)
                return cmpAddress;
        }
        
        if (minSkillRequirement != null && anotherJob.minSkillRequirement != null) {
            int thisTotal = 0;
            int anotherTotal = 0;
            
            SetInterface<Integer> thisValues = minSkillRequirement.getValues();
            SetInterface<Integer> anotherValues = anotherJob.minSkillRequirement.getValues();
            
            Iterator<Integer> thisIterator = thisValues.getIterator();
            while (thisIterator.hasNext())
                thisTotal += (int) thisIterator.next();
            
            Iterator<Integer> anotherIterator = anotherValues.getIterator();
            while (anotherIterator.hasNext())
                anotherTotal += (int) anotherIterator.next();
            
            if (thisTotal > anotherTotal)
                return 1;
            else if (thisTotal > anotherTotal)
                return -1;
        }
        
        if (description != null && anotherJob.description != null){
            int cmpDescription = description.compareTo(anotherJob.description);
            if (cmpDescription != 0)
                return cmpDescription;
        }
        
        if (salaryRange != null && anotherJob.salaryRange != null && salaryRange.length == 2){
            if (salaryRange[0] != anotherJob.salaryRange[0])
                return (salaryRange[0] > anotherJob.salaryRange[0]) ? 1 : -1;
            else if (salaryRange[1] != anotherJob.salaryRange[1])
                return (salaryRange[1] > anotherJob.salaryRange[1]) ? 1 : -1;
        }
        
        if (applicationList != null && anotherJob.applicationList != null) {
            if (!applicationList.equals(anotherJob.applicationList) && (applicationList.size() != 0 && anotherJob.applicationList.size() != 0)) {
                return (applicationList.size() > anotherJob.applicationList.size()) ? 1 : -1;
            }
        }
        
        return 0;
    }
}
