package giedronowicz.images;

import giedronowicz.Main;
import giedronowicz.windowCompontets.ImageFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.System.exit;

public class Image {

    private BufferedImage image;
    private String name;

    // Contructors

    public Image() {}
    public Image( String fileName ) {
        try {
            this.image = ImageIO.read( getClass().getResource(fileName) );
            this.setName( fileName.replace(".png" , "") );
        } catch (IOException e) {
            System.out.println("Image Loading Error");
            JOptionPane.showMessageDialog(new JFrame("Error"), "Image Loading Error" );
        }
    }
    public Image(Image image) {
        this.setImage( image );
        this.setName( image.getName() );
    }
    public Image( int width, int height)
    {
        this.image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
    }
    public Image( BufferedImage image ) {
        this.setImage( image );
        this.setName("new");
    }



    // -------------
    public void write() {
        try {
            ImageIO.write( this.getImage(), "png", new File("src/giedronowicz/source/new/" + this.getName() +".png"));
            System.out.println("Zapisano obraz do pliku");
        } catch (IOException e) {
            System.out.println("Błąd zapisu obrazu");
            exit(1);
        }
    }
    public void write(Path path) {
        try {
            ImageIO.write( this.getImage(), "png", new File( String.valueOf(path) + ".png" ));
            System.out.println("Zapisano obraz do pliku");
        } catch (IOException e) {
            System.out.println("Błąd zapisu obrazu");
            exit(1);
        }
    }
    public ImageFrame createFrame() {
        System.out.println("tworze " + this.getName() + " " + this );
        return new ImageFrame(this);
    }
    public Image clone() {
        Image res = new Image( this.getWidth(), this.getHeight() );
        res.setName( this.getName() );
        for( int x = 0; x < res.getWidth(); x++)
            for( int y = 0; y < res.getHeight(); y++ )
                res.setRGB(x,y, this.getRGB(x,y) );
        return res;
    }

    public Image resize( int width, int height ) {
        Image scaled = new Image(width , height);
        final double scaleW = (double)scaled.getWidth() / (double)this.getWidth();
        final double scaleH = (double)scaled.getHeight() / (double)this.getHeight();

        AffineTransform transformer = AffineTransform.getScaleInstance( scaleW, scaleH);
        AffineTransformOp operation = new AffineTransformOp( transformer , AffineTransformOp.TYPE_BILINEAR );
        operation.filter(this.getImage(), scaled.getImage());

        scaled.setName( "scaled_" + this.getName() + "x" + scaleW );
        return scaled;
    }


    // Getters And Setters

