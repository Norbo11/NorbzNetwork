package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.github.norbo11.norbznetwork.network.Arc;

public class DurationFieldListener implements ActionListener {

    public Arc arc = null;
    
    public DurationFieldListener(Arc arc) {
        this.arc = arc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        arc.setWeight(Integer.valueOf(((JTextField) e.getSource()).getText()));
    }

}
