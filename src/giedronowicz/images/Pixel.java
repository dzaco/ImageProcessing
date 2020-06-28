package giedronowicz.images;


import java.awt.*;
import java.util.Objects;

public class Pixel {
    private int x;
    private int y;
    private Color color;

    public Pixel( int x, int y, int rgb) {
        this.setX(x);
        this.setY(y);
        this.color = new Color( rgb );
    }

    static public Pixel plus(Pixel pixel , int param) {
        int red = pixel.getRed() + param;
        int green = pixel.getGreen() + param;
        int blue = pixel.getBlue() + param;

        // Niwelujemy problem wartosci powyżej maksymalnej (255)
        red = Math.min(red, 255);
        green = Math.min(green, 255);
        blue = Math.min(blue, 255);

        Color resColor = new Color(red,green,blue);
        return new GrayPixel(pixel.getX(), pixel.getY(), resColor.getRGB() );
    }


    // Getter And Setter
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public Color getColor(){ return this.color; }
    public void setColor( Color color ) {
        this.color = color;
    }
    public int getRGB() { return this.color.getRGB(); }
    public int getRed() { return this.color.getRed(); }
    public int getBlue() { return this.color.getBlue(); }
    public int getGreen() { return this.color.getGreen(); }
    public int getAlpha() { return this.color.getAlpha();}


    @Override
    public String toString() {
        return "Pixel{" +
                "x=" + x +
                ", y=" + y +
                ", red=" + color.getRed() +
                ", green=" + color.getGreen() +
                ", blue=" + color.getBlue() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return x == pixel.x &&
                y == pixel.y &&
                Objects.equals(color, pixel.color);
    }

}
