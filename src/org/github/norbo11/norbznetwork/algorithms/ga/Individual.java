package org.github.norbo11.norbznetwork.algorithms.ga;

import java.util.ArrayList;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public class Individual implements Comparable<Individual> {
    private int fitness = 0;
    private ArrayList<Node> genes = new ArrayList<Node>();
    private Population parentPopulation = null;
    
    public Individual(Population parent) {
        this.parentPopulation = parent;
    }
    
    /* Getters and setters */
    
    public int getNumberOfGenes() {
        return getGenes().size();
    }
    
    public int getRandomGeneIndex() {
        return RandomUtil.from0(genes.size());
    }
    
    public int getFitness() {
        return fitness;
    }
    
    public ArrayList<Node> getGenes() {
        return genes;
    }
    
    public Population getParentPopulation() {
        return parentPopulation;
    }
    
    /* Instance methods */
    
    public void recalculateFitness() {
        fitness = parentPopulation.getParentNetwork().getPathWeight(genes);
    }

    /* Override methods */
    
    //This is implemented just so that it may be sorted in a collection.
    @Override
    public int compareTo(Individual another) {
        if (this.getFitness() < another.getFitness()) return -1;
        else if (this.getFitness() > another.getFitness()) return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return genes.toString() + " - Fitness: " + getFitness();
    }

    public int getSize() {
        return genes.size();
    }

    public void checkValidity() {
        ArrayList<Node> checked = new ArrayList<Node>();
        
        for (Node gene : getGenes()) {
            if (checked.contains(gene)) {
                Main.writeText("Found duplicate genes in " + this);
                break;
            } else checked.add(gene);
        }
    }
}
