package org.github.norbo11.norbznetwork.network;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.listeners.NodeMouseListener;
import org.github.norbo11.norbznetwork.main.NetworkManager;

public class Node {
    public static final int DIAMETER = 15;
    public static final int CLICKABLE_DIAMETER = 30;
    public static final Color NODE_COLOR = Color.DARK_GRAY;
    public static final Color NODE_PATH_COLOR = Color.RED;
    
    private char id;
    private Point point;
    private JPanel panel;
    private boolean mouseOver;
    private int pLabel = -1;
    private int tLabel = -1;
    private Node bestFrom = null;
    private Color color = NODE_COLOR;
    
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

    public int getpLabel() {
        return pLabel;
    }

    public int gettLabel() {
        return tLabel;
    }

    public void setpLabel(int pLabel) {
        this.pLabel = pLabel;
    }

    public void settLabel(int tLabel) {
        this.tLabel = tLabel;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Node(char id, Point point)
    {
        this.id = id;
        this.point = new Point(point);
        
        panel = new JPanel();
        panel.setBounds((int) point.x - CLICKABLE_DIAMETER / 2, (int) point.y - CLICKABLE_DIAMETER / 2, CLICKABLE_DIAMETER, CLICKABLE_DIAMETER);
        panel.addMouseListener(new NodeMouseListener(this));
        Main.getNetworkPanel().add(panel);
    }

    public char getId() {
        return id;
    }

    public void setId(char id) {
        this.id = id;
    }
 
    public void draw(Graphics2D g) {
        double x = 0;
        double y = 0;
        double width = 0;
        double height = 0;
        
        if (!mouseOver)
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

    public static char getNextId() {
        return (char) ((int) 'A' + NetworkManager.getNodes().size());
    }
    
    public String toString()
    {
        return String.valueOf(id);
    }
    
}
