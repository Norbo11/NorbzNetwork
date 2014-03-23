package org.github.norbo11.norbznetwork.main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.util.Arc;
import org.github.norbo11.norbznetwork.util.Node;

public class NetworkManager {
    private static ArrayList<Node> nodes = new ArrayList<Node>();
    private static ArrayList<Arc> arcs = new ArrayList<Arc>();
    private static Arc currentDrawArc = null;

    public static Arc getCurrentDrawArc() {
        return currentDrawArc;
    }

    public static void setCurrentDrawArc(Arc currentDrawArc) {
        NetworkManager.currentDrawArc = currentDrawArc;
    }

    public static ArrayList<Arc> getArcs() {
        return arcs;
    }

    public static void setArcs(ArrayList<Arc> arcs) {
        NetworkManager.arcs = arcs;
    }

    public static ArrayList<Node> getNodes() {
        return nodes;
    }

    public static void setNodes(ArrayList<Node> nodes) {
        NetworkManager.nodes = nodes;
    }

    public static void addNode(Point point) {
        nodes.add(new Node(Node.getNextId(), point));
    }

    public static void connectNode(Node node, double x2, double y2) {
        currentDrawArc = new Arc(node, x2, y2);
    }
    
    public static void connectCurrentArc(Node node, double x2, double y2) {
        currentDrawArc.setEndNode(node);
        arcs.add(currentDrawArc);
        Main.getArcsTab().updateArcs();
        connectNode(node, x2, y2);
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
        return getArcByNodes(node1, node2).getDuration();
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

    public static void resetAllLabels() {
        for (Node node : nodes)
        {
            node.setpLabel(-1);
            node.settLabel(-1);
        }
    }

    public static void drawPath(Vector<Node> path) {
        int i = 0;
        //A, E, F, D, C
        //0, 1, 2
        //AB, BC, CD
        for (i = 0; i <= path.size() - 2; i++)
        {
            System.out.println("changing color of arc" + getArcByNodes(path.get(i), path.get(i + 1)).getDuration());
            getArcByNodes(path.get(i), path.get(i + 1)).setColor(Node.NODE_PATH_COLOR);
        }
    }
}
