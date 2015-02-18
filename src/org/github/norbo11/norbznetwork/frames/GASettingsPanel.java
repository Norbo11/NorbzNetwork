package org.github.norbo11.norbznetwork.frames;

import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.github.norbo11.norbznetwork.util.ConfigUtil;
import org.github.norbo11.norbznetwork.util.GUIUtil;

public class GASettingsPanel  {
    private static JPanel panel;
    
    private static JTextField generalRandomSeed, generalPopulationSize;
    
    private static ButtonGroup initializationGroup;
    private static JRadioButton initializationRandom = new JRadioButton("Random");
    private static JRadioButton initializationGreedy = new JRadioButton("Greedy");
    private static JRadioButton initializationGreedyRoulette = new JRadioButton("Greedy roulette");
    
    private static JTextField selectionElitismOffsetField;
    private static ButtonGroup selectionGroup;
    private static JRadioButton selectionBestHalf = new JRadioButton("Best half");
    private static JRadioButton selectionRouletteWheel = new JRadioButton("Roulette-wheel");
    private static JRadioButton selectionSimulatedAnnealing = new JRadioButton("Simulated annealing");
    
    private static ButtonGroup crossoverGroup;
    private static JRadioButton crossoverOrdered = new JRadioButton("Ordered");
    private static JRadioButton crossoverSinglePoint = new JRadioButton("Single-point");
    private static JRadioButton crossoverTwoPoint = new JRadioButton("Two-point");
    private static JRadioButton crossoverCutAndSplice = new JRadioButton("Cut and splice");
    
    private static ButtonGroup mutationGroup;
    private static JTextField mutationChanceField;
    private static JRadioButton mutationSingleBit = new JRadioButton("Single-bit");
    private static JRadioButton mutationInversion = new JRadioButton("Inversion");
    private static JRadioButton mutationSwap = new JRadioButton("Swap");
    private static JRadioButton mutationInsert = new JRadioButton("Insert");
    
    private static JSlider simulationSpeed;
    private static JButton stopButton;    
    
