package giedronowicz.histogram;

import giedronowicz.images.GrayImage;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class GrayHistogram extends Histogram {

    // Count the occurrence of 26 letters
    private int[] count;

    public GrayHistogram() {}
    public GrayHistogram(GrayImage image ) {
        super();
        this.calculate( image );
    }

    public int[] getCount() { return this.count; }

    /**
     * Set the count and display histogram
     */
    public int[] calculate( GrayImage image ) {
        this.setName( image.getName() + "-histogram");
        int[] count = new int[256];

        for(int x = 0; x < image.getWidth(); x++)
        {
            for( int y = 0; y < image.getHeight(); y++)
            {
                int gray = image.getPixel(x,y).getGray();
                count[gray]++;
            }
        }
        this.count = count;
        repaint();
        return this.count;
    }

    /**
     * Paint the histogram
     */
    protected void paintComponent(Graphics g) {
        if (count == null) return; // No display if count is null
        super.paintComponent(g);

        // Find the panel size and bar width and interval dynamically
        int width = getWidth();
        int height = getHeight();
        int interval = (width - 40) / count.length;
        int individualWidth = (int) (((width - 40) / 256) * 0.60);


        // Find the maximum count. The maximum count has the highest bar
        int maxCount = 0;
        int sum = 0;

        for (int i = 0; i < count.length; i++) {
            if (maxCount < count[i])
                maxCount = count[i];

            sum += count[i];
        }

        // x is the start position for the first bar in the histogram
        int x = 40;
        int maxPercentHeight = 0;

        for (int i = 0; i < count.length; i++) {
            // Find the bar height
            int barHeight = (int) (((double) count[i] / (double) maxCount) * (height - 55));

            if (count[i] == maxCount)
                maxPercentHeight = barHeight;

            // Display a bar (i.e. rectangle)
            if( count[i] > 0 )
                g.drawRect(x, height - 45 - barHeight, individualWidth, barHeight);

            // Display a value under
            if (i % 20 == 0)
                g.drawString(Integer.toString(i) + "", x-5, height - 30);


            // Move x for displaying the next val
            x += interval;

        }
        g.drawString(Integer.toString(255) + "", x-5, height - 30);


        g.drawString( "--" + new DecimalFormat("#.##").format(maxCount),
                40, height-35 - maxPercentHeight);
        g.drawString( "--" + new DecimalFormat("#.##").format(3*maxCount/4),
                40, height-35 - (3*maxPercentHeight/4) );
        g.drawString( "--" + new DecimalFormat("#.##").format(maxCount/2),
                40, height-35 - (maxPercentHeight/2) );
        g.drawString( "--" + new DecimalFormat("#.##").format(maxCount/4),
                40, height-35 - (maxPercentHeight/4) );

        // Draw a horizontal base line
        //g.drawLine(10, height - 45, width - 10, height - 45);
        g.drawLine(35,height - 45, 35, 10);



    }


}
