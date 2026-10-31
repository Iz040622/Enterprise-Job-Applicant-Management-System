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
 * @author Axinzen Lau
 */
public class ApplicationStatsUI extends JFrame {
    public ApplicationStatsUI(SetInterface<Applicant> applicantList) {
        this(aggregateApplicationsFromApplicants(applicantList), true);
    }

    public ApplicationStatsUI(SetInterface<Application> applicationSet, boolean isApplicationSet) {
        super("Application Statistics Report");
        SetInterface<Application> allApplications = new adt.TreeSet<>();
        Iterator<Application> applIt = applicationSet.getIterator();
        while (applIt.hasNext()) {
            allApplications.add(applIt.next());
        }
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.5; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(new BarChartPanel(allApplications, 400, 400), gbc);
        gbc.gridx = 1;
        add(new StatusPieChartPanel(allApplications, 400, 400), gbc);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static SetInterface<Application> aggregateApplicationsFromApplicants(SetInterface<Applicant> applicantList) {
        SetInterface<Application> allApplications = new adt.TreeSet<>();
        Iterator<Applicant> appIt = applicantList.getIterator();
        while (appIt.hasNext()) {
            Applicant applicant = appIt.next();
            Iterator<Application> applIt = applicant.getApplicationList().getIterator();
            while (applIt.hasNext()) {
                allApplications.add(applIt.next());
            }
        }
        return allApplications;
    }

    // Bar chart: Applications per month
    private static class BarChartPanel extends JPanel {
        private final MapInterface<String, Integer> monthCounts;
        private final int prefW, prefH;
        public BarChartPanel(SetInterface<Application> applications, int prefW, int prefH) {
            this.prefW = prefW; this.prefH = prefH;
            monthCounts = control.ApplicationStatsControl.getMonthCounts(applications);
        }
        @Override
        public Dimension getPreferredSize() { return new Dimension(prefW, prefH); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("Applications per Month", 20, 30);
            if (monthCounts.size() == 0) {
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
            int barWidth = Math.max(15, chartWidth / Math.max(1, monthCounts.size()) - 8);
            // Find max value
            int max = 1;
            SetInterface<String> keys = monthCounts.getKeys();
            Iterator<String> keyIt = keys.getIterator();
            while (keyIt.hasNext()) {
                int v = monthCounts.get(keyIt.next());
                if (v > max) max = v;
            }
            int barMargin = 20; // Add a left margin for the first bar
            int i = 0;
            keyIt = keys.getIterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                int value = monthCounts.get(key);
                int barHeight = (int) ((value * 1.0 / max) * (chartHeight - 40));
                int x = x0 + barMargin + i * (barWidth + 8); // Add margin to the first bar
                int y = y0 - barHeight;
                g2d.setColor(new Color(100, 149, 237));
                g2d.fillRect(x, y, barWidth, barHeight);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, barWidth, barHeight);
                // Draw the date label horizontally and bold
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                int labelWidth = g2d.getFontMetrics().stringWidth(key);
                g2d.drawString(key, x + barWidth / 2 - labelWidth / 2, y0 + 30);
                g2d.drawString(String.valueOf(value), x + barWidth / 2 - 8, y - 5);
                i++;
            }
            // Y axis
            g2d.drawLine(x0, y0, x0, y0 - chartHeight + 40);
            // X axis
            g2d.drawLine(x0, y0, x0 + chartWidth, y0);
            // Y-axis label (vertical)
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            String yLabel = "Number of Applications";
            FontMetrics fm = g2d.getFontMetrics();
            int yLabelWidth = fm.stringWidth(yLabel);
            Graphics2D g2dY = (Graphics2D) g2d.create();
            g2dY.rotate(-Math.PI / 2);
            g2dY.drawString(yLabel, -(y0 - chartHeight / 2 + 20), x0 - 35);
            g2dY.dispose();
            // X-axis label (horizontal)
            String xLabel = "Month";
            int xLabelWidth = fm.stringWidth(xLabel);
            g2d.drawString(xLabel, x0 + (chartWidth - xLabelWidth) / 2, y0 + 60);
        }
    }

    // Pie chart: Status distribution
    private static class StatusPieChartPanel extends JPanel {
        private final MapInterface<String, Integer> statusCounts;
        private final String[] labels = {"Pending", "Approved", "Rejected"};
        private final Color[] colors = {Color.ORANGE, Color.GREEN, Color.RED};
        private final int prefW, prefH;
        public StatusPieChartPanel(SetInterface<Application> applications, int prefW, int prefH) {
            this.prefW = prefW; this.prefH = prefH;
            statusCounts = control.ApplicationStatsControl.getStatusCounts(applications);
        }
        @Override
        public Dimension getPreferredSize() { return new Dimension(prefW, prefH); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("Application Status Distribution", 20, 30);
            int width = getWidth();
            int height = getHeight();
            int radius = Math.min(width, height) / 3 - 10;
            int centerX = width / 2 - 20;
            int centerY = height / 2 + 10;
            int total = 0;
            for (String label : labels) {
                Integer count = statusCounts.get(label);
                if (count != null) total += count;
            }
            if (total == 0) {
                g2d.drawString("No data", 60, 60);
                return;
            }
            double startAngle = 0.0;
            for (int i = 0; i < labels.length; i++) {
                Integer count = statusCounts.get(labels[i]);
                int value = (count == null) ? 0 : count;
                double percent = (double) value / total;
                double arcAngle = (i == labels.length - 1) ? 360.0 - startAngle : percent * 360.0;
                g2d.setColor(colors[i]);
                g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, (int) Math.round(startAngle), (int) Math.round(arcAngle));
                startAngle += arcAngle;
            }
            // Draw legend
            int legendX = centerX + radius + 20;
            int legendY = centerY - radius;
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            for (int i = 0; i < labels.length; i++) {
                g2d.setColor(colors[i]);
                g2d.fillRect(legendX, legendY + i * 24, 16, 16);
                g2d.setColor(Color.BLACK);
                Integer count = statusCounts.get(labels[i]);
                int value = (count == null) ? 0 : count;
                double percent = total == 0 ? 0.0 : (value * 100.0 / total);
                String label = labels[i] + " " + value + " (" + String.format("%.0f", percent) + "%)";
                g2d.drawString(label, legendX + 22, legendY + i * 24 + 13);
            }
        }
    }

    public static ApplicationStatsUI fromApplications(SetInterface<Application> applicationSet) {
        return null;
    }
} 
