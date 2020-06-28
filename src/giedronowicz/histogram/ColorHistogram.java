package giedronowicz.histogram;

import giedronowicz.images.ColorImage;
import giedronowicz.images.Image;
import giedronowicz.windowCompontets.ImageFrame;

import java.awt.*;
import java.text.DecimalFormat;

public class ColorHistogram extends Histogram {

    private int[] red;
    private int[] green;
    private int[] blue;

    public ColorHistogram(){}
    public ColorHistogram(int[] red, int[] green, int[] blue){
        super();
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.repaint();
    }
    public ColorHistogram(ColorImage image) {
        super();
        this.calculate( image );
    }

    /** Set the count and display histogram */
    public ColorHistogram calculate( ColorImage image ) {

        this.setName( image.getName() + "-histogram");
        int[] red = new int[256];
        int[] green = new int[256];
        int[] blue = new int[256];

        for(int x = 0; x < image.getWidth(); x++)
        {
            for( int y = 0; y < image.getHeight(); y++)
            {
                int r = image.getPixel(x,y).getRed();
                int g = image.getPixel(x,y).getGreen();
                int b = image.getPixel(x,y).getBlue();
                red[r]++;
                green[g]++;
                blue[b]++;
            }
        }
        this.red = red;
        this.green = green;
        this.blue = blue;
        repaint();
        return this;
    }

