package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import org.github.norbo11.norbznetwork.frames.EditDistanceFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.main.NetworkManager;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.util.GUIHelper;

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
        {
            boolean intersecting = false;
            for (Arc arc : NetworkManager.getArcs())
            {
                intersecting = GUIHelper.IsIntersecting(arc.getPoint1(), arc.getPoint2(), e.getPoint(), Arc.CLICKABLE_WIDTH);
                if (intersecting) 
                {
                    Main.setEditDistanceFrame(new EditDistanceFrame(arc));
                    break;
                }
            }
            if (!intersecting) NetworkManager.addNode(e.getPoint());
        }
        
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
            currentArc.getPoint2().setLocation(e.getPoint());
        }
        
        for (Arc arc : NetworkManager.getArcs())
        {
            if (GUIHelper.IsIntersecting(arc.getPoint1(), arc.getPoint2(), e.getPoint(), Arc.CLICKABLE_WIDTH)) arc.setMouseOver(true);
            else arc.setMouseOver(false);
        }
     }

}
