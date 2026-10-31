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
public class EmployerInitializer {
    public SetInterface<Employer> initializeEmployers(){
        SetInterface<Employer> employerList = new TreeSet<>();
        
        JobInitializer jobInitializer = new JobInitializer();
        
        // Get applicants for matching
        ApplicantInitializer applicantInitializer = new ApplicantInitializer();
        SetInterface<Applicant> applicants = applicantInitializer.initializeApplicants();
        Applicant applicant1 = applicants.get(0);
        Applicant applicant2 = applicants.get(1);
        Applicant applicant3 = applicants.get(2);
        java.time.LocalDateTime now = java.time.LocalDateTime.now();

        // Set custom months for each applicant's applications
        java.time.LocalDateTime dec2024 = java.time.LocalDateTime.of(2024, 12, 10, 10, 0);
        java.time.LocalDateTime jan2025 = java.time.LocalDateTime.of(2025, 1, 15, 11, 0);
        java.time.LocalDateTime mar2025 = java.time.LocalDateTime.of(2025, 3, 20, 14, 0);

        //Employer 1
        SetInterface<Job> jobList1 = new TreeSet<>();
        Job jobA = jobInitializer.initializeJobs().get(5); // Accountant
        Job jobB = jobInitializer.initializeJobs().get(6); // Financial Analyst
        Job jobC = jobInitializer.initializeJobs().get(10); // Biochemist
        Job jobD = jobInitializer.initializeJobs().get(11); // Biotechnologist

        // Add applications to jobs
        SetInterface<Application> appListA = new TreeSet<>();
        Matching matchA1 = new Matching(applicant1, jobA, 85.0);
        Application appA1 = new Application(matchA1, dec2024);
        appA1.setStatus("Approved");
        appListA.add(appA1);
        jobA.setApplicationList(appListA);
        applicant1.getApplicationList().add(appA1);

        SetInterface<Application> appListB = new TreeSet<>();
        Matching matchB1 = new Matching(applicant2, jobB, 78.0);
        Application appB1 = new Application(matchB1, jan2025);
        appB1.setStatus("Pending");
        appListB.add(appB1);
        jobB.setApplicationList(appListB);
        applicant2.getApplicationList().add(appB1);

        SetInterface<Application> appListC = new TreeSet<>();
        Matching matchC1 = new Matching(applicant3, jobC, 90.0);
        Application appC1 = new Application(matchC1, mar2025);
        appC1.setStatus("Rejected");
        appListC.add(appC1);
        jobC.setApplicationList(appListC);
        applicant3.getApplicationList().add(appC1);

        SetInterface<Application> appListD = new TreeSet<>();
        Matching matchD1 = new Matching(applicant1, jobD, 88.0);
        Application appD1 = new Application(matchD1, dec2024.plusDays(2));
        appD1.setStatus("Approved");
        appListD.add(appD1);
        jobD.setApplicationList(appListD);
        applicant1.getApplicationList().add(appD1);

        jobList1.add(jobA);
        jobList1.add(jobB);
        jobList1.add(jobC);
        jobList1.add(jobD);
        employerList.add(new Employer("Isaac1!", "Isaac1!", jobList1)); 
        
        //Employer 2
        SetInterface<Job> jobList2 = new TreeSet<>();
        Job jobE = jobInitializer.initializeJobs().get(0); // Software Engineer
        Job jobF = jobInitializer.initializeJobs().get(1); // Software Developer
        Job jobG = jobInitializer.initializeJobs().get(7); // Investment Analyst
        Job jobH = jobInitializer.initializeJobs().get(8); // Risk Analyst
        Job jobI = jobInitializer.initializeJobs().get(9); // Chemist
        SetInterface<Application> appListE = new TreeSet<>();
        Matching matchE1 = new Matching(applicant2, jobE, 92.0);
        Application appE1 = new Application(matchE1, jan2025.plusDays(1));
        appE1.setStatus("Approved");
        appListE.add(appE1);
        jobE.setApplicationList(appListE);
        applicant2.getApplicationList().add(appE1);
        SetInterface<Application> appListF = new TreeSet<>();
        Matching matchF1 = new Matching(applicant3, jobF, 80.0);
        Application appF1 = new Application(matchF1, mar2025.plusDays(2));
        appF1.setStatus("Pending");
        appListF.add(appF1);
        jobF.setApplicationList(appListF);
        applicant3.getApplicationList().add(appF1);
        SetInterface<Application> appListG = new TreeSet<>();
        Matching matchG1 = new Matching(applicant1, jobG, 75.0);
        Application appG1 = new Application(matchG1, dec2024.plusDays(3));
        appG1.setStatus("Rejected");
        appListG.add(appG1);
        jobG.setApplicationList(appListG);
        applicant1.getApplicationList().add(appG1);
        SetInterface<Application> appListH = new TreeSet<>();
        Matching matchH1 = new Matching(applicant2, jobH, 85.0);
        Application appH1 = new Application(matchH1, jan2025.plusDays(5));
        appH1.setStatus("Approved");
        appListH.add(appH1);
        jobH.setApplicationList(appListH);
        applicant2.getApplicationList().add(appH1);
        SetInterface<Application> appListI = new TreeSet<>();
        Matching matchI1 = new Matching(applicant3, jobI, 88.0);
        Application appI1 = new Application(matchI1, mar2025.plusDays(4));
        appI1.setStatus("Pending");
        appListI.add(appI1);
        jobI.setApplicationList(appListI);
        applicant3.getApplicationList().add(appI1);
        jobList2.add(jobE);
        jobList2.add(jobF);
        jobList2.add(jobG);
        jobList2.add(jobH);
        jobList2.add(jobI);
        employerList.add(new Employer("Keshandra1!", "Keshandra1!", jobList2));
        
        //Employer 3
        SetInterface<Job> jobList3 = new TreeSet<>();
        Job jobJ = jobInitializer.initializeJobs().get(2); // Data Analyst
        Job jobK = jobInitializer.initializeJobs().get(3); // Data Engineer
        Job jobL = jobInitializer.initializeJobs().get(4); // Auditor
        // Add two more jobs with different fields
        Job jobM = jobInitializer.initializeJobs().get(0); // Software Engineer
        Job jobN = jobInitializer.initializeJobs().get(6); // Financial Analyst
        SetInterface<Application> appListJ = new TreeSet<>();
        Matching matchJ1 = new Matching(applicant1, jobJ, 95.0);
        Application appJ1 = new Application(matchJ1, dec2024.plusDays(5));
        appJ1.setStatus("Approved");
        appListJ.add(appJ1);
        jobJ.setApplicationList(appListJ);
        applicant1.getApplicationList().add(appJ1);
        SetInterface<Application> appListK = new TreeSet<>();
        Matching matchK1 = new Matching(applicant2, jobK, 82.0);
        Application appK1 = new Application(matchK1, jan2025.plusDays(10));
        appK1.setStatus("Rejected");
        appListK.add(appK1);
        jobK.setApplicationList(appListK);
        applicant2.getApplicationList().add(appK1);
        SetInterface<Application> appListL = new TreeSet<>();
        Matching matchL1 = new Matching(applicant3, jobL, 77.0);
        Application appL1 = new Application(matchL1, mar2025.plusDays(7));
        appL1.setStatus("Approved");
        appListL.add(appL1);
        jobL.setApplicationList(appListL);
        applicant3.getApplicationList().add(appL1);
        // Applications for new jobs
        SetInterface<Application> appListM = new TreeSet<>();
        Matching matchM1 = new Matching(applicant2, jobM, 88.0);
        Application appM1 = new Application(matchM1, jan2025.plusDays(12));
        appM1.setStatus("Pending");
        appListM.add(appM1);
        jobM.setApplicationList(appListM);
        applicant2.getApplicationList().add(appM1);
        SetInterface<Application> appListN = new TreeSet<>();
        Matching matchN1 = new Matching(applicant1, jobN, 91.0);
        Application appN1 = new Application(matchN1, dec2024.plusDays(8));
        appN1.setStatus("Approved");
        appListN.add(appN1);
        jobN.setApplicationList(appListN);
        applicant1.getApplicationList().add(appN1);
        jobList3.add(jobJ);
        jobList3.add(jobK);
        jobList3.add(jobL);
        jobList3.add(jobM);
        jobList3.add(jobN);
        employerList.add(new Employer("Alex1!", "Alex1!", jobList3));
        return employerList;
    }
}
