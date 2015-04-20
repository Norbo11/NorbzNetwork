package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.github.norbo11.norbznetwork.algorithms.Dijkstras;
import org.github.norbo11.norbznetwork.frames.DijkstrasFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Graph;
import org.github.norbo11.norbznetwork.network.Node;

public class AlgorithmFrameListener implements ActionListener {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == DijkstrasFrame.getBtnConfirm())
        {
            Main.getAlgorithmFrame().setVisible(false);
            Main.setAlgorithmFrame(null);
            
            Graph graph = Main.getCurrentNetwork();
            
            graph.resetAllArcs();
            graph.resetAllNodes();
            
            Node startNode = (Node) graph.getNodeById((String) DijkstrasFrame.getFromBox().getSelectedItem());
            Node endNode = (Node) graph.getNodeById((String) DijkstrasFrame.getToBox().getSelectedItem());
            ArrayList<Node> path = Dijkstras.getShortestPath(graph, startNode, endNode);

            if (path != null) 
            {
                Main.writeText("Shortest path from " + startNode + " to " + endNode + ": " + path + " = " + graph.getPathWeight(path) + " total weight.");
                graph.drawPath(path);
            } else Main.writeText("Shortest path from " + startNode + " to " + endNode + ": Unreachable!");
        }
    }


}