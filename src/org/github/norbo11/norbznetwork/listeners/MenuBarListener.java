package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.github.norbo11.norbznetwork.frames.DijkstrasFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.main.NetworkManager;

public class MenuBarListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Main.getItemClearAll())
        {
            NetworkManager.getArcs().clear();
            NetworkManager.getNodes().clear();
            Main.getArcsTab().updateArcs();
        }
        
        
        if (e.getSource() == Main.getItemDijkstras())
        {
            Main.setAlgorithmFrame(new DijkstrasFrame());
        }
        
    }

}
