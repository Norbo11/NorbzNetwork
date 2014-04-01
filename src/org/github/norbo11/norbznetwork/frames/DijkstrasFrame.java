package org.github.norbo11.norbznetwork.frames;

import static org.github.norbo11.norbznetwork.util.GUIHelper.displayMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.github.norbo11.norbznetwork.algorithms.Dijkstras;
import org.github.norbo11.norbznetwork.main.NetworkManager;
import org.github.norbo11.norbznetwork.network.Node;

public class DijkstrasFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JComboBox<Character> fromBox = null;
    private static JComboBox<Character> toBox = null;
    private static JButton btnConfirm = null;
    
    public static JComboBox<Character> getFromBox() {
        return fromBox;
    }

    public static JComboBox<Character> getToBox() {
        return toBox;
    }

    public static JButton getBtnConfirm() {
        return btnConfirm;
    }
    
    public class DijkstrasFrameListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == DijkstrasFrame.getBtnConfirm())
            {
                NetworkManager.resetAllArcs();
                NetworkManager.resetAllNodes();
                
                Node startNode = (Node) NetworkManager.getNodeById((Character) DijkstrasFrame.getFromBox().getSelectedItem());
                Node endNode = (Node) NetworkManager.getNodeById((Character) DijkstrasFrame.getToBox().getSelectedItem());
                Vector<Node> path = Dijkstras.getShortestPath(startNode, endNode);
                
                if (path != null) 
                {
                    displayMessage("Shortest path from " + startNode + " to " + endNode + ": " + path + " = " + NetworkManager.getPathWeight(path) + " total weight.");
                    NetworkManager.drawPath(path);
                } else displayMessage("Shortest path from " + startNode + " to " + endNode + ": Unreachable!");
                
                dispose();
            }
        }
    }
    
    public DijkstrasFrame()
    {
        super("Shortest Path");
        
        getContentPane().setLayout(null);
        setBounds(100, 100, 244, 145);
        
        JLabel lblFrom = new JLabel("From:");
        lblFrom.setBounds(10, 11, 111, 14);
        getContentPane().add(lblFrom);
        
        JLabel lblTo = new JLabel("To:");
        lblTo.setBounds(10, 36, 111, 14);
        getContentPane().add(lblTo);
        
        Vector<Node> nodes = NetworkManager.getNodes();
        Vector<Character> v = new Vector<Character>();
        for (Node node : nodes)
        {
            v.add(node.getId());
        }
        
        fromBox = new JComboBox<Character>(v);
        fromBox.setBounds(131, 8, 87, 20);
        getContentPane().add(fromBox);
        
        toBox = new JComboBox<Character>(v);
        toBox.setBounds(131, 33, 87, 20);
        getContentPane().add(toBox);
        
        btnConfirm = new JButton("Find shortest route");
        btnConfirm.setBounds(10, 75, 208, 23);
        btnConfirm.addActionListener(new DijkstrasFrameListener());
        getContentPane().add(btnConfirm);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
}
