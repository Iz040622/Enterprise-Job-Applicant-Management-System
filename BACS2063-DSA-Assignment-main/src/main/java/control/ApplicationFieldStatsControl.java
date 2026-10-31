/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
/**
 *
 * @author Lau Aik Hung
 */
public class ApplicationFieldStatsControl {

    /**
     * Returns a map of field/job title to the number of applications in that field.
     * Used for generating pie chart data.
     *
     * @param applications Set of Application objects
     * @return Map of field/job title to application count
     */
    public static MapInterface<String, Integer> getFieldCounts(SetInterface<Application> applications) {
        MapInterface<String, Integer> fieldCounts = new HashMap<>();
        // Iterate through all applications and count by field/job title
        java.util.Iterator<Application> applIt = applications.getIterator();
        while (applIt.hasNext()) {
            Application app = applIt.next();
            String field = "Unknown";
            try {
                // Try to get the field from the job
                field = app.getMatching().getJob().getField();
            } catch (Exception e) {
                try { 
                    // Fallback: use job title if field is unavailable
                    field = app.getMatching().getJob().getJobTitle(); 
                } catch (Exception ex) 
                {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            Integer count = fieldCounts.get(field);
            if (count == null) count = 0;
            fieldCounts.put(field, count + 1);
        }
        return fieldCounts;
    }

    // For bar chart: Success rate by field/job title
    /**
     * Returns a map of field/job title to the total number of applications in that field.
     * Used for generating bar chart data (total applications per field/job title).
     *
     * @param applications Set of Application objects
     * @return Map of field/job title to total application count
     */
    public static MapInterface<String, Integer> getTotalCounts(SetInterface<Application> applications) {
        MapInterface<String, Integer> totalMap = new HashMap<>();
        // Iterate through all applications and count total by field/job title
        java.util.Iterator<Application> applIt = applications.getIterator();
        while (applIt.hasNext()) {
            Application app = applIt.next();
            String field = "Unknown";
            try {
                // Try to get the field from the job
                field = app.getMatching().getJob().getField();
            } catch (Exception e) {
                try { field = app.getMatching().getJob().getJobTitle(); } catch (Exception ex) {}
            }
            Integer total = totalMap.get(field);
            if (total == null) total = 0;
            totalMap.put(field, total + 1);
        }
        return totalMap;
    }

    /**
     * Returns a map of field/job title to the number of approved applications in that field.
     * Used for generating bar chart data (approved applications per field/job title).
     *
     * @param applications Set of Application objects
     * @return Map of field/job title to approved application count
     */
    public static MapInterface<String, Integer> getApprovedCounts(SetInterface<Application> applications) {
        MapInterface<String, Integer> approvedMap = new HashMap<>();
        // Iterate through all applications and count approved by field/job title
        java.util.Iterator<Application> applIt = applications.getIterator();
        while (applIt.hasNext()) {
            Application app = applIt.next();
            String field = "Unknown";
            try {
                // Try to get the field from the job
                field = app.getMatching().getJob().getField();
            } catch (Exception e) {
                try { field = app.getMatching().getJob().getJobTitle(); } catch (Exception ex) {}
            }
            // Only count if application status is "Approved"
            if (app.getStatus().equalsIgnoreCase("Approved")) {
                Integer approved = approvedMap.get(field);
                if (approved == null) approved = 0;
                approvedMap.put(field, approved + 1);
            }
        }
        return approvedMap;
    }
} 