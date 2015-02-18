package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import org.github.norbo11.norbznetwork.frames.EditDistanceFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Network;
import org.github.norbo11.norbznetwork.util.GUIUtil;

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
        Network network = Main.getCurrentNetwork();
        
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            boolean intersecting = false;
            for (Arc arc : network.getArcs())
            {
                intersecting = GUIUtil.IsIntersecting(arc.getPoint1(), arc.getPoint2(), e.getPoint(), Arc.CLICKABLE_WIDTH);
                if (intersecting) 
                {
                    Main.setEditDistanceFrame(new EditDistanceFrame(arc));
                    break;
                }
            }
            if (!intersecting) network.addNode(e.getPoint());
        }
        
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            network.setCurrentDrawArc(null);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        Network network = Main.getCurrentNetwork();
        
        Arc currentArc = network.getCurrentDrawArc();
        
        if (currentArc != null)
        {
            currentArc.getPoint2().setLocation(e.getPoint());
        }
        
        for (Arc arc : network.getArcs())
        {
            if (GUIUtil.IsIntersecting(arc.getPoint1(), arc.getPoint2(), e.getPoint(), Arc.CLICKABLE_WIDTH)) arc.setMouseOver(true);
            else arc.setMouseOver(false);
        }
     }

}
