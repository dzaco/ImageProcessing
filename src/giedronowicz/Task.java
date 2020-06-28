package giedronowicz;

import giedronowicz.histogram.ColorHistogram;
import giedronowicz.histogram.GrayHistogram;
import giedronowicz.images.ColorImage;
import giedronowicz.images.GrayImage;
import giedronowicz.images.Image;
import giedronowicz.windowCompontets.ImageFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Task {

    public void exe(String taskName ) {
        try {

            for( Method method : this.getClass().getDeclaredMethods() )
            {
                if( method.getName().equals( taskName ) )
                {
                    method.invoke(this);
                    break;
                }
            }


        }
        catch (InvocationTargetException | IllegalAccessException e)
        { e.printStackTrace(); }

    }


    public void task1_1() {
        GrayImage image = new GrayImage("/gray/barbara.png");
        GrayImage image2 = new GrayImage("/gray/animal.png");
        image.createFrame();
        image2.createFrame().setLocation( image.getWidth(), 0);

        Image unify = image.unifyGeometric(image2);
        unify.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - unify.getWidth()/2 , 0);
    }         // unifyGeometric gray
    public void task1_2() {
        GrayImage image = new GrayImage("/gray/barbara.png");
        GrayImage image2 = new GrayImage("/gray/animal.png");
        image.createFrame();
        image2.createFrame().setLocation(image.getWidth(),0);

        GrayImage unify = image.unifyRaster( image2.getWidth(), image2.getHeight() );
        unify.createFrame();
        GrayImage interpolatedImage = unify.interpolate();
        interpolatedImage.createFrame().setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - interpolatedImage.getWidth() , 0);
    }         // unifyRaster gray
    public void task1_3() {
        ColorImage image = new ColorImage("/color/Lena2.png");
        ColorImage image2 = new ColorImage("/color/animal.png");
        image.createFrame();
        image2.createFrame().setLocation( image.getWidth(), 0);

        Image unify = image.unifyGeometric(image2.getWidth(), image2.getHeight() );
        unify.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - unify.getWidth()/2 , 0);
    }         // unifyGeometric color
    public void task1_4() {
        ColorImage image = new ColorImage("/color/Lena2.png");
        ColorImage image2 = new ColorImage("/color/animal.png");
        image.createFrame();
        image2.createFrame().setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - image2.getWidth() , 0);

        ColorImage unify = image.unifyRaster( image2.getWidth(), image2.getHeight() );
        unify.createFrame();
        ColorImage interpolatedImage = unify.interpolate();
        interpolatedImage.createFrame().setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - interpolatedImage.getWidth() , 0);
    }         // unifyRaster color

    public void task2_1_1() {

        int param = Integer.parseInt( JOptionPane.showInputDialog("Parametr sumowania typu int:") );
        GrayImage image = new GrayImage("/gray/barbara.png");
        image.createFrame();

        Image newImage = image.sum(param);
        newImage.createFrame().setLocation( image.getWidth() , 0);

    }       // sum param
    public void task2_1_2() {
        GrayImage image = new GrayImage("/gray/Lena2.png");
        GrayImage image2 = new GrayImage("/binary/circ2.png");
        image.createFrame();
        image2.createFrame().setLocation( image.getWidth(), 0);

        Image newImage = image.sum(image2);
        newImage.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - newImage.getWidth() / 2 , 0);
    }       // sum img
    public void task2_2_1() {
        float param = Float.parseFloat( JOptionPane.showInputDialog("Parametr mnożenia typu float:") );
        GrayImage image = new GrayImage("/gray/barbara.png");
        image.createFrame();
        GrayImage img = image.multiply(param);
        img.createFrame().setLocation( image.getWidth(), 0);
    }       // multiply param
    public void task2_2_2() {
        GrayImage image = new GrayImage("/gray/Lena2.png");
        GrayImage image2 = new GrayImage("/binary/circ2.png");
        image.createFrame(); image2.createFrame().setLocation(image.getWidth(),0);

        GrayImage img = image.multiply(image2);
        img.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - img.getWidth()/2,0);
    }       // multiply img
    public void task2_3() {
        float param;
        do {
            param = Float.parseFloat( JOptionPane.showInputDialog("Parametr mieszania typu float [0,1]:") );
        } while( param < 0 || param > 1);

        GrayImage image = new GrayImage("/gray/Lena2.png");
        GrayImage image2 = new GrayImage("/gray/monkey.png");
        image.createFrame(); image2.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - image.getWidth() , 0);
        GrayImage img = image.mix(image2, param);
        img.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - img.getWidth()/2,0);
    }         // mix
    public void task2_4() {
        float param = Float.parseFloat( JOptionPane.showInputDialog("Parametr potęgowania typu float:") );
        GrayImage image = new GrayImage("/gray/Lena2.png");
        image.createFrame();

        GrayImage img = image.pow(param);
        img.createFrame().setLocation( image.getWidth() ,0);
    }         // pow
    public void task2_5_1() {
        float param = Float.parseFloat( JOptionPane.showInputDialog("Parametr dzielenia typu float:") );
        GrayImage image = new GrayImage("/gray/Lena2.png");
        image.createFrame();
        GrayImage img = image.divide(param);
        img.createFrame().setLocation(image.getWidth(), 0);
    }       // divide param
    public void task2_5_2() {
        GrayImage image = new GrayImage("/gray/ship.png");
        GrayImage image2 = new GrayImage("/gray/ship2.png");

        image.createFrame(); image2.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - image2.getWidth() , 0);
        GrayImage img = image.divide(image2);
        img.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - img.getWidth()/2 , 0);
    }       // divide img
    public void task2_6() {
        float param = Float.parseFloat( JOptionPane.showInputDialog("Parametr pierwiastkowania typu float:") );
        GrayImage image = new GrayImage("/gray/lena.png");
        image.createFrame();

        GrayImage img = image.root(param);
        img.createFrame().setLocation( image.getWidth() , 0);
    }         // root
    public void task2_7() {
        GrayImage image = new GrayImage("/gray/port.png");
        image.createFrame();

        GrayImage img = image.log();
        img.createFrame().setLocation( image.getWidth() , 0);
    }         // log

    public void task3_1_1() {
        int param = Integer.parseInt( JOptionPane.showInputDialog("Parametr sumowania typu int:") );
        ColorImage image = new ColorImage("/color/fruits.png");
        image.createFrame();
        ColorImage res = image.sum(param);
        res.createFrame().setLocation( image.getWidth(), 0);
    }       // sum param
    public void task3_1_2() {
        ColorImage image = new ColorImage("/color/fruits.png");
        ColorImage image2 = new ColorImage("/color/Lena2.png");

        image.createFrame();
        image2.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - image2.getWidth() , 0);
        ColorImage res = image.sum(image2);
        res.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - res.getWidth()/2 , 0);
    }       // sum img
    public void task3_2_1() {
        float param = Float.parseFloat( JOptionPane.showInputDialog("Parametr mnożenia typu float:") );
        ColorImage image = new ColorImage("/color/bird.png");

        image.createFrame();
        ColorImage res = image.multiply(param);
        res.createFrame().setLocation( image.getWidth(), 0);
    }       // multiply param
    public void task3_2_2() {
        ColorImage image = new ColorImage("/color/Lena2.png");
        ColorImage image2 = new ColorImage("/color/blur.png");

        image.createFrame();
        image2.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - image2.getWidth() , 0);
        ColorImage res = image.multiply(image2);
        res.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - image2.getWidth()/2 , 0);
    }       // multiply img
    public void task3_3() {
        float param;
        do {
            param = Float.parseFloat( JOptionPane.showInputDialog("Parametr mieszania typu float [0,1]:") );
        } while( param < 0 || param > 1);

        ColorImage image = new ColorImage("/color/bird.png");
        ColorImage image2 = new ColorImage("/color/fruits.png");

        image.createFrame();
        image2.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - image2.getWidth() , 0);
        ColorImage res = image.mix(image2, param);
        res.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - image2.getWidth()/2 , 0);
    }         // mix
    public void task3_4() {
        float param = Float.parseFloat( JOptionPane.showInputDialog("Parametr potęgowania typu float:") );
        ColorImage image = new ColorImage("/color/monkey.png");
        image.createFrame();

        ColorImage res = image.pow(param);
        res.createFrame().setLocation( image.getWidth(), 0);
    }         // pow
    public void task3_5_1() {
        float param = Float.parseFloat( JOptionPane.showInputDialog("Parametr dzielenia typu float:") );
        ColorImage image = new ColorImage("/color/Lena2.png");
        image.createFrame();

        ColorImage img = image.divide(param);
        img.createFrame().setLocation( image.getWidth(),0);
    }       // divide param
    public void task3_5_2() {
        ColorImage image = new ColorImage("/color/fruits.png");
        ColorImage image2 = new ColorImage("/color/fruits2.png");
        image.createFrame();
        image2.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - image2.getWidth() , 0);;

        ColorImage img = image.divide(image2);
        img.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - image2.getWidth()/2 , 0);
    }       // divide img
    public void task3_6() {
        float param = Float.parseFloat( JOptionPane.showInputDialog("Parametr pierwiastkowania typu float:") );
        ColorImage image = new ColorImage("/color/monkey.png");
        image.createFrame();

        ColorImage img = image.root(param);
        img.createFrame().setLocation( image.getWidth(),0);
    }         // root
    public void task3_7() {
        ColorImage image = new ColorImage("/color/forest.png");
        image.createFrame();

        ColorImage img = image.log();
        img.createFrame().setLocation(image.getWidth(),0);
    }         // log

    public void task4_1() {
        int param = Integer.parseInt( JOptionPane.showInputDialog("Parametr przemieszczenia X typu int:") );
        int param2 = Integer.parseInt( JOptionPane.showInputDialog("Parametr przemieszczenia Y typu int:") );
        Image image = new ColorImage("/color/bird.png");
        image.createFrame();

        Image img = image.move(param, param2);
        img.createFrame().setLocation( image.getWidth(), 0);
    }       // move
    public void task4_2() {
        float param = Float.parseFloat( JOptionPane.showInputDialog("Parametr skalowania X typu float:") );
        float param2 = Float.parseFloat( JOptionPane.showInputDialog("Parametr skalowania Y typu float:") );
        Image image = new ColorImage("/color/flower.png");
        image.createFrame();

        Image img = image.scaling(param, param2);
        img.createFrame().setLocation(image.getWidth(),0);
    }       // scaling
    public void task4_3() {
        int param = Integer.parseInt( JOptionPane.showInputDialog("Parametr obrotu typu int:") );
        Image image = new ColorImage("/color/fruits.png");
        image.createFrame();

        Image img = image.rotate(param);
        Image inter = img.interpolate();
        img.setName(img.getName() + "-raw");
        img.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - img.getWidth() , 0);
        inter.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 - img.getWidth()/2 , 0);

    }       // rotate
    public void task4_4_1() {
        Image image = new ColorImage("/color/animal.png");
        image.createFrame();

        Image img = image.symmetryX();
        img.createFrame();

        img = image.symmetryY();
        img.createFrame();
    }     // symmetryX symmetryY
    public void task4_4_2() {
        Image image = new ColorImage("/color/animal.png");
        image.createFrame();

        Image img = image.symmetryVerticalLine();
        img.createFrame();

        img = image.symmetryHorizontalLine();
        img.createFrame();

        img = image.symmetryLine();
        img.createFrame();

    }     // symmetryVerticalLine
    public void task4_5() {
        Image image = new ColorImage("/color/animal.png");
        image.createFrame();

        Image img = image.cutImageRegion(new Rectangle(310, 320, 130, 130));
        img.createFrame();

    }       // cutRegion
    public void task4_6() {
        Image image = new ColorImage("/color/animal.png");
        image.createFrame();
        //new Ellipse2D.Double(310, 320, 150, 150)
        Image img = image.getImageRegion(new Ellipse2D.Double(310, 320, 150, 150));
        img.createFrame().setLocation(image.getWidth(),0);
    }       // getRegion

    public void task5_1() {
        GrayImage image = new GrayImage("/gray/Lena2.png");
        image.createFrame();

        GrayHistogram his = new GrayHistogram();
        his.calculate(image);
        his.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),0);
    }       // gray histogram
    public void task5_2() {
        GrayImage image = new GrayImage("/gray/barbara.png");
        image.createFrame();

        GrayHistogram his = new GrayHistogram();
        his.calculate(image);
        his.createFrame().setLocation( 0,200);

        image = image.sum(40);
        image.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),0);
        his = new GrayHistogram();
        his.calculate(image);
        his.createFrame().setLocation( (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),200);
    }       // move histogram
    public void task5_3() {
        GrayImage image = new GrayImage("/gray/port.png");
        image = image.sum(100).sum(-100);
        image.createFrame();

        GrayHistogram his = new GrayHistogram();
        his.calculate(image);
        his.createFrame().setLocation(0,200);

        image = image.histogramStretching();
        image.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),0);
        his = new GrayHistogram();
        his.calculate(image);
        his.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),200);
    }       // stretch histogram
    public void task5_4() {
        int size = Integer.parseInt( JOptionPane.showInputDialog("Parametr rozmiaru lokalnego okna typu int: (zalezany: 7)") );
        int epsilon = Integer.parseInt( JOptionPane.showInputDialog("Parametr epsilon - minimalny kontrast okna typu int: (zalezany: 30)") );
        int global = Integer.parseInt( JOptionPane.showInputDialog("Parametr progu globalnego typu int: (zalezany: 150)") );
        GrayImage image = new GrayImage("/gray/port.png");
        GrayHistogram his = new GrayHistogram();
        his.calculate(image);
        his.createFrame().setLocation(0,200);
        image.createFrame();

        GrayImage res = image.localThreshold(size, epsilon, global);
        his = new GrayHistogram();
        his.calculate(res);
        his.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),200);
        res.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),0);
    }       // local threshold
    public void task5_5() {
        GrayImage image = new GrayImage("/gray/port.png");
        GrayHistogram his = new GrayHistogram();
        his.calculate(image);
        his.createFrame().setLocation(0,200);
        image.createFrame();

        GrayImage res = image.globalThreshold(his);
        his = new GrayHistogram();
        his.calculate(res);
        his.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),200);
        res.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),0);
    }       // global threshold

    public void task6_1() {
        ColorImage image = new ColorImage("/color/monkey.png");
        image.createFrame();
        ColorHistogram his = new ColorHistogram();
        his.calculate(image);
        his.createFrame().setLocation( image.getWidth(),0);

    }       // Color histogram calculate
    public void task6_2() {
        ColorImage image = new ColorImage("/color/monkey.png");
        image.createFrame();
        ColorHistogram his = new ColorHistogram().calculate(image);
        his.createFrame().setLocation(0,200);

        image = image.sum(30);
        image.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),0);
        his = new ColorHistogram().calculate(image);
        his.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),200);


    }       // Color histogram move
    public void task6_3() {
        ColorImage image = new ColorImage("/color/blur.png");
        image.createFrame();
        ColorHistogram his = new ColorHistogram().calculate(image);
        his.createFrame().setLocation(0,200);

        image = image.histogramStretching();
        image.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),0);
        his = new ColorHistogram().calculate(image);
        his.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - his.getWidth(),200);

    }       // Color histogram stretch
    public void task6_4_1() {
        int size = Integer.parseInt( JOptionPane.showInputDialog("Parametr rozmiaru lokalnego okna typu int: (zalezany: 7)") );
        int epsilon = Integer.parseInt( JOptionPane.showInputDialog("Parametr epsilon - minimalny kontrast okna typu int: (zalezany: 30)") );
        int global = Integer.parseInt( JOptionPane.showInputDialog("Parametr progu globalnego typu int: (zalezany: 150)") );
        ColorImage image = new ColorImage( "/color/Lena2.png");
        ColorHistogram histogram = new ColorHistogram( image );
        histogram.createFrame().setLocation(0,200);
        image.createFrame();

        ColorImage res = image.localThreshold( size,epsilon,global );
        histogram = new ColorHistogram( res );
        histogram.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - histogram.getWidth(),200);
        res.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - histogram.getWidth(),0);
    }       // local 1-threshold
    public void task6_4_2() {
        ColorImage image = new ColorImage( "/color/Lena2.png");
        ColorHistogram histogram = new ColorHistogram( image );
        histogram.createFrame().setLocation(0,200);
        image.createFrame();

        ColorImage res = image.globalThreshold( histogram );
        histogram = new ColorHistogram( res );
        histogram.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - histogram.getWidth(),200);
        res.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - histogram.getWidth(),0);
    }       // global 1-threshold
    public void task6_5_1() {
        int size = Integer.parseInt( JOptionPane.showInputDialog("Parametr rozmiaru lokalnego okna typu int: (zalezany: 21)") );
        int count = Integer.parseInt( JOptionPane.showInputDialog("Parametr ilości progów typu int: (zalezany: 4)") );

        ColorImage image = new ColorImage( "/color/Lena2.png");
        ColorHistogram histogram = new ColorHistogram( image );
        histogram.createFrame().setLocation(0,200);
        image.createFrame();

        ColorImage res = image.localMultiThreshold(size, count);
        histogram = new ColorHistogram( res );
        histogram.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - histogram.getWidth(),200);
        res.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - histogram.getWidth(),0);


    }       // local multi-threshold
    public void task6_5_2() {
        int count = Integer.parseInt( JOptionPane.showInputDialog("Parametr ilości progów typu int: (zalezany: 4)") );
        ColorImage image = new ColorImage( "/color/monkey.png");
        ColorHistogram histogram = new ColorHistogram( image );
        histogram.createFrame().setLocation(0,200);
        image.createFrame();

        ColorImage res = image.globalMultiThreshold(count);
        histogram = new ColorHistogram( res );
        histogram.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - histogram.getWidth(),200);
        res.createFrame().setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - histogram.getWidth(),0);


    }       // global multi-threshold


}
