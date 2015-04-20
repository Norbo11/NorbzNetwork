package org.github.norbo11.norbznetwork.main;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Node;

public class TabNodes extends JScrollPane {

    private static final long serialVersionUID = 1L;
    private static JPanel panel = new JPanel();
    
    public TabNodes()
    {
        super(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }
  
    public void addNode(final Node node)
    {        
        final JTextField field = new JTextField(3);
        field.setText(node.getId());
        field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                node.setId(field.getText());
            }
        });
        
        JPanel container = new JPanel(new FlowLayout());
        JButton deleteButton = new JButton("X");
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.getCurrentNetwork().deleteNode(node);
            }
        });
        
        container.add(field);
        container.add(deleteButton);

        panel.add(container);
    }

    public void updateNodes() {
        panel.removeAll();
        for (Node node : Main.getCurrentNetwork().getNodes())
        {
            addNode(node);
        }
        panel.revalidate();
        panel.repaint();
    }
}
