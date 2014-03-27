package org.github.norbo11.norbznetwork.algorithms;

import java.util.Vector;

import org.github.norbo11.norbznetwork.main.NetworkManager;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Node;

public class Prims {
    private static Vector<Node> minNodes = null;
    private static Vector<Arc> minArcs = null;
    private static Vector<Node> nodes = null;
    
    public static void getMinimumSpanningTree()
    {
        minNodes = new Vector<Node>();
        minArcs = new Vector<Arc>();
        nodes = NetworkManager.getNodes();
        minNodes.add(nodes.get(0));
        
        formTree();
        drawTree();
    }
    
    private static Arc getMinArc()
    {
        Vector<Arc> arcs = NetworkManager.getArcs();
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
        
        if (!minNodes.contains(smallest.getStartNode()) && !minNodes.contains(smallest.getEndNode()))
        {
            minArcs.add(smallest);
            minNodes.add(smallest.getStartNode());
            minNodes.add(smallest.getEndNode());
        }
        
        System.out.println("Min arcs: " + minArcs);
        System.out.println("Min nodes: " + minNodes);
        
        if (!isSpanning()) formTree();
    }
    
    private static boolean isSpanning()
    {
        return (NetworkManager.getNodes().equals(minNodes));
    }
    
    private static void drawTree()
    {
        Vector<Arc> arcs = NetworkManager.getArcs();
        arcs.clear();
        arcs.addAll(minArcs);
    }
}
