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
public class ApplicantInitializer {
    public SetInterface<Applicant> initializeApplicants(){
        SetInterface<Applicant> applicantList = new TreeSet<>();
        
        CourseInitializer courseInitializer = new CourseInitializer();
        //application pending
        
        //Applicant 1 (Software Engineering)
        MapInterface<String, Integer> softskill1 = new HashMap<>();
        softskill1.put("Problem Solving", 4);
        softskill1.put("Teamwork & Collaboration", 4);
        softskill1.put("Time Management", 3);
        softskill1.put("Adaptability & Willingness to Learn", 3);
        softskill1.put("Presentation & Reporting Skills", 3);
        
        MapInterface<String, Integer> skillSet1 = new HashMap<>();
        skillSet1.put("Python", 4);
        skillSet1.put("Kotlin", 4);
        skillSet1.put("MySQL", 2);
        skillSet1.put("Linux", 2);
        skillSet1.put("HTML CSS JavaScript", 3);
        
        applicantList.add(new Applicant("JJChin1!", "JJChin1!", "Jia Jun", new Address("21, Taman Midah", "Kuala Lumpur", "56000", "Kuala Lumpur"), courseInitializer.initializeCourses().get(2), softskill1, skillSet1, 4, new TreeSet<>()));
        
        //Applicant 2 (Data Science)
        MapInterface<String, Integer> softskill2 = new HashMap<>();
        softskill2.put("Problem Solving", 3);
        softskill2.put("Teamwork & Collaboration", 5);
        softskill2.put("Time Management", 2);
        softskill2.put("Adaptability & Willingness to Learn", 3);
        softskill2.put("Presentation & Reporting Skills", 5);
        
        MapInterface<String, Integer> skillSet2 = new HashMap<>();
        skillSet2.put("Python", 3);
        skillSet2.put("Data Visualization", 3);
        skillSet2.put("Machine Learning", 3);
        skillSet2.put("MySQL", 2);
        skillSet2.put("AWS", 3);
        
        applicantList.add(new Applicant("Roy$6", "Roy$6", "Roy", new Address("10, Kampung Melayu Subang", "Shah Alam", "40150", "Selangor"), courseInitializer.initializeCourses().get(1), softskill2, skillSet2, 3, new TreeSet<>()));
        
        //Applicant 3 (Accounting)
        MapInterface<String, Integer> softskill3 = new HashMap<>();
        softskill3.put("Problem Solving", 4);
        softskill3.put("Teamwork & Collaboration", 4);
        softskill3.put("Time Management", 4);
        softskill3.put("Adaptability & Willingness to Learn", 3);
        softskill3.put("Presentation & Reporting Skills", 3);
        
        MapInterface<String, Integer> skillSet3 = new HashMap<>();
        skillSet3.put("Auditing", 3);
        skillSet3.put("Financial Reporting", 3);
        skillSet3.put("Bookkeeping", 3);
        skillSet3.put("Tax Knowledge", 2);
        skillSet3.put("Spreadsheet Proficiency", 3);
        
        applicantList.add(new Applicant("AH123!", "AH123!", "Aik Hung", new Address("22, Taman Danau Kota", "Kuala Lumpur", "53000", "Kuala Lumpur"), courseInitializer.initializeCourses().get(0), softskill3, skillSet3, 3.5, new TreeSet<>()));
        
        return applicantList;
    }
}
