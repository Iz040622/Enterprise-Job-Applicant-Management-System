/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import adt.*;
import entity.*;

/**
 *
 * @author chinj
 */
public class CourseInitializer {
    public SetInterface<Course> initializeCourses(){
        SetInterface<Course> courses = new TreeSet<>();
        
        //Software Engineering
        SetInterface<String> seSkill = new TreeSet<>();
        seSkill.add("Python");
        seSkill.add("Kotlin");
        seSkill.add("MySQL");
        seSkill.add("Linux");
        seSkill.add("HTML CSS JavaScript");
        
        SetInterface<String> seJobs = new TreeSet<>();
        seJobs.add("Software Engineer");
        seJobs.add("Software Developer");
        seJobs.add("Software Tester");
        
        courses.add(new Course("Software Engineering", "FOCS", "Computer Science", seSkill, seJobs));
        
        //Data Science
        SetInterface<String> dsSkill = new TreeSet<>();
        dsSkill.add("Python");
        dsSkill.add("Data Visualization");
        dsSkill.add("Machine Learning");
        dsSkill.add("MySQL");
        dsSkill.add("AWS");
        
        SetInterface<String> dsJobs = new TreeSet<>();
        dsJobs.add("Software Engineer");
        dsJobs.add("Data Analyst");
        dsJobs.add("Data Engineer");
        
        courses.add(new Course("Data Science", "FOCS", "Computer Science", dsSkill, dsJobs));
        
        //Accounting
        SetInterface<String> accSkill = new TreeSet<>();
        accSkill.add("Auditing");
        accSkill.add("Financial Reporting");
        accSkill.add("Bookkeeping");
        accSkill.add("Tax Knowledge");
        accSkill.add("Spreadsheet Proficiency");
        
        SetInterface<String> accJobs = new TreeSet<>();
        accJobs.add("Auditor");
        accJobs.add("Accountant");
        accJobs.add("Financial Analyst");
        
        courses.add(new Course("Accounting", "FAFB", "Business and Finance", accSkill, accJobs));
        
        //Finance and Investment
        SetInterface<String> fiSkill = new TreeSet<>();
        fiSkill.add("Financial Analysis & Modeling");
        fiSkill.add("Risk Management");
        fiSkill.add("Market Research and Forecasting");
        fiSkill.add("Corporate Finance Fundamentals");
        fiSkill.add("Spreadsheet Proficiency");
        
        SetInterface<String> fiJobs = new TreeSet<>();
        fiJobs.add("Investment Analyst");
        fiJobs.add("Risk Analyst");
        fiJobs.add("Financial Analyst");
        
        courses.add(new Course("Finance and Investment", "FAFB", "Business and Finance", fiSkill, fiJobs));
        
        //Bioscience with Chemistry
        SetInterface<String> cheSkill = new TreeSet<>();
        cheSkill.add("Spectrophotometry and Gel Electrophoresis");
        cheSkill.add("Safe Lab Practices and Chemical Handling");
        cheSkill.add("PCR and DNA/RNA Handling Techniques");
        cheSkill.add("Protein Purification and Analysis");
        cheSkill.add("Cell Culture Techniques");
        
        SetInterface<String> cheJobs = new TreeSet<>();
        cheJobs.add("Chemist");
        cheJobs.add("Biochemist");
        cheJobs.add("Biotechnologist");
        
        courses.add(new Course("Bioscience with Chemistry", "FOAS", "Science", cheSkill, cheJobs));
        
        return courses;
    }
}
