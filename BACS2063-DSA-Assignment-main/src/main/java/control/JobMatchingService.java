/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Chin Jia Jun

package control;

import adt.*;
import entity.*;
import boundary.*;
import java.util.Iterator;
import utility.EmptyADTHandling;
/**
 *
 * @author chinj
 */
public class JobMatchingService {
    Applicant applicant;
    SetInterface<Employer> employerList;   //To get all jobs

    public JobMatchingService() {
        
    }
    
    public JobMatchingService(Applicant applicant, SetInterface<Employer> employerList) {
        this.applicant = applicant;
        this.employerList = employerList;
    }
    
    public Matching runJobMatching(){
        //Get all jobs from all employers
        SetInterface<Job> allJobs = new TreeSet<>();
        Iterator<Employer> employerIterator = employerList.getIterator();
        while (employerIterator.hasNext()){
            allJobs = allJobs.union(employerIterator.next().getJobList());
        }
        
        SetInterface<Matching> defaultMatchings = matchApplicantJob(allJobs);   //Obtain matching list from jobs
        SetInterface<Matching> filteredMatchings = defaultMatchings;
        int[] index = {0, 0};
        int direction = 1;
        
        while (true){
            JobMatchingServiceUI.displayJobsHeader();
            
            //For empty matching list
            if (filteredMatchings.isEmpty()){
                EmptyADTHandling.displayEmptyMessage("Matching List");
                return null;
            }
            
            //Get page
            getStartEndIndex(filteredMatchings, index, direction);          
            
            //Display matchings
            JobMatchingServiceUI.displayMatches(filteredMatchings, index[0], index[1]);
            direction = 0;      //Reset navigation flag 
            
            //Get matching service option
            int option;
            boolean valid = true;
            do{
                option = JobMatchingServiceUI.getMatchingServiceOption(index[1] - index[0], valid);
                valid = true;
                if (option < 1 || option > 7)
                    valid = false;
            } while(!valid);
            
            switch(option){
                //view matching details
                case 1:                    
                    boolean choiceValid = true;
                    int matchingChoice;
                    do {
                        matchingChoice = JobMatchingServiceUI.getMatchingChoice(index[1] - index[0], choiceValid);
                        choiceValid = validateMatchingChoice(matchingChoice, index);
                    } while(!choiceValid);
                    
                    Matching chosenMatching = filteredMatchings.get(matchingChoice - 1);
                    JobMatchingServiceUI.displayMatchingDetailHeader();
                    JobMatchingServiceUI.displayMatchingDetail(applicant, chosenMatching);
                    
                    //get matching option (apply/back)
                    boolean matchingActionOptionValid = true;
                    int matchingActionOption;
                    do {
                        matchingActionOption = JobMatchingServiceUI.getDetailMatchingOption(chosenMatching, matchingActionOptionValid);
                        matchingActionOptionValid = validateMatchingActionOption(matchingActionOption);
                    } while(!matchingActionOptionValid);
                    
                    //apply job
                    if (matchingActionOption == 1){                        
                        return chosenMatching;
                    }
                    
                    break;
                    
                //next 10 matches
                case 2:                    
                    direction = checkNext(filteredMatchings, index[1]) ? 1 : 0;
                    break;
                    
                //previous 10 matches
                case 3:                    
                    direction = checkPrevious(index[0]) ? -1 : 0;
                    break;
                    
                //filter matchings
                case 4:                                                            
                    boolean filterActionOptionValid = true;
                    int filterActionOption;
                    do {
                        filterActionOption = JobMatchingServiceUI.getFilterChoice(index[1] - index[0], filterActionOptionValid);
                        filterActionOptionValid = validateFilterChoice(filterActionOption);
                    } while(!filterActionOptionValid);
                    
                    if (filterActionOption != 5)
                        filteredMatchings = filterMatching(filteredMatchings, filterActionOption);
                    
                    //reset page
                    index[0] = 0;
                    index[1] = 0;
                    direction = 1;
                    
                    break;
                  
                //search matching
                case 5:              
                    SearchService searchService = new SearchService();
                    String keyword = searchService.validateKeyword(index[1] - index[0] + 15);
                    filteredMatchings = getSearchMatchings(filteredMatchings, keyword);
                    
                    //reset page
                    index[0] = 0;
                    index[1] = 0;
                    direction = 1;
                    
                    break;
                  
                //clear filter/search
                case 6:                    
                    filteredMatchings = defaultMatchings;
                    
                    //reset page
                    index[0] = 0;
                    index[1] = 0;
                    direction = 1;
                    break;
                    
                //back
                case 7:                    
                    return null;
            }     
        }
    }
    
    //Matches applicant with job
    public SetInterface<Matching> matchApplicantJob(SetInterface<Job> jobs){
        SetInterface<Matching> matchings = new TreeSet<>();
        
        Iterator<Job> jobIterator = jobs.getIterator();
        while (jobIterator.hasNext()){
            Job job = jobIterator.next();
            Matching matching = new Matching(applicant, job, calScore(applicant, job));
            
            boolean add = true;
            Iterator<Application> applicationIterator = applicant.getApplicationList().getIterator();
            
            while(applicationIterator.hasNext()){
                add = true;
                if(applicationIterator.next().getMatching().getJob().equals(job)){
                    add = false;
                    break;
                }
            }
            
            if(add == true)
                matchings.add(matching);
        }
        
        return matchings;
    }
    
