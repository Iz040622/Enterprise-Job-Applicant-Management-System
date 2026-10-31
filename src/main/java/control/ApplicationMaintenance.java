/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Chin Jia Jun

package control;

import adt.*;
import entity.*;
import boundary.*;
import utility.*;
import java.time.LocalDateTime;
import java.util.Iterator;
/**
 *
 * @author chinj
 */
public class ApplicationMaintenance {
    
    private SetInterface<Application> applicationList;
    
    public ApplicationMaintenance(SetInterface<Application> applicationList) {
        this.applicationList = applicationList;
    }
    
    //Method for applicant to apply job
    public void makeApplication(Matching matching) {
        Application newApplication = new Application(matching, LocalDateTime.now());
        applicationList.add(newApplication);        //Add application into applicationList in applicant side
        matching.getJob().getApplicationList().add(newApplication);     //Add application into applicationList in job side
        SuccessMessageUI.successMessage("Apply");
    }

    public void runApplicationMaintenance(User user) {
        SetInterface<Application> filteredApplicationList = applicationList;
        int[] index = {0, 0};
        int direction = 1;
        
        while (true) {
            ApplicationMaintenanceUI.displayApplicationListHeader();
            
            //For empty applicationList
            if (filteredApplicationList.isEmpty()){
                EmptyADTHandling.displayEmptyMessage("Application List");
                return;
            }
            
            //Get page
            getStartEndIndex(filteredApplicationList, index, direction);
            
            //Display applications
            if (user instanceof Applicant) {
                ApplicationMaintenanceUI.applicantViewApplications(filteredApplicationList, index[0], index[1]);   //get this out for filter & search & page 
            }
            else{
                ApplicationMaintenanceUI.employerViewApplications(filteredApplicationList, index[0], index[1]);
            }
            direction = 0;      //Reset page navigation flag
            
            //Get maintenance option
            boolean maintenanceValid = true;
            int maintenanceOption;
            do {
                maintenanceOption = ApplicationMaintenanceUI.getMaintenanceChoice(index[1] - index[0], maintenanceValid);
                maintenanceValid = validateMaintenanceOption(maintenanceOption);
            } while (!maintenanceValid);

            switch (maintenanceOption) {
                //View application detail
                case 1:                                       
                    //Obtain application option
                    boolean maintenanceSelectValid = true;
                    int maintenanceSelectOption;
                    do {
                        maintenanceSelectOption = ApplicationMaintenanceUI.selectApplication(index[1] - index[0], maintenanceSelectValid);
                        maintenanceSelectValid = validateMaintenanceSelectOption(maintenanceSelectOption, index);
                    } while (!maintenanceSelectValid);

                    Application selectedApplication = filteredApplicationList.get(maintenanceSelectOption - 1);
                    ApplicationMaintenanceUI.displayApplicationDetailHeader();

                    //Display application details
                    if (user instanceof Applicant) {
                        ApplicationMaintenanceUI.applicantViewApplicationDetail(selectedApplication);
                    } else {
                        ApplicationMaintenanceUI.employerViewApplicationDetail(selectedApplication);
                    }

                    //For pending application
                    if (selectedApplication.getStatus().equals("Pending")) {
                        //Obtain detail maintenance option
                        boolean detailMaintenanceValid = true;
                        int detailMaintenanceOption = 0;
                        do {
                            if (user instanceof Applicant) {
                                detailMaintenanceOption = ApplicationMaintenanceUI.getApplicantApplicationDetailAction(selectedApplication, detailMaintenanceValid);
                                detailMaintenanceValid = validateApplicantDetailMaintenanceOption(detailMaintenanceOption);
                            } else {
                                detailMaintenanceOption = ApplicationMaintenanceUI.getEmployerApplicationDetailAction(detailMaintenanceValid);
                                detailMaintenanceValid = validateEmployerDetailMaintenanceOption(detailMaintenanceOption);
                            }
                        } while (!detailMaintenanceValid);

                        //Applicant cancel application
                        if (user instanceof Applicant && detailMaintenanceOption == 1) {
                            boolean cancelValid = true;
                            char confirmCancel;

                            do {
                                confirmCancel = ApplicationMaintenanceUI.confirmAction(cancelValid, "cancel", selectedApplication.getMatching().getJob().getMinSkillRequirement().size() + 26);
                                cancelValid = validateConfirm(confirmCancel);
                            } while (!cancelValid);

                            if (confirmCancel == 'Y') {
                                cancelApplication(selectedApplication);
                            }
                        } else {
                            //Employer approve application
                            if (user instanceof Employer && detailMaintenanceOption == 1) {
                                boolean approveValid = true;
                                char confirmApprove;

                                do {
                                    confirmApprove = ApplicationMaintenanceUI.confirmAction(approveValid, "approve", 37);
                                    approveValid = validateConfirm(confirmApprove);
                                } while (!approveValid);

                                if (confirmApprove == 'Y') {
                                    approveApplication(selectedApplication);
                                }
                            //Employer reject application
                            } else if (user instanceof Employer && detailMaintenanceOption == 2) {
                                boolean rejectValid = true;
                                char confirmReject;

                                do {
                                    confirmReject = ApplicationMaintenanceUI.confirmAction(rejectValid, "reject", 37);
                                    rejectValid = validateConfirm(confirmReject);
                                } while (!rejectValid);

                                if (confirmReject == 'Y') {
                                    rejectApplication(selectedApplication);
                                }
                            }
                        }

                    //For approved/rejected application
                    } else {
                        ApplicationMaintenanceUI.getApplicationDetailProceedInput();
                    }
                    break;
                
                //next 10 application
                case 2:                    
                    direction = checkNext(filteredApplicationList, index[1]) ? 1 : 0;
                    break;
                    
                //previous 10 application
                case 3:                    
                    direction = checkPrevious(index[0]) ? -1 : 0;
                    break;
                  
                //filter application
                case 4:
                    //Obtain filter option                              
                    boolean filterActionOptionValid = true;
                    int filterActionOption;
                    do {
                        filterActionOption = ApplicationMaintenanceUI.getFilterChoice(index[1] - index[0], filterActionOptionValid);
                        filterActionOptionValid = validateFilterChoice(filterActionOption);
                    } while(!filterActionOptionValid);
                    
                    if (filterActionOption != 5)
                        filteredApplicationList = filterApplication(filteredApplicationList, filterActionOption);
                    
                    //Reset page
                    index[0] = 0;
                    index[1] = 0;
                    direction = 1;
                    break;
                
                //search application
                case 5:                                                             
                    SearchService searchService = new SearchService();
                    String keyword = searchService.validateKeyword(index[1] - index[0] + 15);
                    filteredApplicationList = getSearchApplications(user, filteredApplicationList, keyword);
                    
                    //Reset page
                    index[0] = 0;
                    index[1] = 0;
                    direction = 1;
                    break;
                    
                //clear filter/search
                case 6:
                    //Reset filter list
                    filteredApplicationList = applicationList;
                    
                    //Reset page
                    index[0] = 0;
                    index[1] = 0;
                    direction = 1;
                    break;
                 
                //back
                case 7:                    
                    return;
            }
        }
    }

