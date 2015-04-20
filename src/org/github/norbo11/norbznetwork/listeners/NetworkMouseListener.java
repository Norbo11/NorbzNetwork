package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import org.github.norbo11.norbznetwork.frames.EditDistanceFrame;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Graph;
import org.github.norbo11.norbznetwork.util.GUIUtil;
//github.com/Norbo11/NorbzNetwork.git
import org.github.norbo11.norbznetwork.network.Arc;

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
        Graph graph = Main.getCurrentNetwork();
        
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            boolean intersecting = false;
            for (Arc arc : graph.getArcs())
            {
                intersecting = GUIUtil.IsIntersecting(arc.getPoint1(), arc.getPoint2(), e.getPoint(), Arc.CLICKABLE_WIDTH);
                if (intersecting) 
                {
                    new EditDistanceFrame(arc);
                    break;
                }
            }
            if (!intersecting) graph.addNode(e.getPoint());
        }
        
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            graph.setCurrentDrawArc(null);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        if (Main.getCurrentSimulation() == null || !Main.getCurrentSimulation().isRunning()) {
            Graph graph = Main.getCurrentNetwork();
            
            Arc currentArc = graph.getCurrentDrawArc();
            
            if (currentArc != null)
            {
                currentArc.getPoint2().setLocation(e.getPoint());
            }
            
            for (Arc arc : graph.getArcs())
            {
                if (GUIUtil.IsIntersecting(arc.getPoint1(), arc.getPoint2(), e.getPoint(), Arc.CLICKABLE_WIDTH)) arc.setBold(true);
                else arc.setBold(false);
            }
        }
     }
}
