package org.github.norbo11.norbznetwork.network;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.listeners.NodeMouseListener;

public class Node {
    public static final int DIAMETER = 15;
    public static final int CLICKABLE_DIAMETER = 30;
    public static final Color NODE_COLOR = Color.DARK_GRAY;
    public static final Color NODE_PATH_COLOR = Color.RED;
    public static final Color NODE_PATH_START_COLOR = Color.BLUE;
    
    private String id;
    private Point point;
    private JPanel panel;
    private boolean bold;
    private double pLabel = -1;
    private double tLabel = -1;
    private Node bestFrom = null;
    private Color color = NODE_COLOR;
    private Graph parent;
    
    public Node(String id, Point point, Graph parent)
    {
        this.id = id;
        this.point = new Point(point);
        this.parent = parent;
        
        panel = new JPanel();
        panel.setBounds((int) point.x - CLICKABLE_DIAMETER / 2, (int) point.y - CLICKABLE_DIAMETER / 2, CLICKABLE_DIAMETER, CLICKABLE_DIAMETER);
        panel.addMouseListener(new NodeMouseListener(this));
        Main.getNetworkPanel().add(panel);
    }
    
    public Node() {
    }

    
    /* Getters and setters */    

    public Graph getParent() {
        return parent;
    }

    public Point getPoint() {
        return point;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Node getBestFrom() {
        return bestFrom;
    }

    public void setBestFrom(Node bestFrom) {
        this.bestFrom = bestFrom;
    }

    public double getpLabel() {
        return pLabel;
    }

    public double gettLabel() {
        return tLabel;
    }

    public void setpLabel(double pLabel) {
        this.pLabel = pLabel;
    }

    public void settLabel(double tLabel) {
        this.tLabel = tLabel;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
 
    public void draw(Graphics2D g) {
        double x = 0;
        double y = 0;
        double width = 0;
        double height = 0;
        
        if (!bold)
        {
            width = DIAMETER;
            height = DIAMETER;
        } else {
            width = CLICKABLE_DIAMETER;
            height = CLICKABLE_DIAMETER;
        }
        
        x = point.x - width / 2;
        y = point.y - height / 2;
        
        g.setPaint(color);
        g.drawString(String.valueOf(id), (int) point.x - 3, (int) y - 4);
        g.fill(new Ellipse2D.Double(x, y, width, height));
    }
    
    public ArrayList<Node> getNeighbouringNodes() {
        return getNeighbouringNodes(new ArrayList<Node>());
    }
    
    public ArrayList<Node> getNeighbouringNodes(ArrayList<Node> ignore)
    {
        ArrayList<Node> connections = new ArrayList<>();
        for (Arc arc : parent.getArcs())
        {
            if (arc.getStartNode() == this)
            {
                if (!connections.contains(arc.getEndNode()) && !ignore.contains(arc.getEndNode())) connections.add(arc.getEndNode());
            }
            
            if (arc.getEndNode() == this)
            {
                if (!connections.contains(arc.getStartNode()) && !ignore.contains(arc.getStartNode())) connections.add(arc.getStartNode());
            }
        }
        return connections;
    }
    
    public ArrayList<Arc> getNeighbouringArcs()
    {
        ArrayList<Arc> neighbours = new ArrayList<Arc>();
        for (Arc arc : parent.getArcs())
        {
            if (arc.getStartNode() == this || arc.getEndNode() == this)
            {
                if (!neighbours.contains(arc)) neighbours.add(arc);
            }

        }
        return neighbours;
    }
    
    public Arc getArcJoiningNode(Node node) {
        return parent.getArcByNodes(this, node);
    }
    
    public String toString()
    {
        return String.valueOf(id);
    }

    public ArrayList<Arc> getNeighbouringArcsWhichDontConnectGivenNodes(ArrayList<Node> ignore) {
        ArrayList<Arc> neighbours = new ArrayList<Arc>();
        for (Arc arc : parent.getArcs())
        {
            if (arc.getStartNode() == this)
            {
                if (!neighbours.contains(arc) && !ignore.contains(arc.getNodeConnecting(arc.getStartNode()))) neighbours.add(arc);
            } else if  (arc.getEndNode() == this) {
                if (!neighbours.contains(arc) && !ignore.contains(arc.getNodeConnecting(arc.getEndNode()))) neighbours.add(arc);
            }

        }
        return neighbours;
    }
    
}
