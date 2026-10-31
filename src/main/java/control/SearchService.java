/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Author: Keshandra A/L Jayaselan

package control;

import adt.*;
import entity.*;
import boundary.*;
import java.util.Iterator;

/**
 *
 * @author chinj
 */
public class SearchService {
    public static MapInterface<String, Integer> matchingKeywordCount = new HashMap<>();
    public static MapInterface<String, Integer> applicationKeywordCount = new HashMap<>();
    
    //Check similarity between 2 words
    public boolean checkSimilarity(String word1, String word2){
        return calSimilarity(word1.toLowerCase(), word2.toLowerCase()) >= 0.6;
    }
    
    //Check whether a word exists in a sentence based on similarity
    public boolean checkSentence(String sentence, String keyword){
        String[] words = sentence.split("\\s+"); 
        
        for (String word : words){
            if (checkSimilarity(word, keyword))
                return true;
        }
        return false;
    }
    
    //Calculate similarity based on LevenshteinDistance
    public double calSimilarity(String word1, String word2){
        int distance = calLevenshteinDistance(word1, word2);
        return 1.0 - (double) distance / Math.max(word1.length(), word2.length());
    }
    
    //Calculate LevenshteinDistance
    public int calLevenshteinDistance(String word1, String word2){
        int[][] subproblem = new int[word1.length() + 1][word2.length() + 1]; 
        
        for (int i = 0; i < subproblem.length; i++){
            for (int j = 0; j < subproblem[i].length; j++){
                if (i == 0){
                    subproblem[i][j] = j;
                }
                else if (j == 0){
                    subproblem[i][j] = i;
                }
                else{
                    if (word1.charAt(i - 1) == word2.charAt(j - 1)){
                        subproblem[i][j] = subproblem[i - 1][j - 1];
                    }
                    else{
                        subproblem[i][j] = Math.min((Math.min(subproblem[i][j - 1], subproblem[i - 1][j])), subproblem[i - 1][j - 1]) + 1;
                    }
                }
            }
        }
        return subproblem[word1.length()][word2.length()];
    }
    
    //Applicant search matching 
    public boolean searchMatching(Job job, String keyword){
        matchingKeywordCount.put(keyword, matchingKeywordCount.containsKey(keyword) ? matchingKeywordCount.get(keyword) + 1 : 1);
        
        if (checkSimilarity(job.getCompany(), keyword))
            return true;
        
        if (checkSentence(job.getAddress().toString(), keyword))
            return true;
        
        if (checkSimilarity(job.getField(), keyword))
            return true;

        if (checkSimilarity(job.getJobTitle(), keyword))
            return true;
                
        SetInterface<String> skills = job.getMinSkillRequirement().getKeys();
        Iterator<String> skillIterator = skills.getIterator();
        while (skillIterator.hasNext()){
            if (checkSimilarity(skillIterator.next(), keyword))
                return true;
        }
        
        return checkSentence(job.getDescription(), keyword);
    }
    
    //Applicant search application 
    public boolean applicantSearchApplication(Application application, String keyword){
        applicationKeywordCount.put(keyword, applicationKeywordCount.containsKey(keyword) ? applicationKeywordCount.get(keyword) + 1 : 1);
        
        if (checkSimilarity(application.getStatus(), keyword))
            return true;
        
        Job job = application.getMatching().getJob();
        if (checkSimilarity(job.getCompany(), keyword))
            return true;
        
        if (checkSentence(job.getAddress().toString(), keyword))
            return true;
        
        if (checkSimilarity(job.getField(), keyword))
            return true;

        if (checkSimilarity(job.getJobTitle(), keyword))
            return true;
                
        SetInterface<String> skills = job.getMinSkillRequirement().getKeys();
        Iterator<String> skillIterator = skills.getIterator();
        while (skillIterator.hasNext()){
            if (checkSimilarity(skillIterator.next(), keyword))
                return true;
        }
        
        return checkSentence(job.getDescription(), keyword);
    }
    
    //Employer search application 
    public boolean employerSearchApplication(Application application, String keyword){
        applicationKeywordCount.put(keyword, applicationKeywordCount.containsKey(keyword) ? applicationKeywordCount.get(keyword) + 1 : 1);
        
        if (checkSimilarity(application.getStatus(), keyword))
            return true;
        
        Applicant applicant = application.getMatching().getApplicant();
        if (checkSimilarity(applicant.getName(), keyword))
            return true;
        
        if (checkSentence(applicant.getAddress().toString(), keyword))
            return true;
        
        if (checkSimilarity(applicant.getCourse().getCourseName(), keyword))
            return true;

        if (checkSimilarity(applicant.getCourse().getFaculty(), keyword))
            return true;
           
        if (checkSimilarity(applicant.getCourse().getField(), keyword))
            return true;
        
        SetInterface<String> skills = applicant.getSkillSet().getKeys();
        Iterator<String> skillIterator = skills.getIterator();
        while (skillIterator.hasNext()){
            if (checkSimilarity(skillIterator.next(), keyword))
                return true;
        }
        
        return false;
    }
    
    //Validate keyowrd
    public String validateKeyword(int row){
        boolean valid = true;
        String keyword;
        
        do{
            keyword = SearchServiceUI.getKeyword(valid, row);
            valid = (!keyword.equals(""));
        } while(!valid);
                
        return keyword;
    }
}
