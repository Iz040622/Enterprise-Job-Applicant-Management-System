/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Isaac Lim Tian Le
 */
public class ApplicationStatsControl {

    // For bar chart: Applications per month
    /**
     * Returns a map of year-month (yyyy-MM) to the number of applications submitted in that month.
     * Used for generating bar chart data (applications per month).
     *
     * @param applications Set of Application objects
     * @return Map of year-month to application count
     */
    public static MapInterface<String, Integer> getMonthCounts(SetInterface<Application> applications) {
        MapInterface<String, Integer> monthCounts = new HashMap<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");
        java.util.Iterator<Application> applIt = applications.getIterator();
        while (applIt.hasNext()) {
            Application app = applIt.next();
            String ym = app.getApplicationDateTime().format(fmt);
            Integer count = monthCounts.get(ym);
            if (count == null) count = 0;
            monthCounts.put(ym, count + 1);
        }
        return monthCounts;
    }

    // For pie chart: Status distribution
    /**
     * Returns a map of application status to the number of applications with that status.
     * Used for generating pie chart data (status distribution).
     *
     * @param applications Set of Application objects
     * @return Map of status to application count
     */
    public static MapInterface<String, Integer> getStatusCounts(SetInterface<Application> applications) {
        MapInterface<String, Integer> statusCounts = new HashMap<>();
        java.util.Iterator<Application> applIt = applications.getIterator();
        while (applIt.hasNext()) {
            Application app = applIt.next();
            String status = app.getStatus();
            Integer count = statusCounts.get(status);
            if (count == null) count = 0;
            statusCounts.put(status, count + 1);
        }
        return statusCounts;
    }
} 