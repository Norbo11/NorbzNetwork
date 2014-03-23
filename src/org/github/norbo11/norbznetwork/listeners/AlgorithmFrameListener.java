package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

import org.github.norbo11.norbznetwork.frames.DijkstrasFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.main.NetworkManager;
import org.github.norbo11.norbznetwork.util.Node;

public class AlgorithmFrameListener implements ActionListener {

    private Node startNode = null;
    private Vector<Node> path = new Vector<Node>();

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == DijkstrasFrame.getBtnConfirm())
        {
            Main.getAlgorithmFrame().setVisible(false);
            Main.setAlgorithmFrame(null);
            
            NetworkManager.resetAllLabels();
            path.clear();
            
            startNode = (Node) NetworkManager.getNodeById((Character) DijkstrasFrame.getFromBox().getSelectedItem());
            startNode.setpLabel(0);
            Node endNode = (Node) NetworkManager.getNodeById((Character) DijkstrasFrame.getToBox().getSelectedItem());
            
            assignLabels(startNode);
            
            for (Node node : NetworkManager.getNodes())
            {
                System.out.println(node + "'s P-Label: " + node.getpLabel());
            }
            path.add(endNode);
            getShortestPath(endNode);
            Collections.reverse(path);
            System.out.println(path);
            NetworkManager.drawPath(path);
            
        }
    }

    private void getShortestPath(Node endNode) {
        
        for (Node node : NetworkManager.getAllConnectedNodes(endNode))
        {
            if (!path.contains(node) && Math.abs(node.getpLabel() - endNode.getpLabel()) == NetworkManager.getDuration(node, endNode))
            {
                path.add(node);
                if (node != startNode) 
                {
                    getShortestPath(node);
                }
                break;
            }
        }
    }

    private void assignLabels(Node startNode) {
       for (Node node : NetworkManager.getAllConnectedNodes(startNode))
       {
           if (node.getpLabel() == -1)
           {
               int newTLabel = (startNode.getpLabel() == -1 ? 0 : startNode.getpLabel()) + NetworkManager.getDuration(startNode, node);
               
               if (node.gettLabel() == -1 || newTLabel < node.gettLabel())
               {
                   node.settLabel(newTLabel);
                   System.out.println(node + ": " + newTLabel);
               }
           }
       }
       
       //Find smallest T label
       Vector<Node> nodes = NetworkManager.getNodesWithTLabels();
       Node smallest = nodes.size() > 0 ? nodes.get(0) : null;
       for (Node node : nodes)
       {
           if (node.gettLabel() < smallest.gettLabel()) smallest = node;
       }
       
       //Convert smallest T label to P label, then assign labels starting from that label
       //If there is no smallest P label (because all labels are P labels), abort
       System.out.println("Smallest: " + smallest);
       if (smallest != null) 
       {
           smallest.setpLabel(smallest.gettLabel());
           assignLabels(smallest);
       }
    }

}
