package giedronowicz.images;

import java.awt.*;

public class GrayPixel extends Pixel {

    private int gray;

    public GrayPixel(int x, int y, int rgb) {
        super(x, y, rgb);

        Color color = new Color(rgb);
        if( color.getRed() == color.getGreen() && color.getRed() == color.getBlue())
            this.setGray( color.getRed() );
    }

    public int getGray() {
        return gray;
    }
    public void setGray(int gray) {
        this.gray = gray;
        this.setColor( new Color(gray,gray,gray) );
    }


    // Operacje

    static public GrayPixel plus(GrayPixel pixel , int param) {
        int gray = pixel.getGray() + param;

        // Niwelujemy problem wartosci po za (0, 255)
        gray = Math.min(gray, 255);
        gray = Math.max(gray, 0);

        Color resColor = new Color(gray,gray,gray);
        return new GrayPixel(pixel.getX(), pixel.getY(), resColor.getRGB() );
    }
    static public GrayPixel plus(GrayPixel pixel , GrayPixel pixel2){
        int gray = pixel.getGray() + pixel2.getGray();

        // Niwelujemy problem wartosci powyżej maksymalnej (255)
        gray %= 255;

        Color resColor = new Color(gray,gray,gray);
        return new GrayPixel(pixel.getX(), pixel.getY(), resColor.getRGB() );
    }

    public static GrayPixel multiply(GrayPixel pixel, float param) {

        int gray = Math.round( (float)pixel.getGray() * param );

        // Niwelujemy problem wartosci powyżej maksymalnej (255)
        gray = Math.min(gray, 255);

        Color resColor = new Color(gray,gray,gray);
        return new GrayPixel(pixel.getX(), pixel.getY(), resColor.getRGB() );
    }
}
