package org.github.norbo11.norbznetwork.algorithms.ga;

import java.util.ArrayList;
import java.util.Collections;

import org.github.norbo11.norbznetwork.algorithms.ga.techniques.CrossoverTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.InitializationTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.MutationTechnique;
import org.github.norbo11.norbznetwork.algorithms.ga.techniques.SelectionTechnique;
import org.github.norbo11.norbznetwork.frames.GASettingsPanel;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Network;

public class Population {
    private ArrayList<Individual> individuals = new ArrayList<Individual>();
    private MutationTechnique mutationTechnique;
    private CrossoverTechnique crossoverTechnique;
    private SelectionTechnique selectionTechnique;
    private Network parentNetwork;
    
    public Population(Network network, int size, InitializationTechnique init) {
        this.parentNetwork = network;
        
        init.initialize(this, size);
    }

    public Population(Network network, int size) {
        this.parentNetwork = network;        
    }
    
    /* Getters and setters */
    
    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }
    
    public Network getParentNetwork() {
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
    
    private void sort() {
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

    public void evolve() {
        ArrayList<Individual> newIndividuals = new ArrayList<Individual>();
        
        ArrayList<Individual> elite = getEliteIndividuals();
        newIndividuals.addAll(elite);
        
        while (newIndividuals.size() < getIndividuals().size()) {
            Individual a, b;
            a = selectionTechnique.select();
            b = selectionTechnique.select();
            
            ArrayList<Individual> children = crossoverTechnique.crossover(a, b);
            
            newIndividuals.addAll(children);
        }

        individuals = newIndividuals;
        recalculateAllFitness();
        //printPopulation();
        mutationTechnique.mutate();
        recalculateAllFitness();
        checkValidity();
    }
    
    private void checkValidity() {
        for (Individual individual : individuals) {
            individual.checkValidity();
        }
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
