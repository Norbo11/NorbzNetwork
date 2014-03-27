package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import org.github.norbo11.norbznetwork.algorithms.Dijkstras;
import org.github.norbo11.norbznetwork.frames.DijkstrasFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.main.NetworkManager;
import org.github.norbo11.norbznetwork.network.Node;

public class AlgorithmFrameListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == DijkstrasFrame.getBtnConfirm())
        {
            Main.getAlgorithmFrame().setVisible(false);
            Main.setAlgorithmFrame(null);
            
            NetworkManager.resetAllArcs();
            NetworkManager.resetAllNodes();
            
            Node startNode = (Node) NetworkManager.getNodeById((Character) DijkstrasFrame.getFromBox().getSelectedItem());
            Node endNode = (Node) NetworkManager.getNodeById((Character) DijkstrasFrame.getToBox().getSelectedItem());
            Vector<Node> path = Dijkstras.getShortestPath(startNode, endNode);
            
            if (path != null) 
            {
                System.out.println("Shortest path from " + startNode + " to " + endNode + ": " + path + " = " + NetworkManager.pathWeight(path) + " total weight.");
                NetworkManager.drawPath(path);
            } else System.out.println("Shortest path from " + startNode + " to " + endNode + ": Unreachable!");

        }
    }


}