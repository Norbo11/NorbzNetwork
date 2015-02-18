package org.github.norbo11.norbznetwork.algorithms;

import java.util.ArrayList;

import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Network;
import org.github.norbo11.norbznetwork.network.Node;

public class Prims {
    private static ArrayList<Node> minNodes = null;
    private static ArrayList<Arc> minArcs = null;
    private static Network network = null;
    
    public static Network getMinimumSpanningTree(Network network)
    {
        Prims.network = network;
        
        minNodes = new ArrayList<Node>();
        minArcs = new ArrayList<Arc>();
        minNodes.add(network.getNodes().get(0));
        
        
        network = new Network(minNodes, minArcs, network.getFilename());
        
        formTree();
        
        return network;
    }
    
    private static ArrayList<Arc> getArcsInMinTree()
    {
        ArrayList<Arc> arcs = new ArrayList<Arc>();
        
        for (Node node : minNodes)
        {
            ArrayList<Arc> connectedArcs = network.getAllConnectedArcs(node);
            for (Arc connectedArc : connectedArcs)
            {
                if (!arcs.contains(connectedArc) && (!minNodes.contains(connectedArc.getStartNode()) || !minNodes.contains(connectedArc.getEndNode()))) arcs.add(connectedArc);
            }
        }
        
        return arcs;
    }
    
    private static Arc getMinArc()
    {
        ArrayList<Arc> arcs = getArcsInMinTree();
        if (arcs.size() == 0) return null;
        
        Arc smallest = arcs.get(0);
        for (Arc arc : arcs)
        {
            if (!minArcs.contains(arc) && arc.getWeight() < smallest.getWeight()) smallest = arc;
        }
        
        return smallest;
    }
    
    private static void formTree()
    {
        Arc smallest = getMinArc();
        
        if (smallest == null) return;
        
        minArcs.add(smallest);
        
        if (!minNodes.contains(smallest.getStartNode()))
        {
            minNodes.add(smallest.getStartNode());
        }
        
        if (!minNodes.contains(smallest.getEndNode()))
        {
            minNodes.add(smallest.getEndNode());
        }
        
        if (!isSpanning()) 
        {
            formTree();
        }
    }
    
    private static boolean isSpanning()
    {
        return minNodes.containsAll(network.getNodes());
    }
}