    public BufferedImage getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image.getImage();
    }
    protected void setImage(BufferedImage image) { this.image = image; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getWidth()
    {
        return this.getImage().getWidth();
    }
    public int getHeight() { return this.getImage().getHeight(); }

    public int getRGB(int x, int y)
    {
        return this.getImage().getRGB(x,y);
    }
    public void setRGB(int x, int y, int rgb){
        this.getImage().setRGB(x,y,rgb);
    }
    public Pixel getPixel( int x, int y)
    {
        return new Pixel(x,y, this.getRGB(x,y));
    }

    public boolean isEqualSize( Image image ) {
        return this.getWidth() == image.getWidth() && this.getHeight() == image.getHeight();
    }
    public boolean isSmaller( Image image ) {
        return this.getWidth() < image.getWidth() || this.getHeight() < image.getHeight();
    }
    public boolean isInRange(int x, int y) {
        return x >= 0 && x < this.getWidth()
                && y >= 0 && y < this.getHeight();
    }

    public Image interpolate() {
        Image resImage = new Image( this.getWidth(), this.getHeight());
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

    public Image move( int x, int y ) {
        Image resImage = new Image(this.getWidth(), this.getHeight() );
        resImage.setName( this.getName() + "-move-" + x + "-" + y);
        for( int w = 0; w < this.getWidth(); w++)
            for( int h = 0; h < this.getHeight(); h++)
                resImage.setRGB(w,h, new Color(0,0,0).getRGB() );

        y = -y; // pierwsza cwiartka

        for( int w = 0; w < this.getWidth(); w++ )
        {
            for( int h = 0; h < this.getHeight(); h++ )
            {
                if( isInRange(w+x, h+y) )
                {
                    resImage.setRGB( w+x, h+y, this.getRGB(w,h) );
                }

            }
        }

        return resImage;
    }

    public Image scaling( float s ) {
        return this.scaling(s,s);
    }
    public Image scaling( float sx, float sy) {
        Image resImage = new Image(this.getWidth(), this.getHeight() );
        resImage.setName( this.getName() + "-scaling-" + sx + "-" + sy);
        sx = 1 / sx;  sy = 1 / sy;

        for(int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++)
            {
                int pomX = (int) (x * sx);
                int pomY = (int) (y * sy);
                if ( isInRange(pomX, pomY) )
                    resImage.setRGB(x,y, this.getRGB(pomX,pomY) );
            }
        }
        return resImage;
    }

    public Image rotate( int angle ) {
        Image resImage = new Image(this.getWidth(), this.getHeight() );
        resImage.setName( this.getName() + "-rotate-" + angle );
        double a = Math.toRadians(angle);
        Pixel p0 = new Pixel( this.getWidth()/2, this.getHeight()/2 , Color.BLACK.getRGB() );
        int vx = -p0.getX();
        int vy = -p0.getY();

        for( int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                Pixel p = new Pixel(x,y, this.getRGB(x,y));
                p.setX( p.getX() - p0.getX() );
                p.setY( p.getY() - p0.getY() );

                int newX = (int) Math.round(( p.getX() * Math.cos(a) ) - ( p.getY() * Math.sin(a) ));
                int newY = (int) Math.round(( p.getX() * Math.sin(a) ) + ( p.getY() * Math.cos(a) ));

                p.setX(newX - vx);
                p.setY(newY - vy);

                if(isInRange(p.getX(), p.getY()))
                    resImage.setRGB( p.getX(), p.getY() , p.getRGB());
            }
        }

        return resImage;
    }

    public Image symmetryX() {
        Image resImage = new Image(this.getWidth(), this.getHeight() );
        resImage.setName( this.getName() + "-symmetryX" );

        for( int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                resImage.setRGB(x,y, this.getRGB(x,this.getHeight()-1 -y));
            }
        }

        return resImage;
    }
    public Image symmetryY() {
        Image resImage = new Image(this.getWidth(), this.getHeight() );
        resImage.setName( this.getName() + "-symmetryY" );

        for( int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                resImage.setRGB(x,y, this.getRGB(this.getWidth()-1-x,y));
            }
        }

        return resImage;
    }
    public Image symmetryVerticalLine() {
        Image resImage = new Image(this.getWidth(), this.getHeight() );
        resImage.setName( this.getName() + "-symmetryVerticalLine" );
        int c = this.getWidth()/2;

        for( int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                if( x < c )
                    resImage.setRGB(x,y, this.getRGB(x,y));
                else
                    resImage.setRGB(x,y, this.getRGB(this.getWidth()-1-x,y));
            }
        }

        return resImage;
    }
    public Image symmetryHorizontalLine() {
        Image resImage = new Image(this.getWidth(), this.getHeight() );
        resImage.setName( this.getName() + "-symmetryHorizontalLine" );
        int c = this.getHeight()/2;

        for( int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                if( y < c )
                    resImage.setRGB(x,y, this.getRGB(x,y));
                else
                    resImage.setRGB(x,y, this.getRGB(x,this.getHeight()-1-y));
            }
        }

        return resImage;
    }
    public Image symmetryLine() {
        Image resImage = new Image(this.getWidth(), this.getHeight() );
        resImage.setName( this.getName() + "-symmetryLine" );

        for( int x = 0; x < this.getWidth(); x++ )
        {
            for( int y = 0; y < this.getHeight(); y++ )
            {
                if( y < x )
                    resImage.setRGB(x,y, this.getRGB(x,y));
                else
                    resImage.setRGB(x,y, this.getRGB(this.getWidth()-1-x,this.getHeight()-1-y));
            }
        }

        return resImage;
    }

    public Image getImageRegion( Rectangle region ) {
        Image resImage = new Image( (int) region.getWidth(), (int) region.getHeight() );
        resImage.setName( this.getName() + "-getRegion" );

        for( int x = 0; x < resImage.getWidth()-1; x++)
        {
            for( int y = 0; y < resImage.getHeight()-1; y++)
            {
                resImage.setRGB(x,y, this.getRGB((int) region.getMinX() + x , (int) region.getMinY() + y) );
            }
        }
        return resImage;
    }
    public Image getImageRegion( Ellipse2D region ) {
        Image resImage = new Image( (int)region.getWidth(), (int)region.getHeight() );
        resImage.setName( this.getName() + "-getRegion" );

        for( int x = 0; x < resImage.getWidth(); x++)
        {
            for( int y = 0; y < resImage.getHeight(); y++)
            {
                if( region.contains((int) region.getMinX() + x , (int) region.getMinY() + y) )
                    resImage.setRGB(x,y, this.getRGB((int) region.getMinX() + x , (int) region.getMinY() + y) );
                else
                    resImage.setRGB(x,y, 0 );
            }
        }
        return resImage;
    }
    public Image cutImageRegion( Shape region ) {
        Image resImage = new Image( this.clone() );
        resImage.setName( this.getName() + "-cutRegion" );

        for( int x = 0; x < resImage.getWidth(); x++)
        {
            for( int y = 0; y < resImage.getHeight(); y++)
            {
                if( region.contains(x,y))
                {
                    resImage.setRGB( x , y , 0 );
                }
            }
        }
        return resImage;
    }



}
