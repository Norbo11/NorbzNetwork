package org.github.norbo11.norbznetwork.algorithms;

import java.util.Vector;

import org.github.norbo11.norbznetwork.main.NetworkManager;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.network.Network;

public class Prims {
    private static Vector<Node> minNodes = null;
    private static Vector<Arc> minArcs = null;
    private static Network network = null;
    static int i = 0;
    
    public static Network getMinimumSpanningTree()
    {
        minNodes = new Vector<Node>();
        minArcs = new Vector<Arc>();
        minNodes.add(NetworkManager.getNodes().get(0));
        
        
        network = new Network(minNodes, minArcs);
        
        formTree();
        
        return network;
    }
    
    private static Vector<Arc> getArcsInMinTree()
    {
        Vector<Arc> arcs = new Vector<Arc>();
        
        for (Node node : minNodes)
        {
            Vector<Arc> connectedArcs = NetworkManager.getAllConnectedArcs(node);
            for (Arc connectedArc : connectedArcs)
            {
                if (!arcs.contains(connectedArc) && (!minNodes.contains(connectedArc.getStartNode()) || !minNodes.contains(connectedArc.getEndNode()))) arcs.add(connectedArc);
            }
        }
        
        return arcs;
    }
    
    private static Arc getMinArc()
    {
        Vector<Arc> arcs = getArcsInMinTree();
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
        return minNodes.containsAll(NetworkManager.getNodes());
    }
}
