/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import adt.*;

/**
 *
 * @author chinj
 */
public class Employer extends User implements Comparable<Employer>{
    private SetInterface<Job> jobList;

    public Employer(String username, String password) {
        this(username, password, new TreeSet<>());
    }
    
    public Employer(String username, String password, SetInterface<Job> jobList) {
        super(username, password);
        this.jobList = jobList;
    }

    public SetInterface<Job> getJobList() {
        return jobList;
    }

    public void setJobList(SetInterface<Job> jobList) {
        this.jobList = jobList;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int compareTo(Employer anotherEmployer){
        if (getUsername() != null) {
            int cmpUsername = getUsername().compareTo(anotherEmployer.getUsername());
            if (cmpUsername != 0) {
                return cmpUsername;
            }
        }

        if (getPassword() != null) {
            int cmpPassword = getPassword().compareTo(anotherEmployer.getPassword());
            if (cmpPassword != 0) {
                return cmpPassword;
            }
        }
        
        if (jobList != null) {
            if (!jobList.equals(anotherEmployer.jobList)) {
                return (jobList.size() > anotherEmployer.jobList.size()) ? 1 : -1;
            }
        }
        
        return 0;   //Returns 0 if its same or some/all null (for filtering purpose)
    }
}
