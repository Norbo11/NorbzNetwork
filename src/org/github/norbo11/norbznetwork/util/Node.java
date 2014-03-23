package org.github.norbo11.norbznetwork.util;

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
    public static final Color NODE_TEXT_COLOR = Color.BLACK;
    public static final Color NODE_PATH_COLOR = Color.RED;
    
    private char id;
    private double x;
    private double y;
    private JPanel panel;
    private boolean mouseOver;
    private int pLabel = -1;
    private int tLabel = -1;
    
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Node(char id, Point location)
    {
        this(id, location.getX(), location.getY());
    }
    
    public Node(char id, double x, double y)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        
        panel = new JPanel();
        panel.setBounds((int) x - CLICKABLE_DIAMETER / 2, (int) y - CLICKABLE_DIAMETER / 2, CLICKABLE_DIAMETER, CLICKABLE_DIAMETER);
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
        
        x = this.x - width / 2;
        y = this.y - height / 2;
        
        g.setPaint(NODE_TEXT_COLOR);
        g.drawString(String.valueOf(id), (int) this.x - 3, (int) y - 4);
        
        g.setPaint(NODE_COLOR);
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
