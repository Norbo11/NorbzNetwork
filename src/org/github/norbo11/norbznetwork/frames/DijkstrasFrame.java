package org.github.norbo11.norbznetwork.frames;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.github.norbo11.norbznetwork.listeners.AlgorithmFrameListener;
import org.github.norbo11.norbznetwork.network.Node;

public class DijkstrasFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JComboBox<String> fromBox = null;
    private static JComboBox<String> toBox = null;
    private static JButton btnConfirm = null;
    
    public static JComboBox<String> getFromBox() {
        return fromBox;
    }

    public static JComboBox<String> getToBox() {
        return toBox;
    }

    public static JButton getBtnConfirm() {
        return btnConfirm;
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
        
        ArrayList<Node> nodes = Main.getCurrentNetwork().getNodes();
        Vector<String> v = new Vector<String>();
        for (Node node : nodes)
        {
            v.add(node.getId());
        }
        
        fromBox = new JComboBox<String>(v);
        fromBox.setBounds(131, 8, 87, 20);
        getContentPane().add(fromBox);
        
        toBox = new JComboBox<String>(v);
        toBox.setBounds(131, 33, 87, 20);
        getContentPane().add(toBox);
        
        btnConfirm = new JButton("Find shortest route");
        btnConfirm.setBounds(10, 75, 208, 23);
        btnConfirm.addActionListener(new AlgorithmFrameListener());
        getContentPane().add(btnConfirm);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
