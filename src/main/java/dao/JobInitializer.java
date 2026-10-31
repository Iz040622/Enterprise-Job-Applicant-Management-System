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
public class JobInitializer {
    public SetInterface<Job> initializeJobs(){
        SetInterface<Job> jobs = new TreeSet<>();
        //Application pending
        
        //12) Software Engineer
        MapInterface<String, Integer> minSkillRequirement1 = new HashMap<>();
        minSkillRequirement1.put("Python", 3);
        minSkillRequirement1.put("HTML CSS JavaScript", 3);
        jobs.add(new Job("Intel", new Address("12, Jalan Pasar", "Pudu", "56000", "Kuala Lumpur"), "Computer Science", "Software Engineer", minSkillRequirement1, "Join our team to build and improve software solutions alongside experienced engineers.", new double[] {1000, 1200}, new TreeSet<>()));
        
        //11) Software Developer
        MapInterface<String, Integer> minSkillRequirement2 = new HashMap<>();
        minSkillRequirement2.put("Kotlin", 3);
        minSkillRequirement2.put("Linux", 2);
        jobs.add(new Job("iFast", new Address("97, Jalan Marof", "Bangsar", "59100", "Kuala Lumpur"), "Computer Science", "Software Developer", minSkillRequirement2, "Offers hands-on experience in coding, debugging, and building real-world applications in a collaborative tech environment.", new double[] {1100, 1200}, new TreeSet<>()));
        
        //6) Data Analyst
        MapInterface<String, Integer> minSkillRequirement3 = new HashMap<>();
        minSkillRequirement3.put("Python", 3);
        minSkillRequirement3.put("Data Visualization", 3);
        minSkillRequirement3.put("MySQL", 2);
        jobs.add(new Job("Oracle", new Address("24, Taman Matahari", "Kuala Selangor", "53000", "Selangor"), "Computer Science", "Data Analyst", minSkillRequirement3, "Join our team to analyze data, uncover insights, and support data-driven decisions.", new double[] {800, 1200}, new TreeSet<>()));
        
        //7) Data Engineer
        MapInterface<String, Integer> minSkillRequirement4 = new HashMap<>();
        minSkillRequirement4.put("Python", 3);
        minSkillRequirement4.put("Data Visualization", 2);
        minSkillRequirement4.put("MySQL", 3);
        jobs.add(new Job("Oracle", new Address("12, Taman Sri Setia", "Johor Bahru", "80300", "Johor"), "Computer Science", "Data Engineer", minSkillRequirement4, "Provides hands-on experience in building data pipelines, optimizing data workflows, and working with big data tools in a real-world tech environment.", new double[] {800, 1200}, new TreeSet<>()));
        
        //2) Auditor
        MapInterface<String, Integer> minSkillRequirement5 = new HashMap<>();
        minSkillRequirement5.put("Auditing", 3);
        minSkillRequirement5.put("Spreadsheet Proficiency", 3);
        jobs.add(new Job("Deloitte", new Address("24, Taman Melati", "Wangsa Maju", "53000", "Kuala Lumpur"), "Business and Finance", "Auditor", minSkillRequirement5, "Gain hands-on experience in auditing processes, internal controls, and financial reporting.", new double[] {800, 1000}, new TreeSet<>()));
        
        //1) Accountant
        MapInterface<String, Integer> minSkillRequirement6 = new HashMap<>();
        minSkillRequirement6.put("Tax Knowledge", 3);
        minSkillRequirement6.put("Bookkeeping", 3);
        jobs.add(new Job("Jefrey & Co", new Address("99, Jalan Gombak", "Setapak", "53000", "Kuala Lumpur"), "Business and Finance", "Accountant", minSkillRequirement6, "Assist in preparing financial reports, analyzing account discrepancies, and ensuring compliance with accounting standards and procedures.", new double[] {900, 1100}, new TreeSet<>()));
        
        //8) Financial Analyst
        MapInterface<String, Integer> minSkillRequirement7 = new HashMap<>();
        minSkillRequirement7.put("Financial Reporting", 3);
        minSkillRequirement7.put("Financial Analysis & Modeling", 3);
        jobs.add(new Job("Setia Capital", new Address("34, Persiaran Setia Impian", "Setia Alam", "40170", "Selangor"), "Business and Finance", "Financial Analyst", minSkillRequirement7, "Offers valuable exposure to financial modeling, budgeting, and investment analysis in a dynamic and supportive environment.", new double[] {800, 1100}, new TreeSet<>()));
        
        //9) Investment Analyst
        MapInterface<String, Integer> minSkillRequirement8 = new HashMap<>();
        minSkillRequirement8.put("Financial Analysis & Modeling", 3);
        minSkillRequirement8.put("Spreadsheet Proficiency", 2);
        jobs.add(new Job("AstraFin Capital", new Address("27, Jalan Sri Mahkota", "Kuantan", "25150", "Pahang"), "Business and Finance", "Investment Analyst", minSkillRequirement8, "Gain hands-on experience in financial modeling, market analysis, and reporting. Collaborate with the finance team to assess risk and optimize investment strategies.", new double[] {800, 1000}, new TreeSet<>()));
        
        //10) Risk Analyst
        MapInterface<String, Integer> minSkillRequirement9 = new HashMap<>();
        minSkillRequirement9.put("Risk Management", 3);
        minSkillRequirement9.put("Market Research and Forecasting", 2);
        jobs.add(new Job("Maybank", new Address("1, Jalan 12", "Ipoh", "25150", "Perak"), "Business and Finance", "Risk Analyst", minSkillRequirement9, "Offers hands-on experience in identifying financial risks, analyzing data, and supporting internal risk assessments in a dynamic finance environment.", new double[] {1000, 1300}, new TreeSet<>()));
        
        //5) Chemist
        MapInterface<String, Integer> minSkillRequirement10 = new HashMap<>();
        minSkillRequirement10.put("Safe Lab Practices and Chemical Handling", 3);
        minSkillRequirement10.put("Spectrophotometry and Gel Electrophoresis", 2);
        jobs.add(new Job("BioChem Solutions", new Address("3, Jalan Mewah", "Ampang", "68000", "Selangor"), "Science", "Chemist", minSkillRequirement10, "Provides hands-on experience in chemical analysis, formulation, and quality control in a real-world laboratory environment.", new double[] {1200, 1500}, new TreeSet<>()));
        
        //3) Biochemist
        MapInterface<String, Integer> minSkillRequirement11 = new HashMap<>();
        minSkillRequirement11.put("Safe Lab Practices and Chemical Handling", 3);
        minSkillRequirement11.put("Cell Culture Techniques", 3);
        jobs.add(new Job("Duopharma Biotech", new Address("Lot 3, Jalan Teknologi 3/6", "Petaling Jaya", "47810", "Selangor"), "Science", "Biochemist", minSkillRequirement11, "Work closely with experienced biochemists and researchers to assist in experiments and support various research projects.", new double[] {1200, 1400}, new TreeSet<>()));
        
        //4) Biotechnologist
        MapInterface<String, Integer> minSkillRequirement12 = new HashMap<>();
        minSkillRequirement12.put("PCR and DNA/RNA Handling Techniques", 3);
        minSkillRequirement12.put("Spectrophotometry and Gel Electrophoresis", 2);
        jobs.add(new Job("BiotechCorp", new Address("Level 7, Tower 2, Menara Usahawan", "Bukit Jalil", "57000", "Kuala Lumpur"), "Science", "Biotechnologist", minSkillRequirement12, "Provides an opportunity to work with cutting-edge technologies and methodologies in biotechnology.", new double[] {1300, 1500}, new TreeSet<>()));
        
        return jobs;
    }
}