    static {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        //General
        JPanel generalPanel = new JPanel();
        generalPanel.setBorder(BorderFactory.createTitledBorder("General"));
        generalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        generalRandomSeed = new JTextField(10);
        generalPopulationSize = new JTextField(10);
        
        generalPanel.add(new JLabel("Population size:"));
        generalPanel.add(generalPopulationSize);
        generalPanel.add(new JLabel("Random seed:"));
        generalPanel.add(generalRandomSeed);
        
        panel.add(generalPanel);
        
        //Initialization
        JPanel initializationPanel = new JPanel();
        initializationPanel.setBorder(BorderFactory.createTitledBorder("Initialization"));
        initializationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        initializationGroup = new ButtonGroup();
        initializationGroup.add(initializationRandom);
        initializationGroup.add(initializationGreedy);
        initializationGroup.add(initializationGreedyRoulette);
        
        initializationPanel.add(initializationRandom);
        initializationPanel.add(initializationGreedy);
        initializationPanel.add(initializationGreedyRoulette);
        
        panel.add(initializationPanel);
        
        //Selection
        JPanel selectionPanel = new JPanel();
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Selection"));
        selectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        selectionElitismOffsetField = new JTextField(5);
        selectionGroup = new ButtonGroup();
        selectionGroup.add(selectionBestHalf);
        selectionGroup.add(selectionRouletteWheel);
        selectionGroup.add(selectionSimulatedAnnealing);
        
        selectionPanel.add(new JLabel("Elite size:"));
        selectionPanel.add(selectionElitismOffsetField);
        selectionPanel.add(selectionBestHalf);
        selectionPanel.add(selectionRouletteWheel);
        selectionPanel.add(selectionSimulatedAnnealing);
        
        panel.add(selectionPanel);
        
        //Crossover
        
        JPanel crossoverPanel = new JPanel();
        crossoverPanel.setBorder(BorderFactory.createTitledBorder("Crossover"));
        crossoverPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        crossoverGroup = new ButtonGroup();
        crossoverGroup.add(crossoverSinglePoint);
        crossoverGroup.add(crossoverTwoPoint);
        crossoverGroup.add(crossoverCutAndSplice);
        crossoverGroup.add(crossoverOrdered);

        crossoverPanel.add(crossoverSinglePoint);
        crossoverPanel.add(crossoverTwoPoint);
        crossoverPanel.add(crossoverCutAndSplice);
        crossoverPanel.add(crossoverOrdered);
        
        panel.add(crossoverPanel);
                
        //Mutation
        
        JPanel mutationPanel = new JPanel();
        mutationPanel.setBorder(BorderFactory.createTitledBorder("Mutation"));
        mutationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        mutationChanceField = new JTextField(5);
        mutationGroup = new ButtonGroup();
        mutationGroup.add(mutationSingleBit);
        mutationGroup.add(mutationInversion);
        mutationGroup.add(mutationSwap);
        mutationGroup.add(mutationInsert);
        
        mutationPanel.add(new JLabel("Mutation chance:"));
        mutationPanel.add(mutationChanceField);
        mutationPanel.add(mutationSingleBit);
        mutationPanel.add(mutationInversion);
        mutationPanel.add(mutationSwap);
        mutationPanel.add(mutationInsert);
        
        panel.add(mutationPanel);
        
        /* Simulation controls */
        
        JPanel simulationControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        simulationControls.setBorder(BorderFactory.createTitledBorder("Simulation controls"));
        
        JLabel speedLabel = new JLabel("Simulation speed:");
        simulationSpeed = new JSlider(100, 1000);
        stopButton = new JButton("Stop");

        simulationControls.add(stopButton);
        simulationControls.add(speedLabel);
        simulationControls.add(simulationSpeed);
                
        panel.add(simulationControls);
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static JTextField getSelectionElitismOffsetField() {
        return selectionElitismOffsetField;
    }

    public static JRadioButton getSelectionRouletteWheel() {
        return selectionRouletteWheel;
    }

    public static JRadioButton getSelectionSimulatedAnnealing() {
        return selectionSimulatedAnnealing;
    }

    public static JRadioButton getCrossoverSinglePoint() {
        return crossoverSinglePoint;
    }

    public static JRadioButton getCrossoverTwoPoint() {
        return crossoverTwoPoint;
    }

    public static JRadioButton getCrossoverCutAndSplice() {
        return crossoverCutAndSplice;
    }

    public static JTextField getMutationChanceField() {
        return mutationChanceField;
    }

    public static JRadioButton getMutationSingleBit() {
        return mutationSingleBit;
    }

    public static JRadioButton getMutationInversion() {
        return mutationInversion;
    }

    public static JRadioButton getMutationSwap() {
        return mutationSwap;
    }

    public static JRadioButton getMutationInsert() {
        return mutationInsert;
    }

    public static JRadioButton getCrossoverOrdered() {
        return crossoverOrdered;
    }

    public static JRadioButton getSelectionBestHalf() {
        return selectionBestHalf;
    }

    public static ButtonGroup getSelectionGroup() {
        return selectionGroup;
    }

    public static ButtonGroup getCrossoverGroup() {
        return crossoverGroup;
    }

    public static ButtonGroup getMutationGroup() {
        return mutationGroup;
    }
    
    public static JSlider getSimulationSpeed() {
        return simulationSpeed;
    }

    public static void restoreSettings() {
        simulationSpeed.setValue(Integer.valueOf(ConfigUtil.get("simulationSpeed")));
        selectionElitismOffsetField.setText(ConfigUtil.get("elitismOffset"));
        mutationChanceField.setText(ConfigUtil.get("mutationChance"));
        generalRandomSeed.setText(ConfigUtil.get("randomSeed"));
        generalPopulationSize.setText(ConfigUtil.get("populationSize"));
        
        GUIUtil.setRadioSelected(selectionGroup, ConfigUtil.get("selectionTechnique"));
        GUIUtil.setRadioSelected(crossoverGroup, ConfigUtil.get("crossoverTechnique"));
        GUIUtil.setRadioSelected(mutationGroup, ConfigUtil.get("mutationTechnique"));
        GUIUtil.setRadioSelected(initializationGroup, ConfigUtil.get("initializationTechnique"));
    }
    
    public static ButtonGroup getInitializationGroup() {
        return initializationGroup;
    }

    public static JRadioButton getInitializationGreedy() {
        return initializationGreedy;
    }

    public static JRadioButton getInitializationGreedyRoulette() {
        return initializationGreedyRoulette;
    }

    public static JButton getStopButton() {
        return stopButton;
    }
    
    public static int getElitismOffset() {
        return Integer.valueOf(selectionElitismOffsetField.getText());
    }
    
    public static double getMutationChance() {
        return Double.valueOf(mutationChanceField.getText());
    }

    public static JTextField getGeneralRandomSeed() {
        return generalRandomSeed;
    }

    public static JTextField getGeneralPopulationSize() {
        return generalPopulationSize;
    }

    public static JRadioButton getInitializationRandom() {
        return initializationRandom;
    }

    public static int getPopulationSize() {
        return Integer.valueOf(generalPopulationSize.getText());
    }

    public static long getRandomSeed() {
        return generalRandomSeed.getText().equals("") ? LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) : Long.valueOf(generalRandomSeed.getText());
    }
}
