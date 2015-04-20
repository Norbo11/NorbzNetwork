package org.github.norbo11.norbznetwork.frames;

import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
    
    private static ButtonGroup encodingGroup;
    private static JRadioButton encodingSimple = new JRadioButton("Simple");
    private static JRadioButton encodingPriority = new JRadioButton("Priority");
    
    private static ButtonGroup initializationGroup;
    private static JRadioButton initializationRandom = new JRadioButton("Random");
    private static JRadioButton initializationGreedy = new JRadioButton("Greedy");
    private static JRadioButton initializationGreedyRoulette = new JRadioButton("Greedy roulette");
    
    private static JTextField selectionElitismOffsetField, selectionTournamentSize;
    private static ButtonGroup selectionGroup;
    private static JRadioButton selectionRouletteWheel = new JRadioButton("Roulette-wheel");
    private static JRadioButton selectionTournament = new JRadioButton("Tournament");
    
    private static ButtonGroup crossoverGroup;
    private static JRadioButton crossoverOrdered = new JRadioButton("Ordered");
    private static JRadioButton crossoverSinglePoint = new JRadioButton("Single-point");
    private static JRadioButton crossoverTwoPoint = new JRadioButton("Two-point");
    
    private static ButtonGroup mutationGroup;
    private static JTextField mutationChanceField;
    private static JRadioButton mutationSingleBit = new JRadioButton("Single-bit");
    private static JRadioButton mutationSwap = new JRadioButton("Swap");
    private static JRadioButton mutationInsert = new JRadioButton("Insert");
    private static JCheckBox mutationMutateElite = new JCheckBox("Mutate copies of elite");
    
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
        
        //Encoding
        JPanel encodingPanel = new JPanel();
        encodingPanel.setBorder(BorderFactory.createTitledBorder("Encoding"));
        encodingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        encodingGroup = new ButtonGroup();
        encodingGroup.add(encodingSimple);
        encodingGroup.add(encodingPriority);
        
        encodingPanel.add(encodingSimple);
        encodingPanel.add(encodingPriority);
        
        panel.add(encodingPanel);        
        
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
        selectionTournamentSize = new JTextField(5);
        
        selectionGroup = new ButtonGroup();
        selectionGroup.add(selectionRouletteWheel);
        selectionGroup.add(selectionTournament);
        
        selectionPanel.add(new JLabel("Elite size:"));
        selectionPanel.add(selectionElitismOffsetField);
        selectionPanel.add(selectionRouletteWheel);
        selectionPanel.add(selectionTournament);
        selectionPanel.add(selectionTournamentSize);
        
        panel.add(selectionPanel);
        
        //Crossover
        
        JPanel crossoverPanel = new JPanel();
        crossoverPanel.setBorder(BorderFactory.createTitledBorder("Crossover"));
        crossoverPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        crossoverGroup = new ButtonGroup();
        crossoverGroup.add(crossoverSinglePoint);
        crossoverGroup.add(crossoverTwoPoint);
        crossoverGroup.add(crossoverOrdered);

        crossoverPanel.add(crossoverSinglePoint);
        crossoverPanel.add(crossoverTwoPoint);
        crossoverPanel.add(crossoverOrdered);
        
        panel.add(crossoverPanel);
                
        //Mutation
        
        JPanel mutationPanel = new JPanel();
        mutationPanel.setBorder(BorderFactory.createTitledBorder("Mutation"));
        mutationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        mutationChanceField = new JTextField(5);
        mutationGroup = new ButtonGroup();
        mutationGroup.add(mutationSingleBit);
        mutationGroup.add(mutationSwap);
        mutationGroup.add(mutationInsert);
        
        mutationPanel.add(new JLabel("Mutation chance:"));
        mutationPanel.add(mutationChanceField);
        mutationPanel.add(mutationSingleBit);
        mutationPanel.add(mutationSwap);
        mutationPanel.add(mutationInsert);
        mutationPanel.add(mutationMutateElite);
        
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

    public static JTextField getSelectionTournamentSize() {
        return selectionTournamentSize;
    }

    public static JRadioButton getSelectionTournament() {
        return selectionTournament;
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

    public static JRadioButton getCrossoverSinglePoint() {
        return crossoverSinglePoint;
    }

    public static JRadioButton getCrossoverTwoPoint() {
        return crossoverTwoPoint;
    }

    public static JTextField getMutationChanceField() {
        return mutationChanceField;
    }

    public static JRadioButton getMutationSingleBit() {
        return mutationSingleBit;
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
        generalRandomSeed.setText(ConfigUtil.get("randomSeed"));
        generalPopulationSize.setText(ConfigUtil.get("populationSize"));
        
        selectionElitismOffsetField.setText(ConfigUtil.get("elitismOffset"));
        selectionTournamentSize.setText(ConfigUtil.get("tournamentSize"));
        
        mutationChanceField.setText(ConfigUtil.get("mutationChance"));
        mutationMutateElite.setSelected(Boolean.valueOf(ConfigUtil.get("mutateElite")));

        simulationSpeed.setValue(Integer.valueOf(ConfigUtil.get("simulationSpeed")));
        
        GUIUtil.setRadioSelected(initializationGroup, ConfigUtil.get("initializationTechnique"));
        GUIUtil.setRadioSelected(encodingGroup, ConfigUtil.get("encodingTechnique"));
        GUIUtil.setRadioSelected(selectionGroup, ConfigUtil.get("selectionTechnique"));
        GUIUtil.setRadioSelected(crossoverGroup, ConfigUtil.get("crossoverTechnique"));
        GUIUtil.setRadioSelected(mutationGroup, ConfigUtil.get("mutationTechnique"));
    }
    
    public static ButtonGroup getInitializationGroup() {
        return initializationGroup;
    }
    
    public static JCheckBox getMutationMutateElite() {
        return mutationMutateElite;
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
        return generalRandomSeed.getText().equals("") ? new Date().getTime() : Long.valueOf(generalRandomSeed.getText());
    }
    
    public static boolean isMutateElite() {
        return mutationMutateElite.isSelected();
    }

    public static int getTournamentSize() {
        return Integer.valueOf(selectionTournamentSize.getText());
    }

    public static ButtonGroup getEncodingGroup() {
        return encodingGroup;
    }

    public static JRadioButton getEncodingSimple() {
        return encodingSimple;
    }

    public static JRadioButton getEncodingPriority() {
        return encodingPriority;
    }
}
