import adt.*;
import control.*;
import dao.*;
import entity.*;
import java.util.Scanner;
import boundary.*;
import utility.SuccessMessageUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author chinj
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        ApplicantInitializer applicantInitializer = new ApplicantInitializer();
        SetInterface<Applicant> applicantList = applicantInitializer.initializeApplicants();
        
        EmployerInitializer employerInitializer = new EmployerInitializer();
        SetInterface<Employer> employerList = employerInitializer.initializeEmployers();

        CourseInitializer courseInitializer = new CourseInitializer();
        SetInterface<Course> courses = courseInitializer.initializeCourses();
        
        SetInterface<String> softskills = new TreeSet<>();
        softskills.add("Problem Solving");
        softskills.add("Teamwork & Collaboration");
        softskills.add("Time Management");
        softskills.add("Adaptability & Willingness to Learn");
        softskills.add("Presentation & Reporting Skills");
        
        while (true) {
            Authentication auth = new Authentication(applicantList, employerList, courses, softskills);
            User user = auth.runAuthentication();
            //check user (null exit, check instance)
            if (user == null) {
                SuccessMessageUI.successMessage("Exit Program");
                return;
            } else if (user instanceof Applicant) {
                AccountMaintenance accountMaintenance = new AccountMaintenance(user, courses, applicantList, softskills, auth);
                JobMatchingService jobMatchingService = new JobMatchingService((Applicant) user, employerList);
                ApplicationMaintenance applicationMaintenance = new ApplicationMaintenance(((Applicant) user).getApplicationList());
                
                 while (user != null){
                    int applicantOption = applicantMenu();
                                        
                    switch(applicantOption){
                        //Find internship
                        case 1:
                            Matching matching = jobMatchingService.runJobMatching();
                                
                            if (matching != null){
                                applicationMaintenance.makeApplication(matching);
                                //success message
                            }
                            break;
                            
                        //View application   
                        case 2:
                            applicationMaintenance.runApplicationMaintenance(user);
                            break;
                            
                        //View account
                        case 3:
                            user = accountMaintenance.runAccountMaintenance();
                            break;
                            
                        //Logout
                        default:
                            user = null;
                            SuccessMessageUI.successMessage("Logout");
                    }
                }
            } else {   
                AccountMaintenance accountMaintenance = new AccountMaintenance(user, courses, employerList, auth);
                JobMaintenance jobMaintenance = new JobMaintenance(((Employer) user).getJobList(), courses);
                
                while (user != null){
                    int employerOption = employerMenu();
                                        
                    switch(employerOption){
                        //Post job
                        case 1:
                            jobMaintenance.createJob();
                            break;
                            
                        //View job   
                        case 2:
                            Job job;

                            do {
                                job = jobMaintenance.runJobMaintenance();
                                //view applications
                                if (job != null) {
                                    ApplicationMaintenance applicationMaintenance = new ApplicationMaintenance(job.getApplicationList());
                                    applicationMaintenance.runApplicationMaintenance(user);
                                }
                            } while (job != null);
                            break;
                            
                        //View report
                        case 3:
                        System.out.print("\033[H\033[2J");      //Clear screen
                        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\tTARUMT Internship Application System" + "\033[0m\n\n\n\n\n\n\n");
                            boolean reportMenuActive = true;
                            while (reportMenuActive) {
                                System.out.println("\n\n\t\t\t1. View application charts");
                                System.out.println("\t\t\t2. View field/title charts");
                                System.out.println("\t\t\t3. View matching score pie chart");
                                System.out.println("\t\t\t4. View keyword report");
                                System.out.println("\t\t\t5. Back");
                                System.out.print("\t\t\tEnter your option (1-5): ");
                                int reportOption = 0;
                                boolean validReportInput = false;
                                while (!validReportInput) {
                                    try {
                                        reportOption = scanner.nextInt();
                                        if (reportOption >= 1 && reportOption <= 5) {
                                            validReportInput = true;
                                        } else {
                                            System.out.println("\033[31m" + "\t\t\tInvalid input! Must be 1-5 only!" + "\033[0m");
                                            System.out.print("\t\t\tEnter your option (1-5): ");
                                        }
                                    } catch (Exception ex) {
                                        scanner.nextLine();
                                        System.out.println("\033[31m" + "\t\t\tInvalid input! Must be 1-5 only!" + "\033[0m");
                                        System.out.print("\t\t\tEnter your option (1-5: ");
                                    }
                                }
                                scanner.nextLine();
                                switch (reportOption) {
                                    case 1:
                                    System.out.print("\033[H\033[2J");      //Clear screen
                                    System.out.println("\n\n\n\n\033[34m" + "\t\t\t\tTARUMT Internship Application System" + "\033[0m\n\n\n\n\n\n\n");
                                        final User currentUser = user;
                                        javax.swing.SwingUtilities.invokeLater(() -> {
                                            adt.SetInterface<entity.Application> employerApplications = new adt.TreeSet<>();
                                            adt.SetInterface<entity.Job> jobList = ((entity.Employer)currentUser).getJobList();
                                            java.util.Iterator<entity.Job> jobIt = jobList.getIterator();
                                            while (jobIt.hasNext()) {
                                                entity.Job empJob = jobIt.next();
                                                java.util.Iterator<entity.Application> appIt = empJob.getApplicationList().getIterator();
                                                while (appIt.hasNext()) {
                                                    employerApplications.add(appIt.next());
                                                }
                                            }
                                            new boundary.ApplicationStatsUI(employerApplications, true).setVisible(true);
                                        });
                                        break;
                                    case 2:
                                    System.out.print("\033[H\033[2J");      //Clear screen
                                    System.out.println("\n\n\n\n\033[34m" + "\t\t\t\tTARUMT Internship Application System" + "\033[0m\n\n\n\n\n\n\n");
                                        new ApplicationFieldStatsUI((Employer)user).setVisible(true);
                                        break;
                                    case 3:
                                    System.out.print("\033[H\033[2J");      //Clear screen
                                    System.out.println("\n\n\n\n\033[34m" + "\t\t\t\tTARUMT Internship Application System" + "\033[0m\n\n\n\n\n\n\n");
                                        new MatchingScorePieChartUI((Employer)user).setVisible(true);
                                        break;
                                    case 4:
                                        // View the keyword report for this employer
                                        System.out.print("\033[H\033[2J");      //Clear screen
                                        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\tTARUMT Internship Application System" + "\033[0m\n\n\n\n\n\n\n");
                                        new boundary.ApplicantKeywordPieChartUI().setVisible(true);
                                        break;

                                    case 5:
                                        reportMenuActive = false;
                                        break;
                                }
                            }
                            break;
                            
                        //View account
                        case 4:
                            user = accountMaintenance.runAccountMaintenance();
                            break;
                            
                        //Logout
                        default:
                            user = null;
                            SuccessMessageUI.successMessage("Logout");
                        }
                    }
                }
            }

        }
    
    
    public static int applicantMenu() {
        boolean inputValid = true;
        int option = 0;

        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\tTARUMT Internship Application System" + "\033[0m\n\n\n\n\n\n\n");

        System.out.println("\n\n\t\t\t1. Find Internship"
                + "\n\t\t\t2. View Applications"
                + "\n\t\t\t3. View Account"
                + "\n\t\t\t4. Logout");

        do {
            try {
                System.out.print("\033[20;0H\033[0J");      //Clear screen from row 20 column 0

                if (!inputValid) {
                    System.out.println("\033[31m" + "\t\t\tInvalid input! Must be 1-4 only!" + "\033[0m");      //MessageUI.displayInvalidChoiceMessage()
                }
                System.out.print("\t\t\tEnter your option (1-4): ");

                inputValid = true;

                option = scanner.nextInt();
                if (option < 1 || option > 4) {
                    inputValid = false;
                }
            } catch (Exception ex) {
                scanner.nextLine();
                inputValid = false;
            }

        } while (!inputValid);

        scanner.nextLine();
        
        return option;
    }
    
    public static int employerMenu() {
        boolean inputValid = true;
        int option = 0;

        System.out.print("\033[H\033[2J");      //Clear screen
        System.out.println("\n\n\n\n\033[34m" + "\t\t\t\tTARUMT Internship Application System" + "\033[0m\n\n\n\n\n\n\n");

        System.out.println("\n\n\t\t\t1. Post new job"
                + "\n\t\t\t2. View jobs"
                + "\n\t\t\t3. View report"
                + "\n\t\t\t4. View account"
                + "\n\t\t\t5. Logout");

        do {
            try {
                System.out.print("\033[21;0H\033[0J");      //Clear screen from row 20 column 0

                if (!inputValid) {
                    System.out.println("\033[31m" + "\t\t\tInvalid input! Must be 1-5 only!" + "\033[0m");
                }
                System.out.print("\t\t\tEnter your option (1-5): ");

                inputValid = true;

                option = scanner.nextInt();
                if (option < 1 || option > 6) {
                    inputValid = false;
                }
            } catch (Exception ex) {
                scanner.nextLine();
                inputValid = false;
            }

        } while (!inputValid);

        scanner.nextLine();
        
        return option;
    }
}