    //Calculate matching score
    public double calScore(Applicant applicant, Job job){
        double score = 0;

        if (applicant.getCourse().getField().equals(job.getField()))
            score += 20;
        
        if (applicant.getCourse().getPotentialJob().contains(job.getJobTitle()))
            score += 30;
        
        if (applicant.getAddress().getState().equals(job.getAddress().getState())){
            score += 7;
            if (applicant.getAddress().getCity().equals(job.getAddress().getCity()))
                score += 3;
        }
        
        SetInterface<String> applicantSkills = applicant.getSkillSet().getKeys();
        double skillTotalScore = 0;
        
        Iterator<String> applicantSkillsIterator = applicantSkills.getIterator();
        while (applicantSkillsIterator.hasNext()) {
            String applicantSkill = applicantSkillsIterator.next();
            if (job.getMinSkillRequirement().containsKey(applicantSkill)){
                double skillScore = applicant.getSkillSet().get(applicantSkill);
                if(skillScore >= job.getMinSkillRequirement().get(applicantSkill)){
                    skillTotalScore += skillScore;
                }
            }
        }
        score += (skillTotalScore / 25 * 20);
        
        SetInterface<Integer> softskillScores = applicant.getSoftskill().getValues();
        double softskillTotalScore = 0;
        
        Iterator<Integer> softskillScoresIterator = softskillScores.getIterator();
        while (softskillScoresIterator.hasNext()) {
            softskillTotalScore += (int) softskillScoresIterator.next();
        }
        score += (softskillTotalScore / 25 * 10);
        
        score += (applicant.getCgpa() / 4 * 10);
        
        return score;
    }
    
    //Get matching index for a certain page
    public void getStartEndIndex(SetInterface<Matching> matching, int[] index, int direction){
        switch (direction){
            //Next
            case 1: 
                index[0] = index[1] + 1;
                index[1] = (index[0] + 10 > matching.size()) ? matching.size() : index[0] + 9;
                
                break;
                
            //Previous
            case -1:
                if (index[1] % 10 != 0) {
                    index[1] -= (index[1] % 10);
                } else {
                    index[1] -= 10;
                }
                    
                index[0] -= 10;
                
                break;
        }
    }
    
    //Validate whether next page exists
    public boolean checkNext(SetInterface<Matching> matching, int endIndex){
        return endIndex < matching.size();
    }
    
    //validate whether previous page exist
    public boolean checkPrevious(int current){
        return current != 1;
    }
    
    //Validate input for matching to be chosen
    public boolean validateMatchingChoice(int option, int[] index){
        return option >= index[0] && option <= index[1];
    }
    
    //Validate matching action option
    public boolean validateMatchingActionOption(int option){
        return option >= 1 && option <= 2;
    }
    
    //Validate filter choice
    public boolean validateFilterChoice(int option){
        return option >= 1 && option <= 5; 
    }
    
    //Filter matching (based on field, jobtitle, address or skill requirement)
    public SetInterface<Matching> filterMatching(SetInterface<Matching> matchings, int option){
        SetInterface<Matching> filteredMatchings = new TreeSet<>();
        Iterator<Matching> iterator = matchings.getIterator();
        
        while(iterator.hasNext()){
            Matching matching = iterator.next();
            
            boolean add = switch (option) {
                case 1 -> filterField(applicant, matching.getJob());
                case 2 -> filterJobTitle(applicant, matching.getJob()); 
                case 3 -> filterAddress(applicant, matching.getJob()); 
                default -> filterMinSkillRequirement(applicant, matching.getJob());
            };
            
            if (add)
                filteredMatchings.add(matching);
        }
        
        return filteredMatchings;
    }
    
    //Check field for filter
    public boolean filterField(Applicant applicant, Job job){ 
        return applicant.getCourse().getField().equals(job.getField());
    }
    
    //Check jobtitle for filter
    public boolean filterJobTitle(Applicant applicant, Job job){
        return applicant.getCourse().getPotentialJob().contains(job.getJobTitle());
    }
    
    //Check address for filter
    public boolean filterAddress(Applicant applicant, Job job){ 
        return (applicant.getAddress().getState().equals(job.getAddress().getState())) || (applicant.getAddress().getCity().equals(job.getAddress().getCity()));
    }
    
    //Check skill requirement for filter
    public boolean filterMinSkillRequirement(Applicant applicant, Job job){ 
        // at least one skillRequirement
        SetInterface<String> skillSets = applicant.getSkillSet().getKeys();
        
        Iterator<String> skillSetsIterator = skillSets.getIterator();
        while (skillSetsIterator.hasNext()) {
            if (job.getMinSkillRequirement().get(skillSetsIterator.next()) != null)
                return true;
        }
        return false;
    }
    
    //Search matching
    public SetInterface<Matching> getSearchMatchings(SetInterface<Matching> matchings, String keyword){
        SearchService searchService = new SearchService();
        
        SetInterface<Matching> filteredMatchings = new TreeSet<>();
        Iterator<Matching> iterator = matchings.getIterator();
        
        while(iterator.hasNext()){
            Matching matching = iterator.next();
            
            if (searchService.searchMatching(matching.getJob(), keyword))
                filteredMatchings.add(matching);
        }
        
        return filteredMatchings;
    }
}
