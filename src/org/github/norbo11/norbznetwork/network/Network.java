package org.github.norbo11.norbznetwork.network;

import java.awt.Point;
import java.util.ArrayList;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public class Network {
    private ArrayList<Node> nodes;
    private ArrayList<Arc> arcs;
    private Arc currentDrawArc = null;
    private String filename = "";

    public Network(ArrayList<Node> nodes, ArrayList<Arc> arcs, String filename)
    {
        this.nodes = nodes;
        this.arcs = arcs;
        this.filename = filename;
    }
    
    public Network(String filename) {
        this(new ArrayList<Node>(), new ArrayList<Arc>(), filename);
    }

    public Arc getCurrentDrawArc() {
        return currentDrawArc;
    }

    public  void setCurrentDrawArc(Arc currentDrawArc) {
        this.currentDrawArc = currentDrawArc;
    }

    public ArrayList<Arc> getArcs() {
        return arcs;
    }

    public  void setArcs(ArrayList<Arc> arcs) {
        this.arcs = arcs;
    }

    public  ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Point point) {
        nodes.add(new Node(getNextId(), point, this));
        Main.getNodesTab().updateNodes();
    }

    public void connectNode(Node node) {
        currentDrawArc = new Arc(node);
    }
    
    public void connectCurrentArc(Node node) {
        currentDrawArc.setEndNode(node);
        arcs.add(currentDrawArc);
        Main.getArcsTab().updateArcs();
        connectNode(node);
    }

    public Node getNodeById(String id) {
         for (Node node : nodes)
         {
             if (node.getId().equals(id)) return node;
         }
         return null;
    }
    
    public Arc getArcByNodes(Node node1, Node node2)
    {
        for (Arc arc : arcs)
        {
            if ((arc.getStartNode() == node1 && arc.getEndNode() == node2) || (arc.getStartNode() == node2 && arc.getEndNode() == node1))
            {
                return arc;
            }
        }
        return null;
    }
    
    public int getDuration(Node node1, Node node2)
    {
        Arc arc = getArcByNodes(node1, node2);
        return arc != null ? arc.getWeight(): 0;
    }

    public ArrayList<Node> getNodesWithPLabels(Node startNode) {
        ArrayList<Node> returnValue = new ArrayList<>();
        
        for (Node node : nodes)
        {
            if (node.getpLabel() != -1) returnValue.add(node);
        }
        
        return returnValue;
    }
    
    public ArrayList<Node> getNodesWithTLabels() {
        ArrayList<Node> returnValue = new ArrayList<>();
        
        for (Node node : nodes)
        {
            if (node.gettLabel() != -1 && node.getpLabel() == -1) returnValue.add(node);
        }
        
        return returnValue;
    }

    public void resetAllNodes() {
        for (Node node : nodes)
        {
            node.setpLabel(-1);
            node.settLabel(-1);
            node.setColor(Node.NODE_COLOR);
        }
    }

    public void drawPath(ArrayList<Node> path) {  
        clearAllPaint();
        
        int i = 0;
        for (i = 0; i <= path.size() - 2; i++)
        {
            Node node1 = path.get(i);
            Node node2 = path.get(i + 1);
            Arc arc = getArcByNodes(node1, node2);
            
            if (arc != null) {
                arc.setColor(Node.NODE_PATH_COLOR);
                node1.setColor(Node.NODE_PATH_COLOR);
                node2.setColor(Node.NODE_PATH_COLOR);
            }
        }
    }
    
    private void clearAllPaint() {
        for (Node node : nodes) {
            node.setColor(Node.NODE_COLOR);
        }
        
        for (Arc arc : arcs) {
            arc.setColor(Arc.ARC_COLOR);
        }
    }

    public int getPathWeight(ArrayList<Node> path)
    {
        int duration = 0;
        for (int i = 0; i < path.size() - 1; i++)
        {
            duration += getDuration(path.get(i), path.get(i + 1));
        }
        return duration;
    }

    public void resetAllArcs() {
        for (Arc arc : arcs)
        {
            arc.setColor(Arc.ARC_COLOR);
        }
    }

    public ArrayList<Arc> getAllConnectedArcs(Node node) {
        ArrayList<Arc> connectedArcs = new ArrayList<Arc>();
        
        for (Arc arc : arcs)
        {
            if (arc.getStartNode() == node || arc.getEndNode() == node) connectedArcs.add(arc);
        }
        
        return connectedArcs;
    }

    public Node getRandomNode(ArrayList<Node> ignore) {
        Node chosen = null;
        
        if (ignore.size() != nodes.size()) {
            do {
                chosen = nodes.get(RandomUtil.from0(nodes.size()));
            } while (ignore.contains(chosen));
        }
            
        return chosen;
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
    
    public String getNextId() {
        return String.valueOf((char) ((int) 'A' + nodes.size()));
    }

    public String getFilename() {
        return filename;
    }

    public void drawPart(Network network) {
        clearAllPaint();
        
        for (Arc arc : arcs) {
            if (network.getArcs().contains(arc)) arc.setColor(Node.NODE_PATH_COLOR);
        }
        
        for (Node node : nodes) {
            if (network.getNodes().contains(node)) node.setColor(Node.NODE_PATH_COLOR);
        }
    }

    public void deleteNode(Node node) {
        for (Arc arc : node.getNeighbouringArcs())
        {
            arcs.remove(arc);
        }
        
        nodes.remove(node);
        
        Main.getNodesTab().updateNodes();
    }

    public void deleteArc(Arc arc) {
        arcs.remove(arc);
        
        Main.getArcsTab().updateArcs();
    }
}
