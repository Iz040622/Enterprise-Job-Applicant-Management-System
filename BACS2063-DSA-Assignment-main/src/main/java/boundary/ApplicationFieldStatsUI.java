/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;
import adt.*;
import entity.*;
import control.*;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 *
 * @author Axinzen Lau
 */
public class ApplicationFieldStatsUI extends JFrame {
    public ApplicationFieldStatsUI(Employer employer) {
        super("Application Field/Title Statistics Report");
        SetInterface<Job> jobList = employer.getJobList();
        SetInterface<Application> applicationSet = new TreeSet<>();
        Iterator<Job> jobIt = jobList.getIterator();
        while (jobIt.hasNext()) {
            Job empJob = jobIt.next();
            Iterator<Application> appIt = empJob.getApplicationList().getIterator();
            while (appIt.hasNext()) {
                applicationSet.add(appIt.next());
            }
        }
        setLayout(new GridLayout(1, 2));
        add(new FieldPieChartPanel(applicationSet, 500, 500));
        add(new SuccessRateBarChartPanel(applicationSet, 500, 500));
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Pie chart: Application portion by field/job title
    private static class FieldPieChartPanel extends JPanel {
        private final MapInterface<String, Integer> fieldCounts;
        private final int prefW, prefH;
        public FieldPieChartPanel(SetInterface<Application> applications, int prefW, int prefH) {
            this.prefW = prefW; this.prefH = prefH;
            fieldCounts = control.ApplicationFieldStatsControl.getFieldCounts(applications);
        }
        @Override
        public Dimension getPreferredSize() { return new Dimension(prefW, prefH); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("Application Portion by Field", 20, 30);
            if (fieldCounts.size() == 0) {
                g2d.drawString("No data", 60, 60);
                return;
            }
            int width = getWidth();
            int height = getHeight();
            int radius = Math.min(width, height) / 3 - 10;
            int centerX = width / 2 - 20;
            int centerY = height / 2 + 10;
            int total = 0;
            SetInterface<String> keys = fieldCounts.getKeys();
            Iterator<String> keyIt = keys.getIterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                total += fieldCounts.get(key);
            }
            double startAngle = 0.0;
            Color[] colors = {Color.CYAN, Color.PINK, Color.MAGENTA, Color.YELLOW, Color.LIGHT_GRAY, Color.ORANGE, Color.GREEN, Color.RED, Color.BLUE};
            int i = 0;
            keyIt = keys.getIterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                int value = fieldCounts.get(key);
                double percent = total == 0 ? 0.0 : (value * 1.0 / total);
                double arcAngle = percent * 360.0;
                g2d.setColor(colors[i % colors.length]);
                g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, (int) Math.round(startAngle), (int) Math.round(arcAngle));
                // Draw border for each segment
                g2d.setColor(Color.BLACK);
                g2d.drawArc(centerX - radius, centerY - radius, radius * 2, radius * 2, (int) Math.round(startAngle), (int) Math.round(arcAngle));
                startAngle += arcAngle;
                i++;
            }
            // Draw legend
            int legendX = centerX + radius + 20;
            int legendY = centerY - radius;
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            i = 0;
            keyIt = keys.getIterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                g2d.setColor(colors[i % colors.length]);
                g2d.fillRect(legendX, legendY + i * 24, 16, 16);
                g2d.setColor(Color.BLACK);
                int value = fieldCounts.get(key);
                double percent = total == 0 ? 0.0 : (value * 100.0 / total);
                String label = key + " " + value + " (" + String.format("%.0f", percent) + "%)";
                g2d.drawString(label, legendX + 22, legendY + i * 24 + 13);
                i++;
            }
        }
    }

    // Bar chart: Success rate by field/job title
    private static class SuccessRateBarChartPanel extends JPanel {
        private final MapInterface<String, Integer> totalMap;
        private final MapInterface<String, Integer> approvedMap;
        private final int prefW, prefH;
        public SuccessRateBarChartPanel(SetInterface<Application> applications, int prefW, int prefH) {
            this.prefW = prefW; this.prefH = prefH;
            totalMap = control.ApplicationFieldStatsControl.getTotalCounts(applications);
            approvedMap = control.ApplicationFieldStatsControl.getApprovedCounts(applications);
        }
        
        @Override
        public Dimension getPreferredSize() { 
            return new Dimension(prefW, prefH); 
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("Success Rate by Field", 20, 30);
            if (totalMap.size() == 0) {
                g2d.drawString("No data", 60, 60);
                return;
            }
            int width = getWidth();
            int height = getHeight();
            int leftPadding = 80; // More space for y-axis label
            int bottomPadding = 80; // More space for x-axis label
            int x0 = leftPadding, y0 = height - bottomPadding;
            int chartHeight = height - 2 * bottomPadding;
            int chartWidth = width - leftPadding - 20; // 20 for right margin
            int barWidth = Math.max(15, chartWidth / Math.max(1, totalMap.size()) - 8);
            int max = 1;
            SetInterface<String> keys = totalMap.getKeys();
            Iterator<String> keyIt = keys.getIterator();
            while (keyIt.hasNext()) {
                int total = totalMap.get(keyIt.next());
                if (total > max) max = total;
            }
            int barMargin = 20; // Add a left margin for the first bar
            int i = 0;
            keyIt = keys.getIterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                int total = totalMap.get(key);
                Integer approvedObj = approvedMap.get(key);
                int approved = (approvedObj == null) ? 0 : approvedObj;
                int barHeight = (total == 0) ? 0 : (int) ((approved * 1.0 / total) * (chartHeight - 40));
                int x = x0 + barMargin + i * (barWidth + 8); // Add margin to the first bar
                int y = y0 - barHeight;
                g2d.setColor(new Color(144, 238, 144));
                g2d.fillRect(x, y, barWidth, barHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, barWidth, barHeight);
                // Draw the field label horizontally and bold
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                int labelWidth = g2d.getFontMetrics().stringWidth(key);
                g2d.drawString(key, x + barWidth / 2 - labelWidth / 2, y0 + 30);
                // Draw the success rate on top
                String rate = total == 0 ? "0%" : String.format("%.0f%%", (approved * 100.0 / total));
                g2d.drawString(rate, x + barWidth / 2 - 10, y - 5);
                i++;
            }
            // Y axis
            g2d.drawLine(x0, y0, x0, y0 - chartHeight + 40);
            // X axis
            g2d.drawLine(x0, y0, x0 + chartWidth, y0);
            // Y-axis label (vertical)
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            String yLabel = "Approved Applications";
            FontMetrics fm = g2d.getFontMetrics();
            Graphics2D g2dY = (Graphics2D) g2d.create();
            g2dY.rotate(-Math.PI / 2);
            g2dY.drawString(yLabel, -(y0 - chartHeight / 2 + 20), x0 - 35);
            g2dY.dispose();
            // X-axis label (horizontal)
            String xLabel = "Field";
            int xLabelWidth = fm.stringWidth(xLabel);
            g2d.drawString(xLabel, x0 + (chartWidth - xLabelWidth) / 2, y0 + 60);
        }
    }
} 
