package org.github.norbo11.norbznetwork.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.github.norbo11.norbznetwork.network.Arc;

public class EditDistanceFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static Arc arc = null;
    private static JTextField textField;
    private static JButton btnOk;
    private static JButton btnCancel;
    
    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {            
            if (e.getSource() == btnOk || e.getSource() == textField)
            {
                arc.setWeight(Integer.valueOf(textField.getText()));
            }
            
            Main.getEditDistanceFrame().setVisible(false);
            Main.setEditDistanceFrame(null);
        }
        
    }
    
    public EditDistanceFrame(Arc arc)
    {
        super(arc + "");
        
        EditDistanceFrame.arc = arc;
        
        getContentPane().setLayout(null);
        setBounds(100, 100, 224, 110);
        
        JLabel lblFrom = new JLabel("Distance:");
        lblFrom.setBounds(10, 11, 111, 14);
        getContentPane().add(lblFrom);
        
        Listener listener = new Listener();
        textField = new JTextField();
        textField.setBounds(85, 8, 111, 20);
        textField.addActionListener(listener);
        getContentPane().add(textField);
        textField.setColumns(10);   
        
        btnOk = new JButton("OK");
        btnOk.setBounds(10, 36, 89, 23);
        btnOk.addActionListener(listener);
        getContentPane().add(btnOk);
        
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(107, 36, 89, 23);
        btnCancel.addActionListener(listener);
        getContentPane().add(btnCancel);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static Arc getArc() {
        return arc;
    }

    public static JTextField getTextField() {
        return textField;
    }

    public static void setArc(Arc arc) {
        EditDistanceFrame.arc = arc;
    }

    public static  void setTextField(JTextField textField) {
        EditDistanceFrame.textField = textField;
    }
}
