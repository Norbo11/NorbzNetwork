package org.github.norbo11.norbznetwork.algorithms;

import java.util.Collections;
import java.util.Vector;

import org.github.norbo11.norbznetwork.main.NetworkManager;
import org.github.norbo11.norbznetwork.network.Node;

public class Dijkstras {

    public static Vector<Node> getShortestPath(Node startNode, Node endNode) 
    {
        Vector<Node> path = new Vector<Node>();

        startNode.setpLabel(0);        
        assignLabels(startNode, endNode);
        
        if (endNode.getpLabel() == -1)
        {
            return null;
        }
        
        path.add(endNode);
        Node lastNode = endNode;
        while (lastNode != startNode)
        {
            Node best = lastNode.getBestFrom();
            path.add(best);
            lastNode = best;
        }
        
        Collections.reverse(path);
        return path;
    }
    
    private static void assignLabels(Node startNode, final Node endNode) {
        //Assign T-Labels to all nodes connected to startNode
        for (Node node : NetworkManager.getAllConnectedNodes(startNode))
        {
            if (node.getpLabel() == -1)
            {
                int newTLabel = (startNode.getpLabel() == -1 ? 0 : startNode.getpLabel()) + NetworkManager.getDuration(startNode, node);
                
                if (node.gettLabel() == -1 || newTLabel < node.gettLabel())
                {
                    node.settLabel(newTLabel);
                    node.setBestFrom(startNode);
                }
            }
        }
        
        //Find smallest T-Label in the whole network
        Vector<Node> nodes = NetworkManager.getNodesWithTLabels();
        Node smallest = nodes.size() > 0 ? nodes.get(0) : null;
        for (Node node : nodes)
        {
            if (node.gettLabel() < smallest.gettLabel()) smallest = node;
        }
        
        //Convert smallest T-Label to a P-label, then assign labels starting from that label
        //If there is no smallest T-Label (because all labels are P labels), abort
        if (smallest != null) 
        {
            smallest.setpLabel(smallest.gettLabel());
            if (smallest != endNode) assignLabels(smallest, endNode);
        }
     }
}