    //Method to obtain index of applications in a certain page
    public void getStartEndIndex(SetInterface<Application> applicationList, int[] index, int direction){
        switch (direction){
            //Next
            case 1: 
                index[0] = index[1] + 1;
                index[1] = (index[0] + 10 > applicationList.size()) ? applicationList.size() : index[0] + 9;    //Add 10 if not yet reach application number, else add until application number
                
                break;
                
            //Previous
            case -1:
                if (index[1] % 10 != 0) {       //Deduct 10 if not yet reach application number, else deduct application number % 10 (balance)
                    index[1] -= (index[1] % 10);
                } else {
                    index[1] -= 10;
                }
                    
                index[0] -= 10;
                
                break;
        }
    }
    
    //Method to check whether next page is available
    public boolean checkNext(SetInterface<Application> applicationList, int endIndex){
        return endIndex < applicationList.size();
    }
    
    //Method to check whether previous page is available
    public boolean checkPrevious(int current){
        return current != 1;
    }
    
    //Method to validate filter choice
    public boolean validateFilterChoice(int option){
        return option >= 1 && option <= 5; 
    }
    
    //Method to filter applications (based on field, jobtitle, address or skill requirement)
    public SetInterface<Application> filterApplication(SetInterface<Application> applicationList, int option){
        SetInterface<Application> filteredApplications = new TreeSet<>();
        Iterator<Application> iterator = filteredApplications.getIterator();
        
        JobMatchingService jobMatchingService = new JobMatchingService();
        while(iterator.hasNext()){
            Application application = iterator.next();
            
            boolean add = switch (option) {
                case 1 -> jobMatchingService.filterField(application.getMatching().getApplicant(), application.getMatching().getJob());
                case 2 -> jobMatchingService.filterJobTitle(application.getMatching().getApplicant(), application.getMatching().getJob()); 
                case 3 -> jobMatchingService.filterAddress(application.getMatching().getApplicant(), application.getMatching().getJob()); 
                default -> jobMatchingService.filterMinSkillRequirement(application.getMatching().getApplicant(), application.getMatching().getJob());
            };
            
            if (add)
                filteredApplications.add(application);
        }
        
        return filteredApplications;
    }
    
