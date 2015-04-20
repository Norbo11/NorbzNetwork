package org.github.norbo11.norbznetwork.main;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Graph;
import org.github.norbo11.norbznetwork.network.Node;

public class NetworkPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public NetworkPanel() {
        super(null); //Needed to set the layout to null
    }

    @Override
    public void paint(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        
        Graph graph = Main.getCurrentNetwork();
        
        
        for (Node node : graph.getNodes())
        {
            node.draw(g);
        }
        
        Arc currentArc = graph.getCurrentDrawArc();
        if (currentArc != null) currentArc.draw(g);
        
        for (Arc arc : graph.getArcs())
        {
            arc.draw(g);
        }
        
        revalidate();
        repaint();
      }
}
