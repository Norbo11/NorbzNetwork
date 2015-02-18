package org.github.norbo11.norbznetwork.algorithms;

import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.CrossoverTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.InitializationTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.MutationTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.SelectionTechnique;
import org.github.norbo11.norbznetwork.frames.GASettingsPanel;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public class LongestPath {    
    public void mainLoop() {
        new Thread(new Runnable() {
            boolean stop = false;
            
            public void run() 
            {
                RandomUtil.seedRandom(GASettingsPanel.getRandomSeed());
                
                //Initialization
                InitializationTechnique init = null;
                if (GASettingsPanel.getInitializationGreedy().isSelected()) init = new InitializationTechnique.Greedy();
                else if (GASettingsPanel.getInitializationRandom().isSelected()) init = new InitializationTechnique.Random();
                else if (GASettingsPanel.getInitializationGreedyRoulette().isSelected()) init = new InitializationTechnique.GreedyRoulette();
                
                int generations = 0;
                Population population = new Population(Main.getCurrentNetwork(), GASettingsPanel.getPopulationSize(), init);
                
                //Selection
                if (GASettingsPanel.getSelectionBestHalf().isSelected()) population.setSelectionTechnique(new SelectionTechnique.BestHalf(population));
                else if (GASettingsPanel.getSelectionRouletteWheel().isSelected()) population.setSelectionTechnique(new SelectionTechnique.RouletteWheel(population));
                
                //Crossover
                if (GASettingsPanel.getCrossoverOrdered().isSelected()) population.setCrossoverTechnique(new CrossoverTechnique.OrderedCrossover(population));
                else if (GASettingsPanel.getCrossoverSinglePoint().isSelected()) population.setCrossoverTechnique(new CrossoverTechnique.SinglePoint(population));
                
                //Mutation
                if (GASettingsPanel.getMutationSingleBit().isSelected()) population.setMutationTechnique(new MutationTechnique.SingleBit(population));
                else if (GASettingsPanel.getMutationSwap().isSelected()) population.setMutationTechnique(new MutationTechnique.Swap(population));
                
                population.recalculateAllFitness();
                GASettingsPanel.getStopButton().addActionListener(e -> stop = true);
                Main.getAverageFitnessData().clear();
                Main.getBestIndividualData().clear();
                
                while (!stop) {
                    generations++;
                    Main.writeText("Generation #" + generations + " Average Fitness: " + population.calculateAverageFitness() + " - Best Individual: " + population.getBestIndividual());
                    population.evolve();
                    population.getParentNetwork().drawPath(population.getBestIndividual().getGenes());
                    population.sketchAverageFitness(generations);
                    population.sketchBestMember(generations);
                    try {
                        Thread.sleep(1010 - GASettingsPanel.getSimulationSpeed().getValue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
