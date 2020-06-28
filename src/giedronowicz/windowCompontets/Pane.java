package giedronowicz.windowCompontets;

import giedronowicz.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Pane extends JPanel {
    private JLabel label;
    private JList<Pair> list;
    private JButton button;

    public Pane( List<Pair> sourceList )
    {
        this.setOpaque(true);
        this.setLayout( new BorderLayout() );
        this.label = new JLabel( sourceList.get(0).task );
        this.label.setFont(new Font("Verdana", Font.BOLD, 15));
        sourceList.remove(0);

        DefaultListModel<Pair> l = new DefaultListModel<Pair>();
        for( Pair item : sourceList)
            l.addElement(item);

        this.list = new JList<>(l);
        this.button = new JButton("EXECUTE");
        this.buttonAction( sourceList );
        this.add( this.label , BorderLayout.NORTH );
        this.add( new JScrollPane(this.list) , BorderLayout.CENTER );
        this.add( this.button , BorderLayout.SOUTH );

    }

    private void buttonAction( List<Pair> sourceList )
    {
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = "task" + sourceList.get(list.getSelectedIndex()).number;
                Task task = new Task();
                task.exe(taskName);
            }
        });
    }


}
