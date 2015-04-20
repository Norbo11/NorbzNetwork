package org.github.norbo11.norbznetwork.algorithms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;
import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.PriorityCrossoverTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.PriorityInitializationTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.PriorityMutationTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.SelectionTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.SimpleCrossoverTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.SimpleInitializationTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.SimpleMutationTechnique;
import org.github.norbo11.norbznetwork.frames.GASettingsPanel;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.frames.ViewPopulationFrame;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public class LongestPath extends Simulation {    
    @Override
    public void perform() {
        RandomUtil.seedRandom(GASettingsPanel.getRandomSeed());
        
        Population population = null;
        
        if (GASettingsPanel.getEncodingSimple().isSelected()) {
            population = setUpForSimple();
        } else if (GASettingsPanel.getEncodingPriority().isSelected()) {
            population = setUpForPriority();
        }
        
        startLoop(population);
    }
    
    public void startLoop(Population population) {
        population.recalculateAllFitness();
        GASettingsPanel.getStopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setRunning(false);
            }
        });
        
        Main.getAverageFitnessData().clear();
        Main.getBestIndividualData().clear();
        
        ViewPopulationFrame.setSimulation(this);
        Individual previousBest = population.getBestIndividual();
        Main.writeText("Generation #" + getGenerations() + " Average Fitness: " + population.calculateAverageFitness() + " - Best Individual: " + population.getBestIndividual());
        
        while (isRunning()) {
            setGenerations(getGenerations() + 1);
            ViewPopulationFrame.update();
            
            Individual currentBest = population.getBestIndividual();
            
            if (currentBest.getFitness() > previousBest.getFitness()) {
                previousBest = currentBest;
                Main.writeText("Generation #" + getGenerations() + " Average Fitness: " + population.calculateAverageFitness() + " - Best Individual: " + population.getBestIndividual());
            }

            population.getParentNetwork().drawPath(currentBest.getPath());
            population.sketchBestMember(getGenerations());
            population.sketchAverageFitness(getGenerations());
            population.evolve();
            
            try {
                Thread.sleep(1010 - GASettingsPanel.getSimulationSpeed().getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Population setUpForPriority() {
        //Initialization
        PriorityInitializationTechnique init = null;
        if (GASettingsPanel.getInitializationGreedy().isSelected()) init = new PriorityInitializationTechnique.Greedy();
        else if (GASettingsPanel.getInitializationRandom().isSelected()) init = new PriorityInitializationTechnique.Random();
        else if (GASettingsPanel.getInitializationGreedyRoulette().isSelected()) init = new PriorityInitializationTechnique.GreedyRoulette();
        
        setGenerations(0);
        Population population = new Population(Main.getCurrentNetwork(), GASettingsPanel.getPopulationSize(), init);
        setPopulation(population);
        
        //Selection
        if (GASettingsPanel.getSelectionRouletteWheel().isSelected()) population.setSelectionTechnique(new SelectionTechnique.RouletteWheel());
        else if (GASettingsPanel.getSelectionTournament().isSelected()) population.setSelectionTechnique(new SelectionTechnique.Tournament());
        
        //Crossover
        if (GASettingsPanel.getCrossoverSinglePoint().isSelected()) population.setCrossoverTechnique(new PriorityCrossoverTechnique.SinglePoint());
        else if (GASettingsPanel.getCrossoverTwoPoint().isSelected()) population.setCrossoverTechnique(new PriorityCrossoverTechnique.TwoPoint());
        
        //Mutation
        if (GASettingsPanel.getMutationSingleBit().isSelected()) population.setMutationTechnique(new PriorityMutationTechnique.SingleBit());
        else if (GASettingsPanel.getMutationSwap().isSelected()) population.setMutationTechnique(new PriorityMutationTechnique.Swap());
        
        return population;
    }
    
    public Population setUpForSimple() {
        //Initialization
        SimpleInitializationTechnique init = null;
        if (GASettingsPanel.getInitializationGreedy().isSelected()) init = new SimpleInitializationTechnique.Greedy();
        else if (GASettingsPanel.getInitializationRandom().isSelected()) init = new SimpleInitializationTechnique.Random();
        else if (GASettingsPanel.getInitializationGreedyRoulette().isSelected()) init = new SimpleInitializationTechnique.GreedyRoulette();
        
        setGenerations(0);
        Population population = new Population(Main.getCurrentNetwork(), GASettingsPanel.getPopulationSize(), init);
        setPopulation(population);
        
        //Selection
        if (GASettingsPanel.getSelectionRouletteWheel().isSelected()) population.setSelectionTechnique(new SelectionTechnique.RouletteWheel());
        else if (GASettingsPanel.getSelectionTournament().isSelected()) population.setSelectionTechnique(new SelectionTechnique.Tournament());
        
        //Crossover
        if (GASettingsPanel.getCrossoverOrdered().isSelected()) population.setCrossoverTechnique(new SimpleCrossoverTechnique.OrderedCrossover());
        
        //Mutation
        if (GASettingsPanel.getMutationSingleBit().isSelected()) population.setMutationTechnique(new SimpleMutationTechnique.SingleBit());
        else if (GASettingsPanel.getMutationSwap().isSelected()) population.setMutationTechnique(new SimpleMutationTechnique.Swap());
        else if (GASettingsPanel.getMutationInsert().isSelected()) population.setMutationTechnique(new SimpleMutationTechnique.Insert());
        
        return population;
    }
}
