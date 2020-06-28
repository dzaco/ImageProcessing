package giedronowicz.images;

import giedronowicz.histogram.ColorHistogram;
import giedronowicz.histogram.GrayHistogram;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ColorImage extends Image {

    // Constructors
    public ColorImage(String fileName) {
        super(fileName);
    }
    public ColorImage(Image image) {
        super(image);
    }
    public ColorImage(int width, int height) {

        this.setImage( new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB) );
    }

    // Unification
    public ColorImage unifyGeometric( int width, int height ) {
        if( width < this.getWidth() || height < this.getHeight())
            return this;

        ColorImage scaled = new ColorImage(width , height);
        scaled.setName( this.getName() + "_with_frame");
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
    public ColorImage unifyRaster( int width, int height ) {
        ColorImage res = new ColorImage( width, height);
        res.setName( this.getName() );
        for( int x = 0; x < res.getWidth(); x++ )
            for( int y = 0; y < res.getHeight(); y++ )
                res.setRGB(x,y, -1);

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

    public ColorImage interpolate() {
        ColorImage res = new ColorImage( this.clone() );
        for( int x = 0;  x < res.getWidth(); x++)
        {
            for( int y = 0; y < res.getHeight(); y++)
            {
                try
                {
                    //System.out.println("x = " + x + " y = " + y);
                    Pixel pixel = res.getPixel(x,y);
                    if( res.getPixel(x,y).getRGB() == -1)     // pixel is empty
                    {
                        int r = 0, g = 0, b = 0;
                        int n = 0;
                        for( int xx = pixel.getX() - 1; xx <= pixel.getX() + 1; xx++)
                        {
                            for( int yy = pixel.getY() - 1; yy <= pixel.getY() + 1; yy++)
                            {
                                if( res.isInRange(xx,yy) && res.getPixel(xx,yy).getRGB() != -1 )
                                {
                                    r += res.getPixel(xx,yy).getRed();
                                    g += res.getPixel(xx,yy).getGreen();
                                    b += res.getPixel(xx,yy).getBlue();
                                    n++;
                                }
                            }
                        }
                        pixel.setColor(new Color(r/n, g/n, b/n));
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
    public ColorImage interpolate2(){
    ColorImage resImage = new ColorImage( this.getWidth(), this.getHeight());
        resImage.setName( this.getName() );
        for( int x = 0; x < this.getWidth(); x++ )
    {
        for( int y = 0; y < this.getHeight(); y++ )
        {
            List<Pixel> pixels = new ArrayList<>();
            if( isInRange(x,y-1))
                pixels.add( new Pixel(x,y-1, this.getRGB(x,y-1)) );
            if( isInRange(x,y+1))
                pixels.add( new Pixel(x,y+1, this.getRGB(x,y+1)) );
            if( isInRange(x-1,y))
                pixels.add( new Pixel(x-1,y, this.getRGB(x-1,y)) );
            if( isInRange(x+1,y))
                pixels.add( new Pixel(x+1,y, this.getRGB(x+1,y)) );

            if( pixels.stream().noneMatch(pixel -> pixel.getColor().getRed() == 0
                    && pixel.getColor().getGreen() == 0
                    && pixel.getColor().getBlue() == 0)

                    && this.getPixel(x,y).getColor().getRed() == 0
                    && this.getPixel(x,y).getColor().getGreen() == 0
                    && this.getPixel(x,y).getColor().getBlue() == 0)
            {
                int r = (int) pixels.stream().mapToDouble(Pixel::getRed).average().getAsDouble();
                int g = (int) pixels.stream().mapToDouble(Pixel::getGreen).average().getAsDouble();
                int b = (int) pixels.stream().mapToDouble(Pixel::getBlue).average().getAsDouble();

                Color color = new Color(r,g,b);
                resImage.setRGB(x,y, color.getRGB());
            }
            else
                resImage.setRGB(x,y, this.getRGB(x,y));
        }
    }
        return resImage;
}

    // Arithmetic Operations

    public ColorImage sum( int param ) {
        ColorImage resImg = new ColorImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-sum-" + param );

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                int red = this.getPixel(x,y).getRed() + param;
                int green = this.getPixel(x,y).getGreen() + param;
                int blue = this.getPixel(x,y).getBlue() + param;

                // Niwelujemy problem wartosci po za zakres
                red = Math.min(red, 255);
                green = Math.min(green, 255);
                blue = Math.min(blue, 255);
                red = Math.max(red, 0);
                green = Math.max(green, 0);
                blue = Math.max(blue, 0);

                Color resColor = new Color(red,green,blue);
                resImg.setRGB(x,y,  resColor.getRGB() );
            }
        }
        return resImg;
    }
    public ColorImage sum( Color color ) {
        ColorImage resImg = new ColorImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-sum-" + color.getRed() + "-" + color.getGreen() + "-" + color.getBlue()  );

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                int red = this.getPixel(x,y).getRed() + color.getRed();
                int green = this.getPixel(x,y).getGreen() + color.getGreen();
                int blue = this.getPixel(x,y).getBlue() + color.getBlue();

                // Niwelujemy problem wartosci powyżej maksymalnej (255)
                red = Math.min(red, 255);
                green = Math.min(green, 255);
                blue = Math.min(blue, 255);

                Color resColor = new Color(red,green,blue);
                resImg.setRGB(x,y,  resColor.getRGB() );
            }
        }
        return resImg;
    }
    public ColorImage sum( ColorImage image ) {
        ColorImage resImg = new ColorImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-sum-" + image.getName() );

        boolean outRange = false;
        int maxR = 0, maxG = 0, maxB = 0;
        int minR = 255, minG = 255, minB = 255;
        int sumR = 0, sumG = 0, sumB = 0;

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                sumR = this.getPixel(x,y).getRed() + image.getPixel(x,y).getRed();
                sumG = this.getPixel(x,y).getGreen() + image.getPixel(x,y).getGreen();
                sumB = this.getPixel(x,y).getBlue() + image.getPixel(x,y).getBlue();
                if(sumR > 255){ outRange = true; maxR = Math.max(maxR , sumR); }
                minR = Math.min(minR , sumR);
                if(sumG > 255){ outRange = true; maxG = Math.max(maxG , sumG); }
                minG = Math.min(minG , sumG);
                if(sumB > 255){ outRange = true; maxB = Math.max(maxB , sumB); }
                minB = Math.min(minB , sumB);

            }
        }
        if( !outRange ) {
            maxR = maxG = maxB = 255;
            minR = minG = minB = 0;
        }
        for( int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                int r = this.getPixel(x,y).getRed() + image.getPixel(x,y).getRed();
                int g = this.getPixel(x,y).getGreen() + image.getPixel(x,y).getGreen();
                int b = this.getPixel(x,y).getBlue() + image.getPixel(x,y).getBlue();

                int resR = Math.round( 255* ( Float.sum(r, - minR) / Float.sum(maxR,- minR) ) );
                int resG = Math.round( 255* ( Float.sum(g, - minG) / Float.sum(maxG,- minG) ) );
                int resB = Math.round( 255* ( Float.sum(b, - minB) / Float.sum(maxB,- minB) ) );

                Color resRGB = new Color(resR,resG,resB);
                resImg.setRGB(x,y, resRGB.getRGB() );
            }
        }

        return resImg;
    }
    public ColorImage sum2( ColorImage image ) {
        ColorImage resImg = new ColorImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-sum-" + image.getName() );

        for( int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                int r = this.getPixel(x,y).getRed() + image.getPixel(x,y).getRed();
                int g = this.getPixel(x,y).getGreen() + image.getPixel(x,y).getGreen();
                int b = this.getPixel(x,y).getBlue() + image.getPixel(x,y).getBlue();

                r /= 2;
                g /= 2;
                b /= 2;


                Color resRGB = new Color(r,g,b);
                resImg.setRGB(x,y, resRGB.getRGB() );
            }
        }

        return resImg;
    }

    public ColorImage multiply(float param ) {
        ColorImage resImg = new ColorImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-multiply-" + param );

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                int r = Math.round( this.getPixel(x,y).getRed() * param );
                int g = Math.round( this.getPixel(x,y).getGreen() * param );
                int b = Math.round( this.getPixel(x,y).getBlue() * param );

                r = Math.min( r, 255 );
                g = Math.min( g, 255 );
                b = Math.min( b, 255 );

                Color resColor = new Color(r,g,b);
                resImg.setRGB(x,y,  resColor.getRGB() );
            }
        }
        return resImg;
    }
    public ColorImage multiply( ColorImage image ) {
        ColorImage resImg = new ColorImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-multiply-" + image.getName() );

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                float tmpR = (float) this.getPixel(x,y).getRed() / 255;
                float tmpG = (float) this.getPixel(x,y).getGreen()/ 255;
                float tmpB = (float) this.getPixel(x,y).getBlue() / 255;

                int resR = Math.round( tmpR * image.getPixel(x,y).getRed() );
                int resG = Math.round( tmpG * image.getPixel(x,y).getGreen() );
                int resB = Math.round( tmpB * image.getPixel(x,y).getBlue() );

                Color resColor = new Color(resR, resG, resB);
                resImg.setRGB(x,y,  resColor.getRGB() );
            }
        }
        return resImg;
    }

    public ColorImage mix( ColorImage image, float param) {
        ColorImage first = this.multiply(param);
        ColorImage second = image.multiply(1 - param);
        ColorImage resImg = first.sum(second);

        resImg.setName( this.getName() + "-mix-" + image.getName() + "-" + param );
        return resImg;
    }

    public ColorImage pow( float param ) {
        ColorImage resImg = new ColorImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-pow-" + param );

        int[] maxColors = this.getMaxColors();
        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                double tmpR = (double) this.getPixel(x,y).getRed() / maxColors[0];
                double tmpG = (double) this.getPixel(x,y).getGreen()/ maxColors[1];
                double tmpB = (double) this.getPixel(x,y).getBlue() / maxColors[2];
                tmpR = Math.pow(tmpR, param);
                tmpG = Math.pow(tmpG, param);
                tmpB = Math.pow(tmpB, param);
                int resR = (int) Math.round( tmpR*255 );
                int resG = (int) Math.round( tmpG*255 );
                int resB = (int) Math.round( tmpB*255 );
                Color resColor = new Color(resR,resG,resB);
                resImg.setRGB(x,y,  resColor.getRGB() );
            }
        }
        return resImg;
    }

    public ColorImage divide( float param ) {
        ColorImage resImg = this.multiply( 1 / param);
        resImg.setName(this.getName() + "-div-" + param );
        return resImg;
    }
    public ColorImage divide( ColorImage image ) {
        ColorImage resImg = new ColorImage(this.getWidth(), this.getHeight());
        resImg.setName(this.getName() + "-div-" + image.getName() );

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                float tmpR = (float) this.getPixel(x,y).getRed() / (float) image.getPixel(x,y).getRed();
                float tmpG = (float) this.getPixel(x,y).getGreen() / (float) image.getPixel(x,y).getGreen();
                float tmpB = (float) this.getPixel(x,y).getBlue() / (float) image.getPixel(x,y).getBlue();
                // Skalujemy do oryginalnego przedziału
                tmpR = tmpR < 1 ? tmpR * 255 : tmpR;
                tmpG = tmpG < 1 ? tmpG * 255 : tmpG;
                tmpB = tmpB < 1 ? tmpB * 255 : tmpB;
                int resR = Math.round( tmpR );
                int resG = Math.round( tmpG );
                int resB = Math.round( tmpB );
                Color resColor = new Color(resR,resG,resB);
                resImg.setRGB(x,y,  resColor.getRGB() );
            }
        }
        return resImg;
    }

    public ColorImage root( float param ) {
        ColorImage resImg = this.pow( 1/ param);
        resImg.setName(this.getName() + "-root-" + param );
        return resImg;
    }
    public ColorImage log() {
        ColorImage resImg = new ColorImage( this.getWidth(), this.getHeight() );
        resImg.setName(this.getName() + "-log");

        int[] maxColors = this.getMaxColors();
        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                int resR = (int) Math.round(
                        255* ( Math.log10(1 + this.getPixel(x,y).getRed() ) / Math.log10(1 + maxColors[0] ) ) );
                int resG = (int) Math.round(
                        255* ( Math.log10(1 + this.getPixel(x,y).getGreen() ) / Math.log10(1 + maxColors[1] ) ) );
                int resB = (int) Math.round(
                        255* ( Math.log10(1 + this.getPixel(x,y).getBlue() ) / Math.log10(1 + maxColors[2] ) ) );
                Color resColor = new Color(resR,resG,resB);
                resImg.setRGB(x,y, resColor.getRGB() );
            }
        }

        return resImg;
    }

    // Histogram operation
    public ColorImage histogramStretching() {
        ColorImage resImg = new ColorImage( this.getWidth(), this.getHeight() );
        resImg.setName(this.getName() + "-his-stretch");
        int[] maxs = this.getMaxColors();
        int[] mins = this.getMinColors();

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                float red = 255 / (Float.sum(maxs[0], -mins[0]));
                red *= (this.getPixel(x,y).getRed() - mins[0]);
                int r = (int)red;

                float green = 255 / (Float.sum(maxs[1], -mins[1]));
                green *= (this.getPixel(x,y).getGreen() - mins[1]);
                int g = (int)green;

                float blue = 255 / (Float.sum(maxs[2], -mins[2]));
                blue *= (this.getPixel(x,y).getBlue() - mins[2]);
                int b = (int)blue;


                resImg.setRGB(x,y, new Color(r,g,b).getRGB() );
            }
        }

        return resImg;
    }
    public ColorImage globalThreshold(ColorHistogram histogram ) {

        int redThreshold = histogram.getRedGlobal();
        int greenThreshold = histogram.getGreenGlobal();
        int blueThreshold = histogram.getBlueGlobal();

       return this.globalThreshold( redThreshold, greenThreshold, blueThreshold );
    }
    public ColorImage globalThreshold( int tr, int tg, int tb) {
        ColorImage resImage = new ColorImage( this.getWidth(), this.getHeight());
        resImage.setName( this.getName() + "-global");
        int redThreshold = tr;
        int greenThreshold = tg;
        int blueThreshold = tb;

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                int r,g,b;

                if( this.getPixel(x,y).getRed() < redThreshold ) r = 0;
                else r = 255;

                if( this.getPixel(x,y).getGreen() < greenThreshold ) g = 0;
                else g = 255;

                if( this.getPixel(x,y).getBlue() < blueThreshold ) b = 0;
                else b = 255;

                resImage.setRGB(x,y, new Color(r,g,b).getRGB() );
            }
        }

        return resImage;
    }
    public ColorImage localThreshold(int size, int epsilon , int globalThreshold ){
        ColorImage resImg = new ColorImage( this.getWidth(), this.getHeight() );
        resImg.setName(this.getName() + "-local");
        if( size % 2 == 0) size++;

        for( int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                int dist = size /2; // sasiedzi : [p-dist , p+dist]
                Pixel pixel = this.getPixel(x,y);

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
                ColorImage regionImage = new ColorImage( this.getImageRegion( region ) );

                int[] maxs = regionImage.getMaxColors();
                int[] mins = regionImage.getMinColors();
                int r,g,b;
                if( maxs[0] - mins[0] <= epsilon ) {
                    if( this.getPixel(x,y).getRed() < globalThreshold ) r = 0;
                    else r = 255;
                }
                else {
                    int threshold = ( maxs[0] + mins[0] ) / 2;
                    if( this.getPixel(x,y).getRed() < threshold ) r = 0;
                    else r = 255;
                }
                if( maxs[1] - mins[1] <= epsilon ) {
                    if( this.getPixel(x,y).getGreen() < globalThreshold ) g = 0;
                    else g = 255;
                }
                else {
                    int threshold = ( maxs[1] + mins[1] ) / 2;
                    if( this.getPixel(x,y).getGreen() < threshold ) g = 0;
                    else g = 255;
                }
                if( maxs[2] - mins[2] <= epsilon ) {
                    if( this.getPixel(x,y).getBlue() < globalThreshold ) b = 0;
                    else b = 255;
                }
                else {
                    int threshold = ( maxs[2] + mins[2] ) / 2;
                    if( this.getPixel(x,y).getBlue() < threshold ) b = 0;
                    else b = 255;
                }
                resImg.setRGB(x,y, new Color(r,g,b).getRGB() );
            }
        }
        return resImg;
    }
    public ColorImage localMultiThreshold( int size, int thresholdCount ) {
        ColorImage resImage = new ColorImage( this.getWidth(), this.getHeight());
        resImage.setName( this.getName() + "-local-multi");

        if( size % 2 == 0 ) size++;


        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                int dist = size /2; // sasiedzi : [p-dist , p+dist]
                Pixel pixel = this.getPixel(x,y);

                if(  pixel.getX() - dist < 0 || this.getWidth() < pixel.getX() + dist
                        || pixel.getY() - dist < 0 || this.getHeight() < pixel.getY() + dist )
                {
                    // get dist = dist or smaller if we are at the border
                    dist = Math.min(
                            Math.min( pixel.getX(), Math.abs(pixel.getX() - this.getWidth()) -1 ),
                            Math.min( pixel.getY(), Math.abs(pixel.getY() - this.getHeight())-1 )
                    ); // min because we don't know which border
                }

                int[] maxs = {255,255,255};
                int[] mins = {0,0,0};
                for( int xx = -dist; xx < dist; xx++)
                {
                    for( int yy = -dist; y < dist; yy++)
                    {
                        int xSafe = x+ xx > this.getWidth() -dist || x+xx < 0 ? x : x+xx;
                        int ySafe = y+yy > this.getHeight() -dist || y+yy < 0 ? y : y+yy;
                        Pixel neighbor = this.getPixel(xSafe, ySafe);
                        maxs[0] = Math.max( maxs[0], neighbor.getRed());
                        maxs[1] = Math.max( maxs[1], neighbor.getGreen());
                        maxs[2] = Math.max( maxs[2], neighbor.getBlue());
                        mins[0] = Math.min( mins[0], neighbor.getRed());
                        mins[1] = Math.min( mins[1], neighbor.getGreen());
                        mins[2] = Math.min( mins[2], neighbor.getBlue());

                    }
                }

                float sr = (float)maxs[0] / (float)(thresholdCount - 1);
                float sg = (float)maxs[1] / (float)(thresholdCount - 1);
                float sb = (float)maxs[2] / (float)(thresholdCount - 1);

                sr = sr == 0 ? 1 : sr;
                sg = sg == 0 ? 1 : sg;
                sb = sb == 0 ? 1 : sb;

                int r = (int) (Math.round( (float)pixel.getRed() / sr ) * sr);
                int g = (int) (Math.round( (float)pixel.getGreen() / sg ) * sg);
                int b = (int) (Math.round( (float)pixel.getBlue() / sb ) * sb);
                resImage.setRGB(x,y, new Color(r,g,b).getRGB() );
            }
        }

        return resImage;
    }
    public ColorImage globalMultiThreshold(int thresholdCount ) {
        ColorImage resImage = new ColorImage( this.getWidth(), this.getHeight());
        resImage.setName( this.getName() + "-global-multi");

        int[] maxs = this.getMaxColors();
        int[] mins = this.getMinColors();
        float sr = (float)maxs[0] / (thresholdCount - 1);
        float sg = (float)maxs[1] / (thresholdCount - 1);
        float sb = (float)maxs[2] / (thresholdCount - 1);

        sr = sr == 0 ? 1 : sr;
        sg = sg == 0 ? 1 : sg;
        sb = sb == 0 ? 1 : sb;

        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                int r,g,b;

                r = (int) (Math.round( this.getPixel(x,y).getRed() / sr) * sr);
                g = (int) (Math.round( this.getPixel(x,y).getGreen() / sg) * sg);
                b = (int) (Math.round( this.getPixel(x,y).getBlue() / sb) * sb);


                resImage.setRGB(x,y, new Color(r,g,b).getRGB() );
            }
        }

        return resImage;
    }

    // Getters And Setters
    public int[] getMinColors(){
        int minR = 255;
        int minG = 255;
        int minB = 255;
        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                if(this.getPixel(x,y).getRed()< minR)
                    minR = this.getPixel(x,y).getRed();

                if(this.getPixel(x,y).getGreen()< minG)
                    minG = this.getPixel(x,y).getGreen();

                if(this.getPixel(x,y).getBlue()< minB)
                    minB = this.getPixel(x,y).getBlue();
            }
        }
        int[] tab = {minR,minG,minB};
        return tab;
    }
    public int[] getMaxColors(){
        int maxR = 0;
        int maxG = 0;
        int maxB = 0;
        for( int x = 0; x < this.getWidth(); x++)
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                if(this.getPixel(x,y).getRed() > maxR)
                    maxR = this.getPixel(x,y).getRed();

                if(this.getPixel(x,y).getGreen() > maxG)
                    maxG = this.getPixel(x,y).getGreen();

                if(this.getPixel(x,y).getBlue() > maxB)
                    maxB = this.getPixel(x,y).getBlue();
            }
        }
        int[] tab = {maxR,maxG,maxB};
        return tab;
    }
    public GrayImage getGrayImage() {
        GrayImage res = new GrayImage( this.getWidth(), this.getHeight());
        res.setName( this.getName() );
        for( int x = 0; x < res.getWidth(); x++)
        {
            for( int y = 0; y < res.getHeight(); y++ )
            {
                int gray = ( this.getPixel(x,y).getRed() + this.getPixel(x,y).getGreen() + this.getPixel(x,y).getBlue() )/3;
                Color color = new Color(gray,gray,gray);
                res.setRGB(x,y, color.getRGB());
            }
        }
        return res;
    }


}
