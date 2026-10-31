/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author chinj
 */
public class Application implements Comparable<Application>{
    private Matching matching;
    private LocalDateTime applicationDateTime;
    private String status;

    public Application(Matching matching, LocalDateTime applicationDateTime) {
        this.matching = matching;
        this.applicationDateTime = applicationDateTime;
        this.status = "Pending";
    }

    public Matching getMatching() {
        return matching;
    }

    public void setMatching(Matching matching) {
        this.matching = matching;
    }

    public LocalDateTime getApplicationDateTime() {
        return applicationDateTime;
    }

    public void setApplicationDateTime(LocalDateTime applicationDateTime) {
        this.applicationDateTime = applicationDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "\t\t\tApplication Date: " + applicationDateTime.toLocalDate() + "\n\t\t\tApplication Time: " + applicationDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n\t\t\tStatus: " + status;
    }
    
    @Override
    public int compareTo(Application anotherApplication) {
        if (status.equals("Pending") && (anotherApplication.status.equals("Approved") || anotherApplication.status.equals("Rejected")))
            return 1;
        else if ((status.equals("Approved") || status.equals("Rejected")) && anotherApplication.status.equals("Pending"))
            return -1;
        
        if (matching.getScore() > anotherApplication.matching.getScore())
            return 1;
        else if (matching.getScore() < anotherApplication.matching.getScore())
            return -1;
        
        int cmpDateTime = applicationDateTime.compareTo(anotherApplication.applicationDateTime);
        if (cmpDateTime != 0)
            return cmpDateTime;
        
        return matching.compareTo(anotherApplication.matching);
    }
}
