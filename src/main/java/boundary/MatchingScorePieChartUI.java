/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;
import adt.*;
import entity.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 *
 * @author Chin Jia Jun
 */
public class MatchingScorePieChartUI extends JFrame {
    public MatchingScorePieChartUI(Employer employer) {
        super("Matching Score Distribution");
        SetInterface<Job> jobList = employer.getJobList();
        SetInterface<Matching> matchingSet = new TreeSet<>();
        Iterator<Job> jobIt = jobList.getIterator();
        while (jobIt.hasNext()) {
            Job empJob = jobIt.next();
            Iterator<Application> appIt = empJob.getApplicationList().getIterator();
            while (appIt.hasNext()) {
                Application app = appIt.next();
                if (app.getMatching() != null) {
                    matchingSet.add(app.getMatching());
                }
            }
        }
        setLayout(new BorderLayout());
        add(new ScorePieChartPanel(matchingSet, 600, 600), BorderLayout.CENTER);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static class ScorePieChartPanel extends JPanel {
        private final MapInterface<String, Integer> scoreCounts; // range label -> count
        private final String[] labels = {"0-20%", "21-40%", "41-60%", "61-80%", "81-100%"};
        private final Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.CYAN, Color.GREEN};
        private final int prefW, prefH;
        public ScorePieChartPanel(SetInterface<Matching> matchings, int prefW, int prefH) {
            this.prefW = prefW; this.prefH = prefH;
            scoreCounts = control.MatchingScorePieChartControl.getScoreCounts(matchings);
        }
        @Override
        public Dimension getPreferredSize() { return new Dimension(prefW, prefH); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("Number of Matches by Matching Score", 20, 30);
            int width = getWidth();
            int height = getHeight();
            int radius = Math.min(width, height) / 3;
            int centerX = width / 2;
            int centerY = height / 2 + 30;
            int total = 0;
            for (String label : labels) {
                Integer count = scoreCounts.get(label);
                if (count != null) total += count;
            }
            if (total == 0) {
                g2d.drawString("No data", 60, 60);
                return;
            }
            double startAngle = 0.0;
            for (int i = 0; i < labels.length; i++) {
                Integer count = scoreCounts.get(labels[i]);
                int value = (count == null) ? 0 : count;
                double percent = (double) value / total;
                double arcAngle = (i == labels.length - 1) ? 360.0 - startAngle : percent * 360.0;
                g2d.setColor(colors[i]);
                g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, (int) Math.round(startAngle), (int) Math.round(arcAngle));
                startAngle += arcAngle;
            }
            // Draw legend
            int legendX = centerX + radius + 30;
            int legendY = centerY - radius;
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            for (int i = 0; i < labels.length; i++) {
                g2d.setColor(colors[i]);
                g2d.fillRect(legendX, legendY + i * 28, 20, 20);
                g2d.setColor(Color.BLACK);
                Integer count = scoreCounts.get(labels[i]);
                int value = (count == null) ? 0 : count;
                double percent = total == 0 ? 0 : ((double) value / total) * 100;
                String label = String.format("%s  %d (%.0f%%)", labels[i], value, percent);
                g2d.drawString(label, legendX + 28, legendY + i * 28 + 16);
            }
        }
    }
} 
