package giedronowicz.windowCompontets;

import giedronowicz.images.Image;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private Image image;


    public ImagePanel( Image image )
    {
        super();
        this.image = image;
        Dimension dimension = new Dimension( this.image.getWidth(), this.image.getHeight() );
        this.setPreferredSize(dimension);

    }

    public Image getImage() {
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image.getImage(), 0, 0, this );
    }
}
