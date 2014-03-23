package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import org.github.norbo11.norbznetwork.main.NetworkManager;
import org.github.norbo11.norbznetwork.util.Arc;

public class NetworkMouseListener extends MouseInputAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            NetworkManager.addNode(e.getPoint());
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            NetworkManager.setCurrentDrawArc(null);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        Arc currentArc = NetworkManager.getCurrentDrawArc();
        
        if (currentArc != null)
        {
            currentArc.setX2(e.getX());
            currentArc.setY2(e.getY());
        }
     }

}
