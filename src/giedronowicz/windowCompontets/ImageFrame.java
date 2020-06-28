package giedronowicz.windowCompontets;

import giedronowicz.histogram.Histogram;
import giedronowicz.images.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

public class ImageFrame extends JFrame {

    ImagePanel imagePanel;
    Image image;
    boolean isImage;


    public ImageFrame(Image image)
    {
        super(image.getName());
        this.image = image;
        isImage = true;
        imagePanel = new ImagePanel(image);
        this.add(imagePanel);
        this.init();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();                // Dzięki temu rozmiar ramki dopasuje sie automatycznie do rozmiaru panelu
        this.setVisible(true);
    }

    public ImageFrame(Histogram histogram)
    {
        super(histogram.getName());
        this.add(histogram);
        this.setName( histogram.getName() );
        this.isImage = false;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();                // Dzięki temu rozmiar ramki dopasuje sie automatycznie do rozmiaru panelu
        this.setVisible(true);
    }

    public Image getImage()
    {
        return this.imagePanel.getImage();
    }

    private void init() {
        this.setJMenuBar( new ImageFrameMenu(this.image) );
    }

}
