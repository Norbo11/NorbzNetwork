package org.github.norbo11.norbznetwork.algorithms.ga;

import java.util.ArrayList;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Node;

public class PriorityIndividual extends Individual {
    
    private ArrayList<Integer> genes = new ArrayList<Integer>();

    public PriorityIndividual(Population population) {
        super(population);
    }
    
    public PriorityIndividual(PriorityIndividual individual) {
        super(individual);
        genes = new ArrayList<Integer>(individual.getGenes());
    }

    @Override
    public void recalculateFitness() {
        setFitness(getParentPopulation().getParentNetwork().getPathWeightFromPriorities(genes));
    }

    @Override
    public ArrayList<Integer> getGenes() {
        return genes;
    }

    @Override
    public ArrayList<Node> getPath() {
        return Main.getCurrentNetwork().createTraversalPathFromPriorities(getGenes());
    }
    
    public void setGenes(ArrayList<Integer> genes) {
        this.genes = genes;
    }
}

