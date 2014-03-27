package org.github.norbo11.norbznetwork.main;

import java.awt.Point;
import java.util.Vector;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Node;

public class NetworkManager {
    private static Vector<Node> nodes = new Vector<Node>();
    private static Vector<Arc> arcs = new Vector<Arc>();
    private static Arc currentDrawArc = null;

    public static Arc getCurrentDrawArc() {
        return currentDrawArc;
    }

    public static void setCurrentDrawArc(Arc currentDrawArc) {
        NetworkManager.currentDrawArc = currentDrawArc;
    }

    public static Vector<Arc> getArcs() {
        return arcs;
    }

    public static void setArcs(Vector<Arc> arcs) {
        NetworkManager.arcs = arcs;
    }

    public static Vector<Node> getNodes() {
        return nodes;
    }

    public static void setNodes(Vector<Node> nodes) {
        NetworkManager.nodes = nodes;
    }

    public static void addNode(Point point) {
        nodes.add(new Node(Node.getNextId(), point));
    }

    public static void connectNode(Node node) {
        currentDrawArc = new Arc(node);
    }
    
    public static void connectCurrentArc(Node node) {
        currentDrawArc.setEndNode(node);
        arcs.add(currentDrawArc);
        Main.getArcsTab().updateArcs();
        connectNode(node);
    }

    public static Node getNodeById(char id) {
         for (Node node : nodes)
         {
             if (node.getId() == id) return node;
         }
         return null;
    }
    
    public static Arc getArcByNodes(Node node1, Node node2)
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
    
    public static int getDuration(Node node1, Node node2)
    {
        Arc arc = getArcByNodes(node1, node2);
        return arc != null ? arc.getWeight(): 0;
    }
    
    public static Vector<Node> getAllConnectedNodes(Node node)
    {
        Vector<Node> connections = new Vector<>();
        for (Arc arc : arcs)
        {
            if (arc.getStartNode() == node)
            {
                if (!connections.contains(arc.getEndNode())) connections.add(arc.getEndNode());
            }
            
            if (arc.getEndNode() == node)
            {
                if (!connections.contains(arc.getStartNode())) connections.add(arc.getStartNode());
            }
        }
        return connections;
    }

    public static Vector<Node> getNodesWithPLabels(Node startNode) {
        Vector<Node> nodes = new Vector<>();
        
        for (Node node : NetworkManager.nodes)
        {
            if (node.getpLabel() != -1) nodes.add(node);
        }
        
        return nodes;
    }

    public static Vector<Node> getNodesWithTLabels() {
        Vector<Node> nodes = new Vector<>();
        
        
        for (Node node : NetworkManager.nodes)
        {
            if (node.gettLabel() != -1 && node.getpLabel() == -1) nodes.add(node);
        }
        
        return nodes;
    }

    public static void resetAllNodes() {
        for (Node node : nodes)
        {
            node.setpLabel(-1);
            node.settLabel(-1);
            node.setColor(Node.NODE_COLOR);
        }
    }

    public static void drawPath(Vector<Node> path) {
        int i = 0;
        for (i = 0; i <= path.size() - 2; i++)
        {
            Node node1 = path.get(i);
            Node node2 = path.get(i + 1);
            getArcByNodes(node1, node2).setColor(Node.NODE_PATH_COLOR);
            node1.setColor(Node.NODE_PATH_COLOR);
            node2.setColor(Node.NODE_PATH_COLOR);
        }
    }
    
    public static int pathWeight(Vector<Node> path)
    {
        int duration = 0;
        for (int i = 0; i < path.size() - 1; i++)
        {
            duration += NetworkManager.getDuration(path.get(i), path.get(i + 1));
        }
        return duration;
    }

    public static void resetAllArcs() {
        for (Arc arc : arcs)
        {
            arc.setColor(Arc.ARC_COLOR);
        }
    }
}