    //Method to search applications
    public SetInterface<Application> getSearchApplications(User user, SetInterface<Application> applicationList, String keyword){
        SearchService searchService = new SearchService();
        
        SetInterface<Application> filteredApplications = new TreeSet<>();
        Iterator<Application> iterator = applicationList.getIterator();
        
        while(iterator.hasNext()){
            Application application = iterator.next();
            
            //For applicant search
            if (user instanceof Applicant && searchService.applicantSearchApplication(application, keyword))
                filteredApplications.add(application);
            //For employer search
            else if (user instanceof Employer && searchService.employerSearchApplication(application, keyword))
                filteredApplications.add(application);
        }
        
        return filteredApplications;
    }
    
    //Method to validate input for maintenance option
    public boolean validateMaintenanceOption(int option){
        return option > 0 && option <= 7;
    }
    
    //Method to validate applicant input for detail maintenance option
    public boolean validateApplicantDetailMaintenanceOption(int option){
        return option > 0 && option <= 2;
    }
    
    //Method to validate employer input for detail maintenance option
    public boolean validateEmployerDetailMaintenanceOption(int option){
        return option > 0 && option <= 3;
    }
    
    //Method to validate input to select application
    public boolean validateMaintenanceSelectOption(int option, int[] index){
        return option >= index[0] && option <= index[1];
    }
    
    //Method to validate confirmation input
    public boolean validateConfirm(char confirm){
        return confirm == 'Y' || confirm == 'N';
    }
    
    //Invoked when applicant cancel application
    public void cancelApplication(Application application){
        application.getMatching().getJob().getApplicationList().remove(application);
        application.getMatching().getApplicant().getApplicationList().remove(application);
        
        SuccessMessageUI.successMessage("Cancel");
    }
    
    //Invoked when employer approve application
    public void approveApplication(Application application){
        application.setStatus("Approved");
        
        SuccessMessageUI.successMessage("Approve");
    }
    
    //Invoked when employer reject application
    public void rejectApplication(Application application){
        application.setStatus("Rejected");
        
        SuccessMessageUI.successMessage("Reject");
    }
    
    //Invoked after applicant updates details or employer updates job
    public void updateApplicationScores(){
        JobMatchingService jobMatchingService = new JobMatchingService();
        Iterator<Application> applicationIterator = applicationList.getIterator();
        
        while (applicationIterator.hasNext()){
            Application application = applicationIterator.next();
            double newScore = jobMatchingService.calScore(application.getMatching().getApplicant(), application.getMatching().getJob());
            application.getMatching().setScore(newScore);
        }
    }
    
    //Invoked when a job is removed
    public void removeJobApplications(){
        Iterator<Application> applicationIterator = applicationList.getIterator();
        
        while (applicationIterator.hasNext()){
            Application application = applicationIterator.next();
            application.getMatching().getApplicant().getApplicationList().remove(application);
        }
    }
    
    //Invoked when applicant/employer delete account
    public void removeApplicantApplications(){
        Iterator<Application> applicationIterator = applicationList.getIterator();
        
        while (applicationIterator.hasNext()){
            Application application = applicationIterator.next();
            application.getMatching().getJob().getApplicationList().remove(application);
        }
    }
}
