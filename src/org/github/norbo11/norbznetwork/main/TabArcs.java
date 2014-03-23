package org.github.norbo11.norbznetwork.main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.github.norbo11.norbznetwork.listeners.DurationFieldListener;
import org.github.norbo11.norbznetwork.util.Arc;

public class TabArcs extends JPanel {

    private static final long serialVersionUID = 1L;
    private GridBagLayout layout;
    
    public TabArcs()
    {
        super();
        layout = new GridBagLayout();
        setLayout(layout);
    }
  
    public void addArc(Arc arc)
    {
        JLabel label = new JLabel(arc.toString());
        JTextField field = new JTextField(3);
        field.addActionListener(new DurationFieldListener(arc));
        JPanel container = new JPanel();
        container.add(label);
        container.add(field);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        this.layout.setConstraints(container, constraints);
        add(container);
    }

    public void updateArcs() {
        removeAll();
        for (Arc arc : NetworkManager.getArcs())
        {
            addArc(arc);
        }
        revalidate();
        repaint();
    }
    
}
