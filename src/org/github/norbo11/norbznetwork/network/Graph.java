package org.github.norbo11.norbznetwork.network;

import java.awt.Point;
import java.util.ArrayList;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public class Graph {
    private ArrayList<Node> nodes;
    private ArrayList<Arc> arcs;
    private Arc currentDrawArc = null;
    private String filename = "";

    public Graph(ArrayList<Node> nodes, ArrayList<Arc> arcs, String filename)
    {
        this.nodes = nodes;
        this.arcs = arcs;
        this.filename = filename;
    }
    
    public Graph(String filename) {
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
    
    public double getDuration(Node node1, Node node2)
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
        
        //Set color of first node and arc
        Node firstNode = path.get(0);
        firstNode.setColor(Node.NODE_PATH_START_COLOR);
        firstNode.setBold(true);
        
        Arc arc = getArcByNodes(firstNode, path.get(1));
        arc.setColor(Node.NODE_PATH_COLOR);
        arc.setBold(true);
        
        //Set color of last node and arc
        Node lastNode = path.get(path.size() - 1);
        lastNode.setColor(Node.NODE_PATH_START_COLOR);
        lastNode.setBold(true);
        
        arc = getArcByNodes(path.get(path.size() - 2), lastNode);
        arc.setColor(Node.NODE_PATH_COLOR);
        arc.setBold(true);
        
        //Set color of all intermediate nodes and arcs
        for (int i = 1; i <= path.size() - 3; i++)
        {
            Node node1 = path.get(i);
            Node node2 = path.get(i + 1);
            Arc connectingArc = getArcByNodes(node1, node2);
            
            if (connectingArc != null) {
                connectingArc.setColor(Node.NODE_PATH_COLOR);
                node1.setColor(Node.NODE_PATH_COLOR);
                node2.setColor(Node.NODE_PATH_COLOR);
                
                connectingArc.setBold(true);
                node1.setBold(true);
                node2.setBold(true);
            }
        }
    }
    
    public void clearAllPaint() {
        for (Node node : nodes) {
            node.setBold(false);
            node.setColor(Node.NODE_COLOR);
        }
        
        for (Arc arc : arcs) {
            arc.setBold(false);
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
    
    public int getPathWeightFromPriorities(ArrayList<Integer> path)
    {
        return getPathWeight(createTraversalPathFromPriorities(path));
    }
    
    
    private Node computeNodeWithHighestPriority(ArrayList<Integer> priorities, ArrayList<Node> nodes) {
        Node highestPriorityNode = null;
        Integer highestPriority = 0;
        
        //Go through all supplied nodes
        for (Node node : nodes) {
            //Get the priority for the current node (by using its index from the global list of nodes)
            Integer candidatePriority = priorities.get(this.nodes.indexOf(node));
            
            //If its higher than previous, make that node the current highest priority node
            if (candidatePriority > highestPriority) {
                highestPriority = candidatePriority;
                highestPriorityNode = node;
            }
        }
        
        return highestPriorityNode;
    }
    
    public ArrayList<Node> createTraversalPathFromPriorities(ArrayList<Integer> priorities) {
        ArrayList<Node> traversalPath = new ArrayList<Node>();
        
        //Find the node with the highest priority in the entire graph
        Node lastNode = computeNodeWithHighestPriority(priorities, this.nodes); 

        //Add it to the reconstructed path
        traversalPath.add(lastNode);

        ArrayList<Node> neighbours = lastNode.getNeighbouringNodes();
        
        //While this node has neighbours
        while (neighbours.size() > 0) {
            //Find the node with the highest priority, considering only neighbour nodes
            lastNode = computeNodeWithHighestPriority(priorities, neighbours);
            
            //If a valid node exists
            if (lastNode != null) {
                //Add it the path
                traversalPath.add(lastNode);
                            
                //Compute the new valid neighbours for the next iteration of the loop (traversalPath is the list of nodes to ignore when looking for members)
                neighbours = lastNode.getNeighbouringNodes(traversalPath);
            } else {
                neighbours.clear();
            }
        }
        
        return traversalPath;
    }
    
    public ArrayList<Integer> createPrioritiesFromTraversalPath(ArrayList<Node> traversalPath) {        
        ArrayList<Integer> priorities = new ArrayList<Integer>();
        
        //Initialize empty priorities
        for (int i = 1; i <= nodes.size(); i++) {
            priorities.add(0);
        }
        
        //Initialize current priority to the highest possible
        Integer currentPriority = nodes.size();
        
        for (Node node : traversalPath) {
            int nodeLocus = nodes.indexOf(node);
            
            priorities.set(nodeLocus, currentPriority);
            currentPriority--;
        }

        return priorities;
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
        ArrayList<Node> available = new ArrayList<Node>(nodes);
        available.removeAll(ignore);
        
        Node chosen = null;
        
        if (available.size() > 0)
            chosen = available.get(RandomUtil.from0(available.size()));

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
        int offset  = nodes.size();
        
        if (offset >= 26) {
            offset += 6;
        }
        
        return String.valueOf((char) ((int) 'A' + offset));
    }

    public String getFilename() {
        return filename;
    }

    public void drawPart(Graph graph) {
        clearAllPaint();
        
        for (Arc arc : arcs) {
            if (graph.getArcs().contains(arc)) arc.setColor(Node.NODE_PATH_COLOR);
        }
        
        for (Node node : nodes) {
            if (graph.getNodes().contains(node)) node.setColor(Node.NODE_PATH_COLOR);
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
    
    public void connectAllNodes() {
        arcs.clear();
        
        for (Node node1 : nodes) {
            for (Node node2 : nodes) {
                if (node2 != node1) {
                    if (getArcByNodes(node1, node2) == null) {
                        //Calculate distance between nodes
                        double weight = Math.sqrt(Math.pow(node1.getPoint().getX() - node2.getPoint().getX(), 2) + Math.pow(node1.getPoint().getY() - node2.getPoint().getY(), 2));
                        arcs.add(new Arc(node1, node2, weight));
                    }
                }
            }
        }
    }

    public void randomizeAllWeights() {
        for (Arc arc : arcs) {
            arc.setWeight(RandomUtil.between(1, 200));
        }
    }
}
