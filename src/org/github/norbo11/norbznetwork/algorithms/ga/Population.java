package org.github.norbo11.norbznetwork.algorithms.ga;

import java.util.ArrayList;
import java.util.Collections;

import org.github.norbo11.norbznetwork.algorithms.ga.techniques.CrossoverTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.InitializationTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.MutationTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.SelectionTechnique;
import org.github.norbo11.norbznetwork.frames.GASettingsPanel;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Graph;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public class Population {
    private ArrayList<Individual> individuals = new ArrayList<Individual>();
    private MutationTechnique mutationTechnique;
    private CrossoverTechnique crossoverTechnique;
    private SelectionTechnique selectionTechnique;
    private Graph parentNetwork;
    
    public Population() {

    }
    
    public Population(Graph graph, int size, InitializationTechnique init) {
        this.parentNetwork = graph;
        
        init.initialize(this, size);
    }

    /* Getters and setters */
    
    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }
    
    public Graph getParentNetwork() {
        return parentNetwork;
    }

    public void setSelectionTechnique(SelectionTechnique selectionTechnique) {
        this.selectionTechnique = selectionTechnique;
    }

    public void setCrossoverTechnique(CrossoverTechnique crossoverTechnique) {
        this.crossoverTechnique = crossoverTechnique;
    }

    public void setMutationTechnique(MutationTechnique mutationTechnique) {
        this.mutationTechnique = mutationTechnique;
    }
    
    /* Instance methods */
    
    public int calculateTotalFitness() {
        int total = 0;
        
        for (Individual individual : individuals) {
            total += individual.getFitness();
        }
        
        return total;
    }
    
    public double calculateAverageFitness() {
        double sum = 0;
        
        for (Individual individual : individuals) {
            sum += individual.getFitness();
        }
        
        return sum / individuals.size();
    }

    public void recalculateAllFitness() {
        for (Individual individual : individuals) {
            individual.recalculateFitness();
        }
    }
    
    public void sort() {
        Collections.sort(individuals, Collections.reverseOrder());
    }
    
    private ArrayList<Individual> getBestIndividuals(int number) {   
        sort();
        return new ArrayList<Individual>(individuals.subList(0, number));
    }
    
    public Individual getBestIndividual() {
        return getBestIndividuals(1).get(0);
    }

    public ArrayList<Individual> getEliteIndividuals() {   
        return getBestIndividuals(GASettingsPanel.getElitismOffset());
    }
    
    public ArrayList<Individual> getNonEliteIndividuals() {
        sort();
        return new ArrayList<Individual>(individuals.subList(GASettingsPanel.getElitismOffset(), individuals.size()));
    }

    @SuppressWarnings("unchecked")
    public void evolve() {
        ArrayList<Individual> newIndividuals = new ArrayList<Individual>();
        
        ArrayList<Individual> elite = getEliteIndividuals();
        newIndividuals.addAll(elite);
        
        while (newIndividuals.size() < getIndividuals().size()) {
            Individual a, b;
            a = selectionTechnique.select(this);
            b = selectionTechnique.select(this);
            
            ArrayList<Individual> children = crossoverTechnique.crossover(a, b);
            
            newIndividuals.addAll(children);
        }

        individuals = newIndividuals;
        
        if (GASettingsPanel.isMutateElite()) {      
            //Replace the weakest members with mutated copies of the elite
            sort();
            int i = individuals.size() - 1;
            for (Individual individual : getEliteIndividuals()) {
                if (RandomUtil.chance((GASettingsPanel.getMutationChance()))) {  
                    Individual copy = new Individual(individual);
                    
                    if (individual instanceof SimpleIndividual) {
                        copy = new SimpleIndividual((SimpleIndividual) individual);
                        ((SimpleIndividual) copy).setGenes(new ArrayList<Node>((ArrayList<Node>) individual.getGenes()));
                    } else if (individual instanceof PriorityIndividual) {
                        copy = new PriorityIndividual((PriorityIndividual) individual);
                        ((PriorityIndividual) copy).setGenes(new ArrayList<Integer>((ArrayList<Integer>) individual.getGenes()));
                    }
                    
                    mutationTechnique.mutate(copy);
                    
                    individuals.set(i, copy);
                    i--;
                }
            }
        }
        
        //Mutate all non-elite individuals
        for (Individual individual : getNonEliteIndividuals()) {
            if (RandomUtil.chance((GASettingsPanel.getMutationChance()))) {  
                mutationTechnique.mutate(individual);
            }
        }
        
        recalculateAllFitness();
    }


    public int getSize() {
        return individuals.size();
    }

    /* Display methods */
    
    
    public void sketchAverageFitness(int generation) {
        Main.getAverageFitnessData().add(generation, calculateAverageFitness());
    }

    public void sketchBestMember(int generation) {
        Main.getBestIndividualData().add(generation, getBestIndividual().getFitness());
    }
    
    public void printPopulation() {
        for (Individual i : individuals) {
            System.out.println(i + ": " + i.getFitness());
        }
    }
}
