package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.github.norbo11.norbznetwork.algorithms.LongestPath;
import org.github.norbo11.norbznetwork.algorithms.Prims;
import org.github.norbo11.norbznetwork.frames.DijkstrasFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Network;
import org.github.norbo11.norbznetwork.util.GraphUtil;

public class MenuBarListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        /* File */
        
        if (e.getSource() == Main.getItemSaveGraph()) {
            JFileChooser fc = new JFileChooser(new File("networks"));

            int returnVal = fc.showSaveDialog(Main.getFrame());
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                
                GraphUtil.saveGraph(file, Main.getCurrentNetwork());
            }
        }
        
        if (e.getSource() == Main.getItemLoadGraph()) {
            JFileChooser fc = new JFileChooser(new File("networks"));

            int returnVal = fc.showOpenDialog(Main.getFrame());
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                
                GraphUtil.loadAndSetGraph(file);
            }
        }
        
        /* Tools */
        
        if (e.getSource() == Main.getItemClearAll()) {
            Main.getCurrentNetwork().getArcs().clear();
            Main.getCurrentNetwork().getNodes().clear();
            Main.getArcsTab().updateArcs();
        }
        
        /* Algorithms */
        
        if (e.getSource() == Main.getItemDijkstras()) {
            Main.setAlgorithmFrame(new DijkstrasFrame());
        }
        
        if (e.getSource() == Main.getItemPrims()) {
            Network network = Prims.getMinimumSpanningTree(Main.getCurrentNetwork());
            Main.getCurrentNetwork().drawPart(network);
            Main.getArcsTab().updateArcs();
            Main.writeText("Minimum spanning tree weight: " + network.getWeight() + "\n");
        }
        
        if (e.getSource() == Main.getItemLongest()) {
            LongestPath lp = new LongestPath();
            lp.mainLoop();
        }
        
    }

}
