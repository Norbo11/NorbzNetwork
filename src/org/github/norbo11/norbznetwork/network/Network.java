package org.github.norbo11.norbznetwork.network;

import java.util.Vector;

import org.github.norbo11.norbznetwork.main.NetworkManager;

public class Network {
    private Vector<Node> nodes = null;
    private Vector<Arc> arcs = null;
    
    public Network(Vector<Node> nodes, Vector<Arc> arcs)
    {
        this.nodes = nodes;
        this.arcs = arcs;
    }

    public Vector<Node> getNodes() {
        return nodes;
    }

    public Vector<Arc> getArcs() {
        return arcs;
    }

    public void setNodes(Vector<Node> nodes) {
        this.nodes = nodes;
    }

    public void setArcs(Vector<Arc> arcs) {
        this.arcs = arcs;
    }
    
    public void draw()
    {
        Vector<Arc> currentArcs = NetworkManager.getArcs();
        currentArcs.clear();
        currentArcs.addAll(arcs);
    }
    
    public int getWeight()
    {
        int duration = 0;
        for (Arc arc : arcs)
        {
            duration += arc.getWeight();
        }
        return duration;
    }
}
