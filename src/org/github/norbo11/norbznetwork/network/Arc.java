package org.github.norbo11.norbznetwork.network;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;

public class Arc {
    public static final Color ARC_COLOR = Node.NODE_COLOR;
    public static final int CLICKABLE_WIDTH = 10;
    public static final int HOVER_WIDTH = 4;
    
    private Point point1;
    private Point point2;
    private Node startNode;
    private Node endNode;
    private double weight;
    private Color color = ARC_COLOR;
    private boolean bold;
    
    public Arc(Node startNode)
    {
        this.startNode = startNode;
        
        this.point1 = new Point(startNode.getPoint());
        this.point2 = new Point(startNode.getPoint());
    }
    
    
    public Arc(Node startNode, Node endNode, double weight)
    {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
        
        this.point1 = new Point(startNode.getPoint());
        this.point2 = new Point(endNode.getPoint());
    }
    
    
    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public Color getColor() {
        return color;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
        point2.setLocation(endNode.getPoint());
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int duration) {
        this.weight = duration;
    }

    public void draw(Graphics2D g)
    {
        g.setPaint(color);
        
        if (bold) g.setStroke(new BasicStroke(HOVER_WIDTH));
        else g.setStroke(new BasicStroke(1));
        
        g.draw(new Line2D.Double(point1, point2));
        g.drawString(getRoundedWeight() + "", (point1.x + point2.x) / 2, (point1.y + point2.y) / 2 - 4); 
    }
    
    public String getRoundedWeight() {
        return new DecimalFormat("#.##").format(weight);
    }


    public String toString()
    {
        return getStartNode() + " to " + getEndNode();
    }

    public Node getNodeConnecting(Node node) {
        if (node == getEndNode()) return getStartNode();
        if (node == getStartNode()) return getEndNode();
        return null;
    }
}
