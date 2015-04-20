package org.github.norbo11.norbznetwork.frames;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.github.norbo11.norbznetwork.algorithms.Simulation;
import org.github.norbo11.norbznetwork.algorithms.ga.Individual;

public class ViewPopulationFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static Simulation simulation;
    private static JTextArea textArea = new JTextArea();
    
    public ViewPopulationFrame()
    {
        super("Current Population");
        
        setBounds(100, 100, 500, 700);
        
        Container content = getContentPane();
        content.setLayout(new FlowLayout());
        
        content.add(textArea);
        
        setAlwaysOnTop(true);
        setVisible(true);
    }
    
    
    public static void update() {    
       
            
       
        String populationString = "";
        
        simulation.getPopulation().sort();
        for (Individual simpleIndividual : simulation.getPopulation().getIndividuals()) {
            populationString += simpleIndividual + "\n";
        }
        
        textArea.setText(populationString);
        
    }

    public static Simulation getSimulation() {
        return simulation;
    }

    public static void setSimulation(Simulation simulation) {
        ViewPopulationFrame.simulation = simulation;
    }

}
