package giedronowicz.images;

import giedronowicz.histogram.GrayHistogram;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class GrayImage extends Image {

    // Constructors

    public GrayImage( int width, int height) {
        this.setImage( new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY) );
    }
    public GrayImage(String fileName) {
        super(fileName);
    }
    public GrayImage(Image image) {
        super(image);
    }
    public GrayImage(final ColorImage colorImage){
        super(colorImage.clone());
        ColorImage image = new ColorImage( colorImage );
        for( int x = 0; x < image.getWidth(); x++)
        {
            for( int y = 0; y < image.getHeight(); y++ )
            {
                int gray = ( image.getPixel(x,y).getRed() + image.getPixel(x,y).getGreen() + image.getPixel(x,y).getBlue() )/3;
                Color color = new Color(gray,gray,gray);
                this.setRGB(x,y, color.getRGB());
            }
        }
    }



    // Ujednolicenie

    /**
     * Metoda geometrycznego ujednolicenia obrazów.
     * Metoda obrysowuje obraz w czarną ramkę zwiększając jej fizyczne wymiary
     * do tych podanych w argumencie
     *
     * @param width Szerokośc nowego obrazu w ramce
     * @param height Wysokość nowego obrazu w ramce
     * @return Obraz wypośrodkowany w czarnej ramce
     */
    public GrayImage putInFrame( int width, int height ) {
        if( width < this.getWidth() || height < this.getHeight())
            return this;

        GrayImage scaled = new GrayImage(width , height);
        scaled.setName( this.getName() + "-unify-geometric");
        int startX = (scaled.getWidth() - this.getWidth()) /2;
        int startY = (scaled.getHeight() - this.getHeight()) /2;

        // Prostokąt o wymiarach i lokalizacji wysrodkowanego obrazu
        Rectangle imgRect = new Rectangle();
        imgRect.setSize(this.getWidth(), this.getHeight());
        imgRect.setLocation( startX , startY );

        for( int x = 0; x < scaled.getWidth(); x++)
        {
            for( int y = 0; y < scaled.getHeight() && scaled.isInRange(x,y); y++)
            {
                if(imgRect.contains(x,y))
                    scaled.setRGB(x,y, this.getRGB(x - startX, y - startY));
                else
                    scaled.setRGB(x,y, Color.BLACK.getRGB() );
            }
        }

        return scaled;
    }

    /**
     * Jesli obraz {@code image} jest większy od {@code this} buduje dookoła czarną ramkę
     * i zwraca nowy obraz o wymiarach większego obrazu.
     * W przeciwnym wypadku nie robi nic i zwraca nie zmieniony {@code this}.
     */
    public GrayImage unifyGeometric(GrayImage image ) {
        GrayImage newImage = new GrayImage(this);
        if( newImage.isSmaller(image))
            return newImage.putInFrame(
                    Math.max(newImage.getWidth(), image.getWidth()) ,
                    Math.max(newImage.getHeight(), image.getHeight())
            );
        else
            return newImage;

    }
    public GrayImage unifyRaster( int width, int height ) {
        GrayImage res = new GrayImage( width, height);
        res.setName( this.getName() );

        float scaleW = (float)width / (float)this.getWidth();
        float scaleH = (float)height/ (float)this.getHeight();

        for( int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                int newX = Math.round( x * scaleW );
                int newY = Math.round( y * scaleH );
                res.setRGB(newX, newY , this.getPixel(x,y).getRGB() );
            }
        }

        return res;
    }

    public GrayImage interpolate() {

        GrayImage res = new GrayImage( this.clone() );
        for( int x = 0;  x < res.getWidth(); x++)
        {
            for( int y = 0; y < res.getHeight(); y++)
            {
                try
                {
                    //System.out.println("x = " + x + " y = " + y);
                    GrayPixel pixel = res.getPixel(x,y);
                    if( this.getPixel(x,y).getGray() == 0 )     // pixel is empty
                    {
                        int val = 0;
                        int n = 0;
                        for( int xx = pixel.getX() - 1; xx <= pixel.getX() + 1; xx++)
                        {
                            for( int yy = pixel.getY() - 1; yy <= pixel.getY() + 1; yy++)
                            {
                                if( res.isInRange(xx,yy) && res.getPixel(xx,yy).getGray() != 0 )
                                {
                                    val += res.getPixel(xx,yy).getGray();
                                    n++;
                                }
                            }
                        }
                        pixel.setGray( val/n );
                        res.setRGB(x,y, pixel.getRGB() );
                    }
                }
                catch ( Exception e)
                {
                    System.out.println("----> x=" + x + " y=" + y);
                    System.exit(-1);
                }
            }
        }
        return res;
    }
    // Arithmetic Operations

    public GrayImage sum( int param ) {
        GrayImage resImg = new GrayImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-sum-" + param );

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                GrayPixel p = this.getPixel(x,y);
                GrayPixel resPixel = GrayPixel.plus(p,param);
                resImg.setRGB(x,y,  resPixel.getRGB() );
            }
        }
        return resImg;
    }
    public GrayImage sum( GrayImage image ) {
        GrayImage resImg = new GrayImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-sum-" + image.getName() );

        boolean outRange = false;
        int max = 0;
        int min = 255;
        int[][] sum = new int[this.getWidth()][this.getHeight()];

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                sum[x][y] = this.getPixel(x,y).getGray() + image.getPixel(x,y).getGray();
                if(sum[x][y] > 255){
                    outRange = true;
                    max = Math.max(max , sum[x][y]);
                }
                min = Math.min(min , sum[x][y]);

            }
        }
        if( !outRange ) {
            max = 255;
            min = 0;
        }
        for( int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                int resGray = Math.round(
                        255* ( Float.sum(sum[x][y], - min) /
                                Float.sum(max,- min) )
                );
                Color resRGB = new Color(resGray,resGray,resGray);
                resImg.setRGB(x,y, resRGB.getRGB() );
            }
        }

        return resImg;
    }

    public GrayImage multiply(float param ) {
        GrayImage resImg = new GrayImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-multiply-" + param );

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                GrayPixel resPixel = GrayPixel.multiply(this.getPixel(x,y) , param);
                resImg.setRGB(x,y,  resPixel.getRGB() );
            }
        }
        return resImg;
    }
    public GrayImage multiply( GrayImage image ) {
        GrayImage resImg = new GrayImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-multiply-" + image.getName() );

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                float tmpGray = (float) this.getPixel(x,y).getGray() / 255;
                int resGray = Math.round( tmpGray * image.getPixel(x,y).getGray() );
                Color resColor = new Color(resGray,resGray,resGray);
                resImg.setRGB(x,y,  resColor.getRGB() );
            }
        }
        return resImg;
    }

    public GrayImage mix( GrayImage image, float param) {
        GrayImage resImg = new GrayImage( this.getWidth(), this.getHeight() );

        GrayImage first = this.multiply(param);
        GrayImage second = image.multiply(1 - param);
        resImg = first.sum(second);

        resImg.setName( this.getName() + "-mix-" + image.getName() + "-" + param );
        return resImg;
    }

    public GrayImage pow( float param ) {
        GrayImage resImg = new GrayImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-pow-" + param );

        int max = this.getMax();
        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                double tmpGray = (double) this.getPixel(x,y).getGray() / max;
                tmpGray = Math.pow(tmpGray, param);
                int resGray = (int) Math.round( tmpGray*255 );
                Color resColor = new Color(resGray,resGray,resGray);
                resImg.setRGB(x,y,  resColor.getRGB() );
            }
        }
        return resImg;
    }

    public GrayImage divide( float param ) {
        GrayImage resImg = this.multiply( 1 / param);
        resImg.setName(this.getName() + "-div-" + param );
        return resImg;
    }
    public GrayImage divide( GrayImage image ) {
        GrayImage resImg = new GrayImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-div-" + image.getName() );

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                float tmpGray = (float) this.getPixel(x,y).getGray() / (float) image.getPixel(x,y).getGray();
                // Skalujemy do oryginalnego przedziału
                tmpGray = tmpGray < 1 ? tmpGray * 255 : tmpGray;
                int resGray = Math.round( tmpGray );
                Color resColor = new Color(resGray,resGray,resGray);
                resImg.setRGB(x,y,  resColor.getRGB() );
            }
        }
        return resImg;
    }

    public GrayImage root( float param ) {
        GrayImage resImg = this.pow( 1/ param);
        resImg.setName(this.getName() + "-root-" + param );
        return resImg;
    }
    public GrayImage log() {
        GrayImage resImg = new GrayImage( this.getWidth(), this.getHeight() );
        resImg.setName(this.getName() + "-log");

        int max = this.getMax();
        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                int resRGB = (int) Math.round(
                        255* ( Math.log10(1 + this.getPixel(x,y).getGray() ) / Math.log10(1 + max ) )
                );
                Color resColor = new Color(resRGB,resRGB,resRGB);
                resImg.setRGB(x,y, resColor.getRGB() );
            }
        }

        return resImg;
    }


    // Histogram operation
    public GrayImage histogramStretching() {
        GrayImage resImg = new GrayImage( this.getWidth(), this.getHeight() );
        resImg.setName(this.getName() + "-his-stretch");
        int max = this.getMax();
        int min = this.getMin();

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                float gray = 255 / (Float.sum(max, -min));
                gray *= (this.getPixel(x,y).getGray() - min);
                int g = (int)gray;
                resImg.setRGB(x,y, new Color(g,g,g).getRGB() );
            }
        }

        return resImg;
    }

    public GrayImage localThreshold(int size, int epsilon , int globalThreshold ){
        GrayImage resImg = new GrayImage( this.getWidth(), this.getHeight() );
        resImg.setName(this.getName() + "-binary");
        if( size % 2 == 0) size++;

        for( int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                int dist = size /2; // sasiedzi : [p-dist , p+dist]
                GrayPixel pixel = this.getPixel(x,y);

                if(  pixel.getX() - dist < 0 || this.getWidth() < pixel.getX() + dist
                        || pixel.getY() - dist < 0 || this.getHeight() < pixel.getY() + dist )
                {
                    // get dist = dist or smaller if we are at the border
                    dist = Math.min(
                            Math.min( pixel.getX(), Math.abs(pixel.getX() - this.getWidth()) -1 ),
                            Math.min( pixel.getY(), Math.abs(pixel.getY() - this.getHeight())-1 )
                    ); // min because we don't know which border
                }
                // get part of the image with fixed dimensions
                Rectangle region =
                        new Rectangle(pixel.getX()-dist,pixel.getY()-dist,2*dist+1,2*dist+1);
                GrayImage regionImage = new GrayImage( this.getImageRegion( region ) );

                if( regionImage.getMax() - regionImage.getMin() <= epsilon )
                {
                    if( this.getPixel(x,y).getGray() < globalThreshold )
                        resImg.setRGB(x,y,new Color(0,0,0).getRGB() );
                    else
                        resImg.setRGB(x,y, new Color(255,255,255).getRGB() );
                }
                else
                {
                    int threshold = ( regionImage.getMin() + regionImage.getMax() ) / 2;
                    if( this.getPixel(x,y).getGray() < threshold )
                        resImg.setRGB(x,y,new Color(0,0,0).getRGB() );
                    else
                        resImg.setRGB(x,y, new Color(255,255,255).getRGB() );
                }

            }
        }
        return resImg;
    }
    public GrayImage globalThreshold(GrayHistogram histogram ) {
        return this.globalThreshold( histogram.getCount() );
    }
    public GrayImage globalThreshold(int[] histogram ) {

        GrayImage resImage = new GrayImage( this.getWidth(), this.getHeight());
        resImage.setName( this.getName() + "-globalBinary");
        int globalThreshold = 150;
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

            // avg value of white - ie. pixel to the right of the threshold
            pixelVal = 0;
            pixelCount = 0;
            for( int i = globalThreshold; i < 255; i++)
            {
                pixelVal += histogram[i] * i;
                pixelCount += histogram[i];
            }
            float avgWhite = (float)pixelVal / pixelCount;

            globalThreshold = (int) ( avgBlack + avgWhite) / 2;
        } while ( tmp != globalThreshold);

        System.out.println( "GlobalThreshold = " + globalThreshold );
        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                if( this.getPixel(x,y).getGray() < globalThreshold )
                    resImage.setRGB(x,y, Color.BLACK.getRGB() );
                else
                    resImage.setRGB(x,y, Color.WHITE.getRGB() );
            }
        }

        return resImage;
    }

    public static int getGlobalThreshold(int[] histogram ) {
        int globalThreshold = 255 / 2;
        int tmp;

        do {
            tmp = globalThreshold;
            // avg value of black - ie. pixel to the left of the threshold
            int pixelVal = 0, pixelCount = 0;
            for (int i = 0; i < globalThreshold; i++) {
                pixelVal += histogram[i] * i;
                pixelCount += histogram[i];
            }
            float avgBlack = (float) pixelVal / pixelCount;

            // avg value of white - ie. pixel to the right of the threshold
            pixelVal = 0;
            pixelCount = 0;
            for (int i = globalThreshold; i < 255; i++) {
                pixelVal += histogram[i] * i;
                pixelCount += histogram[i];
            }
            float avgWhite = (float) pixelVal / pixelCount;

            globalThreshold = (int) (avgBlack + avgWhite) / 2;
        } while (tmp != globalThreshold);
        System.out.println( globalThreshold );
        return globalThreshold;
    }
    // Getters And Setters

    public int getMin(){
        int min = 255;
        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                if(this.getPixel(x,y).getGray() < min) {
                    min = this.getPixel(x,y).getGray();
                }
            }
        }
        return min;
    }
    public int getMax(){
        int max = 0;
        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                if(this.getPixel(x,y).getGray() > max) {
                    max = this.getPixel(x,y).getGray();
                }
            }
        }
        return max;
    }
    public GrayPixel getPixel( int x, int y)
    {
        return new GrayPixel(x,y, this.getRGB(x,y));
    }

}
