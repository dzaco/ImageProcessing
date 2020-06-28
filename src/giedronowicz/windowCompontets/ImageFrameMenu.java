package giedronowicz.windowCompontets;

import giedronowicz.images.Image;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class ImageFrameMenu extends JMenuBar implements ActionListener {

    private final String SAVE_FILE = "SAVE_FILE";

    private Image image;

    private JMenu fileMenu = new JMenu("File");
    private JMenuItem save = new JMenuItem("Save image");

    public ImageFrameMenu(Image image)
    {
        this.image = image;

        this.fileMenu.add(this.save);
        this.add(fileMenu);

        this.setActions();
    }

    private void setActions()
    {
        this.save.addActionListener(this);
        this.save.setActionCommand(this.SAVE_FILE);



    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals(this.SAVE_FILE))
        {
            String homePath = null;
            try {
                String jarPath = Window.class.getProtectionDomain()
                        .getCodeSource().getLocation().toURI().getPath();

                String jarName = new File(Window.class.getProtectionDomain()
                        .getCodeSource().getLocation().toURI().getPath()).getName();

                homePath = jarPath.replace( jarName, "");
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }


            JFileChooser fileChooser = new JFileChooser(homePath);
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION)
            {
                Path path = fileChooser.getSelectedFile().toPath();
                this.image.write(path);
            }
        }


    }

}
