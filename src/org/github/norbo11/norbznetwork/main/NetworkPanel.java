package org.github.norbo11.norbznetwork.main;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Node;

public class NetworkPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    public NetworkPanel()
    {
        super(null);
    }
    
    @Override
    public void paint(Graphics graphics) {
        super.paintComponent(graphics);
        
        Graphics2D g = (Graphics2D) graphics;
        
        for (Node node : NetworkManager.getNodes())
        {
            node.draw(g);
        }
        
        Arc currentArc = NetworkManager.getCurrentDrawArc();
        if (currentArc != null) currentArc.draw(g);
        
        for (Arc arc : NetworkManager.getArcs())
        {
            arc.draw(g);
        }
        
        revalidate();
        repaint();
      }
      
}
