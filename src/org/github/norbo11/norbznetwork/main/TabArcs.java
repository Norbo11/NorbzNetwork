package org.github.norbo11.norbznetwork.main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.listeners.DurationFieldListener;
import org.github.norbo11.norbznetwork.network.Arc;

public class TabArcs extends JScrollPane {
    private static final long serialVersionUID = 1L;
    private static JPanel panel = new JPanel();
    
    public TabArcs()
    {
        super(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }
  
    public void addArc(final Arc arc)
    {
        JLabel label = new JLabel(arc.toString());
        
        JTextField field = new JTextField(3);
        field.addActionListener(new DurationFieldListener(arc));
        
        JPanel container = new JPanel(new FlowLayout());
        JButton deleteButton = new JButton("X");
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getCurrentNetwork().deleteArc(arc);
            }
        });

        container.add(label);
        container.add(field);
        container.add(deleteButton);
        panel.add(container);
    }

    public void updateArcs() {
        panel.removeAll();
        for (Arc arc : Main.getCurrentNetwork().getArcs())
        {
            addArc(arc);
        }
        panel.revalidate();
        panel.repaint();
    }
}
