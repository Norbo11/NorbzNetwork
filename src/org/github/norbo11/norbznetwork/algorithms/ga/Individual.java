package org.github.norbo11.norbznetwork.algorithms.ga;

import java.util.ArrayList;

import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public class Individual implements Comparable<Individual> {
    private int fitness = 0;
    private Population parentPopulation = null;
    
    public Individual(Population parent) {
        this.parentPopulation = parent;
    }
    
    public Individual(Individual individual) {
        this.fitness = individual.fitness;
        this.parentPopulation = individual.parentPopulation;
    }

    public int getNumberOfGenes() {
        return getGenes().size();
    }
    
    public int getRandomGeneIndex() {
        return RandomUtil.from0(getGenes().size());
    }
        
    public Population getParentPopulation() {
        return parentPopulation;
    }
    
    public int getFitness() {
        return fitness;
    }
    
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getSize() {
        return getGenes().size();
    }
    
    public ArrayList<?> getGenes() {
        return null;
    }
    
    public ArrayList<Node> getPath() {
        return null;
    }
    
    public void recalculateFitness() {
        
    }

    //This is implemented just so that it may be sorted in a collection.
    @Override
    public int compareTo(Individual another) {
        if (this.getFitness() < another.getFitness()) return -1;
        else if (this.getFitness() > another.getFitness()) return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return getGenes().toString() + " - Fitness: " + getFitness();
    }
}
