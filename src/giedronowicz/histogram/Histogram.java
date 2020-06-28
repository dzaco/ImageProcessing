package giedronowicz.histogram;

import giedronowicz.images.ColorImage;
import giedronowicz.images.Image;
import giedronowicz.windowCompontets.ImageFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class Histogram extends JPanel {

    /** Override getPreferredSize */
    public Dimension getPreferredSize() {

        return new Dimension(630, 400);

    }
    public ImageFrame createFrame(){
        return new ImageFrame(this);
    }

    public Histogram write() {
        try {
            BufferedImage img = new BufferedImage(this.getPreferredSize().width,this.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = img.createGraphics();
            this.paint(g);

            ImageIO.write( img, "png", new File("src/giedronowicz/source/new/" + this.getName() +".png"));
            System.out.println("Zapisano obraz do pliku");
        } catch (IOException e) {
            System.out.println("Błąd zapisu obrazu");
            exit(1);
        }

        return this;
    }

}