    /** Paint the histogram */
    protected void paintComponent(Graphics g) {
        if (red == null || green == null || blue == null) return; // No display if count is null
        super.paintComponent(g);

        // Find the panel size and bar width and interval dynamically
        int width = getWidth();
        int height = getHeight();
        int interval = (width - 40) / 256;
        int individualWidth = (int) (((width - 40) / 256) * 0.60);


        // Find the maximum count. The maximum count has the highest bar
        int maxCount = 0;

        for (int i = 0; i < 256; i++) {
            if (maxCount < red[i])
                maxCount = red[i];

            if (maxCount < green[i])
                maxCount = green[i];

            if (maxCount < blue[i])
                maxCount = blue[i];
        }

        // x is the start position for the first bar in the histogram
        int x = 40;
        int maxPercentHeight = 0;

        int barHeightRedPrev = 0;
        int barHeightGreenPrev = 0;
        int barHeightBluePrev = 0;

        for (int i = 0; i < 256; i++) {
            // Find the bar height
            int barHeightRed = (int) (((double) red[i] / (double) maxCount) * (height - 55));
            int barHeightGreen = (int) (((double) green[i] / (double) maxCount) * (height - 55));
            int barHeightBlue = (int) (((double) blue[i] / (double) maxCount) * (height - 55));

            if (red[i] == maxCount)
                maxPercentHeight = barHeightRed;
            if (green[i] == maxCount)
                maxPercentHeight = barHeightGreen;
            if (blue[i] == maxCount)
                maxPercentHeight = barHeightBlue;

            // Display a bar (i.e. rectangle)
            if( red[i] > 0 ) {
                g.setColor( Color.RED );
                //g.drawRect(x, height - 45 - barHeightRed, individualWidth, barHeightRed);
                g.drawLine(x,height - 45 - barHeightRedPrev, x + individualWidth, height - 45 - barHeightRed);
            }

            if( green[i] > 0 ) {
                g.setColor(Color.GREEN);
                //g.drawRect(x, height - 45 - barHeightGreen, individualWidth, barHeightGreen);
                g.drawLine(x,height - 45 - barHeightGreenPrev, x + individualWidth, height - 45 - barHeightGreen);
            }

            if( blue[i] > 0 ) {
                g.setColor( Color.BLUE );
                //g.drawRect(x, height - 45 - barHeightBlue, individualWidth, barHeightBlue);
                g.drawLine(x,height - 45 - barHeightBluePrev, x + individualWidth, height - 45 - barHeightBlue);
            }

            g.setColor( Color.BLACK );
            // Display a value under
            if (i % 20 == 0)
                g.drawString(Integer.toString(i) + "", x-5, height - 30);


            // Move x for displaying the next val
            x += interval;
            barHeightRedPrev = barHeightRed;
            barHeightGreenPrev = barHeightGreen;
            barHeightBluePrev = barHeightBlue;

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

    public int[] getRed() {
        return this.red;
    }
    public int[] getGreen() {
        return this.green;
    }
    public int[] getBlue() { return this.blue; }

    public ColorHistogram getRedHistogram() {
        ColorHistogram histogram = this.clone();
        histogram.green = new int[256];
        histogram.blue = new int[256];
        histogram.setName( "red-histogram ");
        histogram.repaint();
        return histogram;
    }
    public ColorHistogram getGreenHistogram() {
        ColorHistogram histogram = new ColorHistogram( new int[256], this.green , new int[256] );
        histogram.setName( "green-histogram ");
        histogram.repaint();
        return histogram;
    }
    public ColorHistogram getBlueHistogram() {
        ColorHistogram histogram = new ColorHistogram( new int[256], new int[256] , this.blue );
        histogram.setName( "blue-histogram ");
        histogram.repaint();
        return histogram;
    }

    public int getRedGlobal() {
        final int[] histogram = this.red;
        int globalThreshold = 127;
        int tmp;
        do {
            tmp = globalThreshold;
            // avg value of black - ie. pixel to the left of the threshold
            int pixelVal = 0, pixelCount = 0;
            for( int i = 0; i < globalThreshold; i++)
            {
                pixelVal += histogram[i] * i;
                pixelCount += histogram[i];
            }
            float avgBlack = (float)pixelVal / pixelCount;

            // avg value of red - ie. pixel to the right of the threshold
            pixelVal = 0;
            pixelCount = 0;
            for( int i = globalThreshold; i < 255; i++)
            {
                pixelVal += histogram[i] * i;
                pixelCount += histogram[i];
            }
            float avgRed = (float)pixelVal / pixelCount;

            globalThreshold = (int) ( avgBlack + avgRed) / 2;
        } while ( tmp != globalThreshold);

        System.out.println( "RedGlobalThreshold = " + globalThreshold );
        return globalThreshold;
    }
    public int getGreenGlobal() {
        final int[] histogram = this.green;
        int globalThreshold = 127;
        int tmp;
        do {
            tmp = globalThreshold;
            // avg value of black - ie. pixel to the left of the threshold
            int pixelVal = 0, pixelCount = 0;
            for( int i = 0; i < globalThreshold; i++)
            {
                pixelVal += histogram[i] * i;
                pixelCount += histogram[i];
            }
            float avgBlack = (float)pixelVal / pixelCount;

            // avg value of red - ie. pixel to the right of the threshold
            pixelVal = 0;
            pixelCount = 0;
            for( int i = globalThreshold; i < 255; i++)
            {
                pixelVal += histogram[i] * i;
                pixelCount += histogram[i];
            }
            float avgGreen = (float)pixelVal / pixelCount;

            globalThreshold = (int) ( avgBlack + avgGreen) / 2;
        } while ( tmp != globalThreshold);

        System.out.println( "GreenGlobalThreshold = " + globalThreshold );
        return globalThreshold;
    }
    public int getBlueGlobal() {
        final int[] histogram = this.blue;
        int globalThreshold = 127;
        int tmp;
        do {
            tmp = globalThreshold;
            // avg value of black - ie. pixel to the left of the threshold
            int pixelVal = 0, pixelCount = 0;
            for( int i = 0; i < globalThreshold; i++)
            {
                pixelVal += histogram[i] * i;
                pixelCount += histogram[i];
            }
            float avgBlack = (float)pixelVal / pixelCount;

            // avg value of red - ie. pixel to the right of the threshold
            pixelVal = 0;
            pixelCount = 0;
            for( int i = globalThreshold; i < 255; i++)
            {
                pixelVal += histogram[i] * i;
                pixelCount += histogram[i];
            }
            float avgBlue = (float)pixelVal / pixelCount;

            globalThreshold = (int) ( avgBlack + avgBlue) / 2;
        } while ( tmp != globalThreshold);

        System.out.println( "BlueGlobalThreshold = " + globalThreshold );
        return globalThreshold;
    }

    public ColorHistogram clone() {
        ColorHistogram histogram = new ColorHistogram( new int[256], new int[256], new int[256]);
        for( int i = 0; i < this.red.length; i++)
        {
            histogram.red[i] = this.red[i];
            histogram.green[i] = this.green[i];
            histogram.blue[i] = this.blue[i];
        }
        histogram.repaint();
        histogram.setName( this.getName() );
        return histogram;
    }


}
