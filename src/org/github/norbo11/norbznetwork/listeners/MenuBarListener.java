package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.github.norbo11.norbznetwork.algorithms.LongestPath;
import org.github.norbo11.norbznetwork.algorithms.Prims;
import org.github.norbo11.norbznetwork.frames.DijkstrasFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.frames.ViewPopulationFrame;
import org.github.norbo11.norbznetwork.network.Graph;
import org.github.norbo11.norbznetwork.util.GraphUtil;

import javax.swing.JFileChooser;

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
        
        if (e.getSource() == Main.getItemNewGraph()) {
            Main.setCurrentNetwork(new Graph(""));
            Main.getArcsTab().updateArcs();
        }
        
        if (e.getSource() == Main.getItemClearColors()) {
            Main.getCurrentNetwork().clearAllPaint();
        }
        
        /* Tools */
        
        if (e.getSource() == Main.getItemConnectAllNodes()) {
            Main.getCurrentNetwork().connectAllNodes();
        }
        
        if (e.getSource() == Main.getItemViewPopulation()) {
            new ViewPopulationFrame();
        }
        
        if (e.getSource() == Main.getItemRandomizeWeights()) {
            Main.getCurrentNetwork().randomizeAllWeights();
        }
        
        /* Algorithms */
        
        if (e.getSource() == Main.getItemDijkstras()) {
            Main.setAlgorithmFrame(new DijkstrasFrame());
        }
        
        if (e.getSource() == Main.getItemPrims()) {
            Graph graph = Prims.getMinimumSpanningTree(Main.getCurrentNetwork());
            Main.getCurrentNetwork().drawPart(graph);
            Main.getArcsTab().updateArcs();
            Main.writeText("Minimum spanning tree weight: " + graph.getWeight() + "\n");
        }
        
        if (e.getSource() == Main.getItemLongest()) {
            new LongestPath().start();
        }
    }
}
