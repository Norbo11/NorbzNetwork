package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Network;
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
        node.setMouseOver(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        node.setMouseOver(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Network network = Main.getCurrentNetwork();
        
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            if (network.getCurrentDrawArc() == null) network.connectNode(node);
            else {
                network.connectCurrentArc(node);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {   
    }

}
