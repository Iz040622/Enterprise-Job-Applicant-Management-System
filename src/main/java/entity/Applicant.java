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
public class Applicant extends User implements Comparable<Applicant>{
    private String name;
    private Address address;
    private Course course;
    private MapInterface<String, Integer> softskill;
    private MapInterface<String, Integer> skillSet;
    private double cgpa;
    private SetInterface<Application> applicationList;

    public Applicant(String username, String password, String name, Address address, Course course, MapInterface<String, Integer> softskill, MapInterface<String, Integer> skillSet, double cgpa) {
        this(username, password, name, address, course, softskill, skillSet, cgpa, new TreeSet<>());    
    }
    
    public Applicant(String username, String password, String name, Address address, Course course, MapInterface<String, Integer> softskill, MapInterface<String, Integer> skillSet, double cgpa, SetInterface<Application> applicationList) {
        super(username, password);
        this.name = name;
        this.address = address;
        this.course = course;
        this.softskill = softskill;
        this.skillSet = skillSet;
        this.cgpa = cgpa;
        this.applicationList = applicationList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    public MapInterface<String, Integer> getSoftskill() {
        return softskill;
    }

    public void setSoftskill(MapInterface<String, Integer> softskill) {
        this.softskill = softskill;
    }
    
    public MapInterface<String, Integer> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(MapInterface<String, Integer> skillSet) {
        this.skillSet = skillSet;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public SetInterface<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(SetInterface<Application> applicationList) {
        this.applicationList = applicationList;
    }

    @Override
    public String toString() {
        String strSoftskill = "";
        Iterator<MapInterface.Entry<String, Integer>> softskillIterator = softskill.getIterator();
        int count = 1;
        while(softskillIterator.hasNext()){
            MapInterface.Entry<String, Integer> pair = softskillIterator.next();
            strSoftskill += ("\n\t\t\t" + count++ + ". " + pair.getKey() + ": " + pair.getValue());
        }
        
        String strSkillSet = "";
        Iterator<MapInterface.Entry<String, Integer>> skillSetIterator = skillSet.getIterator();
        count = 1;
        while(skillSetIterator.hasNext()){
            MapInterface.Entry<String, Integer> pair = skillSetIterator.next();
            strSkillSet += ("\n\t\t\t" + count++ + ". " + pair.getKey() + ": " + pair.getValue());
        }
        
        return super.toString() + "\n\t\t\tName: " + name + "\n\t\t\tAddress: " + address 
                + "\n\t\t\tCourse: " + course.getCourseName() 
                + "\n\t\t\t[Softskill]" + strSoftskill 
                + "\n\t\t\t[SkillSet]" + strSkillSet 
                + "\n\t\t\tCgpa: " + cgpa + "\n\t\t\tApplications: " + applicationList.size();
    }
    
    @Override
    public int compareTo(Applicant anotherApplicant){
        if (getUsername() != null && anotherApplicant.getUsername() != null) {
            int cmpUsername = getUsername().compareTo(anotherApplicant.getUsername());
            if (cmpUsername != 0) {
                return cmpUsername;
            }
        }

        if (getPassword() != null && anotherApplicant.getPassword() != null) {
            int cmpPassword = getPassword().compareTo(anotherApplicant.getPassword());
            if (cmpPassword != 0) {
                return cmpPassword;
            }
        }

        if (name != null && anotherApplicant.getName() != null) {
            int cmpName = name.compareTo(anotherApplicant.name);
            if (cmpName != 0) {
                return cmpName;
            }
        }

        if (address != null && anotherApplicant.address != null) {
            int cmpAddress = address.compareTo(anotherApplicant.address);
            if (cmpAddress != 0) {
                return cmpAddress;
            }
        }

        if (course != null && anotherApplicant.course != null) {
            int cmpCourse = course.compareTo(anotherApplicant.course);
            if (cmpCourse != 0) {
                return cmpCourse;
            }
        }

        if (softskill != null && anotherApplicant.softskill != null) {
            int thisTotal = 0;
            int anotherTotal = 0;
            
            SetInterface<Integer> thisValues = softskill.getValues();
            SetInterface<Integer> anotherValues = anotherApplicant.softskill.getValues();
            
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
        
        if (skillSet != null && anotherApplicant.skillSet != null) {
            int thisTotal = 0;
            int anotherTotal = 0;
            
            SetInterface<Integer> thisValues = skillSet.getValues();
            SetInterface<Integer> anotherValues = anotherApplicant.skillSet.getValues();
            
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

        if (cgpa != -1 && cgpa != anotherApplicant.cgpa) {
            return (cgpa > anotherApplicant.cgpa) ? 1 : -1;
        }

        if (applicationList != null && anotherApplicant.applicationList != null) {
            if (!applicationList.equals(anotherApplicant.applicationList) && (applicationList.size() != 0 && anotherApplicant.applicationList.size() != 0)) {
                return (applicationList.size() > anotherApplicant.applicationList.size()) ? 1 : -1;
            }
        }
        
        return 0;   //Returns 0 if its same or some/all null (for filtering purpose)
    }
}
