/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author chinj
 */
public class Matching implements Comparable<Matching>{
    private Applicant applicant;
    private Job job;
    private double score;

    public Matching(Applicant applicant, Job job, double score) {
        this.applicant = applicant;
        this.job = job;
        this.score = score;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Applicant: " + applicant.getName() + "\nJob: " + job.getCompany() + "\nScore: " + String.format("%,.2f%%", score);
    }
    
    
    
    @Override
    public int compareTo(Matching anotherMatching) {
        if (score < anotherMatching.score) {
            return 1;
        } else if (score > anotherMatching.score) {
            return -1;
        }
        
        int cmpApplicant = applicant.compareTo(anotherMatching.applicant);
        if (cmpApplicant != 0)
            return cmpApplicant;
        
        int cmpJob = job.compareTo(anotherMatching.job);
        if (cmpJob != 0)
            return cmpJob;
        
        return 0;
    }
}
