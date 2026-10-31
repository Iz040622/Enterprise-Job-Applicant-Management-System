package boundary;

import control.SearchService;
import adt.MapInterface;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class ApplicantKeywordPieChartUI extends JFrame {
    public ApplicantKeywordPieChartUI() {
        super("Frequently Searched Keywords (Applicants)");
        setLayout(new BorderLayout());
        add(new KeywordPieChartPanel(SearchService.matchingKeywordCount, 600, 600), BorderLayout.CENTER);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private static class KeywordPieChartPanel extends JPanel {
        private final MapInterface<String, Integer> keywordCounts;
        private final int prefW, prefH;
        private final Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.CYAN, Color.GREEN, Color.PINK, Color.MAGENTA, Color.LIGHT_GRAY, Color.BLUE, Color.DARK_GRAY};

        public KeywordPieChartPanel(MapInterface<String, Integer> keywordCounts, int prefW, int prefH) {
            this.keywordCounts = keywordCounts;
            this.prefW = prefW;
            this.prefH = prefH;
        }

        @Override
        public Dimension getPreferredSize() { return new Dimension(prefW, prefH); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("Frequently Searched Keywords", 20, 30);

            int width = getWidth();
            int height = getHeight();
            int radius = Math.min(width, height) / 3;
            int centerX = width / 2;
            int centerY = height / 2 + 30;

            // Calculate total searches
            int total = 0;
            Iterator<String> it = keywordCounts.getKeys().getIterator();
            while (it.hasNext()) {
                String keyword = it.next();
                Integer count = keywordCounts.get(keyword);
                if (count != null) total += count;
            }
            if (total == 0) {
                g2d.drawString("No data", 60, 60);
                return;
            }

            // Draw pie chart
            double startAngle = 0.0;
            int colorIdx = 0;
            it = keywordCounts.getKeys().getIterator();
            while (it.hasNext()) {
                String keyword = it.next();
                Integer count = keywordCounts.get(keyword);
                if (count == null || count == 0) continue;
                double percent = (double) count / total;
                double arcAngle = percent * 360.0;
                g2d.setColor(colors[colorIdx % colors.length]);
                g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, (int) Math.round(startAngle), (int) Math.round(arcAngle));
                startAngle += arcAngle;
                colorIdx++;
            }

            // Draw legend
            int legendX = centerX + radius + 30;
            int legendY = centerY - radius;
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            colorIdx = 0;
            it = keywordCounts.getKeys().getIterator();
            while (it.hasNext()) {
                String keyword = it.next();
                Integer count = keywordCounts.get(keyword);
                if (count == null || count == 0) continue;
                g2d.setColor(colors[colorIdx % colors.length]);
                g2d.fillRect(legendX, legendY + colorIdx * 28, 20, 20);
                g2d.setColor(Color.BLACK);
                double percent = total == 0 ? 0 : ((double) count / total) * 100;
                String label = String.format("%s (%.0f%%)", keyword, percent);
                g2d.drawString(label, legendX + 28, legendY + colorIdx * 28 + 16);
                colorIdx++;
            }
        }
    }
} 