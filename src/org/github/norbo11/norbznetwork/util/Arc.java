package org.github.norbo11.norbznetwork.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Arc {
    private static final Color ARC_COLOR = Node.NODE_COLOR;
    private double x1, y1, x2, y2;
    private Node startNode;
    private Node endNode;
    private int duration;
    private Color color = ARC_COLOR;
    
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
        x2 = endNode.getX();
        y2 = endNode.getY();
    }

    public Arc(Node startNode, double x2, double y2)
    {
        this.startNode = startNode;
        this.x1 = startNode.getX();
        this.y1 = startNode.getY();
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
    
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void draw(Graphics2D g)
    {
        g.setPaint(color);
        g.draw(new Line2D.Double(x1, y1, x2, y2));
        g.drawString(duration + "", (int) ((x1 + x2) / 2), (int) ((y1 + y2) / 2 - 4)); 
    }
    
    public String toString()
    {
        return getStartNode() + " to " + getEndNode();
    }
}
