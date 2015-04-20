package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Graph;
import org.github.norbo11.norbznetwork.network.Node;

public class NodeMouseListener implements MouseListener {

    public NodeMouseListener(Node node)
    {
        this.node = node;
    }
    
    private Node node;
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        node.setBold(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        node.setBold(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Graph graph = Main.getCurrentNetwork();
        
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            if (graph.getCurrentDrawArc() == null) graph.connectNode(node);
            else {
                graph.connectCurrentArc(node);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {   
    }

}
