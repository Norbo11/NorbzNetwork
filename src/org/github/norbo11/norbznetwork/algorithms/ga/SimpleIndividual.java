package org.github.norbo11.norbznetwork.algorithms.ga;

import java.util.ArrayList;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Node;

public class SimpleIndividual extends Individual {
    
    public SimpleIndividual(SimpleIndividual individual) {
        super(individual);
        setGenes(individual.getGenes());
    }

    public SimpleIndividual(Population population) {
        super(population);
    }

    private ArrayList<Node> genes = new ArrayList<Node>();
    
    @Override
    public void recalculateFitness() {
        setFitness(getParentPopulation().getParentNetwork().getPathWeight(genes));
    }

    public void checkDuplicates() {
        ArrayList<Node> checked = new ArrayList<Node>();
        
        for (Node gene : getGenes()) {
            if (checked.contains(gene)) {
                Main.writeText("Found duplicate genes in " + this);
                break;
            } else checked.add(gene);
        }
    }

    public boolean isValidPath() {        
        for (int i = 0; i <= genes.size() - 2; i++)
        {
            Node node1 = genes.get(i);
            Node node2 = genes.get(i + 1);
            Arc arc = getParentPopulation().getParentNetwork().getArcByNodes(node1, node2);
            
            if (arc == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<Node> getGenes() {
        return genes;
    }
    
    @Override
    public ArrayList<Node> getPath() {
        return getGenes();
    }

    public void setGenes(ArrayList<Node> genes) {
        this.genes = genes;
    }
}
