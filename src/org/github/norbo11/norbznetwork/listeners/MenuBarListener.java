package org.github.norbo11.norbznetwork.listeners;

import static org.github.norbo11.norbznetwork.frames.Main.MenuItem.CLEARALL;
import static org.github.norbo11.norbznetwork.frames.Main.MenuItem.DIJKSTRAS;
import static org.github.norbo11.norbznetwork.frames.Main.MenuItem.PRIMS;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.github.norbo11.norbznetwork.algorithms.Prims;
import org.github.norbo11.norbznetwork.frames.DijkstrasFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.main.NetworkManager;
import org.github.norbo11.norbznetwork.network.Network;

public class MenuBarListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        JMenuItem e = (JMenuItem) event.getSource();
        System.out.println(e.getActionCommand());
        
        if (e.getActionCommand().equals(CLEARALL + ""))
        {
            NetworkManager.getArcs().clear();
            NetworkManager.getNodes().clear();
            Main.getArcsTab().updateArcs();
        }
        
        if (e.getActionCommand().equals(DIJKSTRAS + ""))
        {
            new DijkstrasFrame();
        }
        
        if (e.getActionCommand().equals(PRIMS + ""))
        {
            
            Network network = Prims.getMinimumSpanningTree();
            network.draw();
            Main.getArcsTab().updateArcs();
            System.out.println("Minimum spanning tree weight: " + network.getWeight());
        }
        
    }

}
