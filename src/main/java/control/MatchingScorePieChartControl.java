/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import adt.*;
import entity.*;

/**
 *
 * @author Chin Jia Jun
 */
public class MatchingScorePieChartControl {
    /**
     * Returns a map of score range labels to the number of matchings in each range.
     * Used for generating pie chart data (distribution of matching scores).
     *
     * @param matchings Set of Matching objects
     * @return Map of score range label to count
     */
    public static MapInterface<String, Integer> getScoreCounts(SetInterface<Matching> matchings) {
        MapInterface<String, Integer> scoreCounts = new HashMap<>();
        // Define the score range labels
        String[] labels = {"0-20%", "21-40%", "41-60%", "61-80%", "81-100%"};
        // Initialize all ranges with zero count
        for (String label : labels) scoreCounts.put(label, 0);
        // Iterate through all matchings and group scores into ranges
        java.util.Iterator<Matching> it = matchings.getIterator();
        while (it.hasNext()) {
            Matching m = it.next();
            double score = m.getScore();
            String label;
            // Determine the appropriate range for the score
            if (score <= 20) label = labels[0];
            else if (score <= 40) label = labels[1];
            else if (score <= 60) label = labels[2];
            else if (score <= 80) label = labels[3];
            else label = labels[4];
            Integer count = scoreCounts.get(label);
            if (count == null) count = 0;
            scoreCounts.put(label, count + 1);
        }
        return scoreCounts;
    }
} 
