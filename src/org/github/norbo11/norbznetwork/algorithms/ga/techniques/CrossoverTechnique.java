package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;
import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public abstract class CrossoverTechnique extends GATechnique {
    public CrossoverTechnique(Population population) {
        super(population);
    }
    
    private static Individual getSmallerIndividual(Individual a, Individual b) {
        if (a.getSize() > b.getSize()) return b;
        else return a;
     }
    
    private static Individual getBiggerIndividual(Individual a, Individual b) {
        if (a.getSize() > b.getSize()) return a;
        else return b;
     }
    
    public abstract ArrayList<Individual> crossover(Individual a, Individual b);

    public static class OrderedCrossover extends CrossoverTechnique {
        public OrderedCrossover(Population population) {
            super(population);
        }
        
        public static class DummyNode extends Node {
            
        }

        //TODO Fix this. also needs to account for different gene sizes.
        @Override
        public ArrayList<Individual> crossover(Individual a, Individual b) {
            Population population = getPopulation();
            ArrayList<Individual> children = new ArrayList<Individual>();
            
            Individual bigger = getBiggerIndividual(a, b);
            Individual smaller = getSmallerIndividual(a, b);
            
            //System.out.println("Smaller: " + smaller + " Bigger:  " + bigger);
            
            int crossoverPos1 = RandomUtil.between(0, smaller.getSize());
            //System.out.println("Crossover pos 1: " + crossoverPos1);

            int crossoverPos2 = RandomUtil.between(crossoverPos1, smaller.getSize());
            //System.out.println("Crossover pos 2: " + crossoverPos2);
            
            //System.out.println("Breeding at positions " + crossoverPos1 + ", " + crossoverPos2 + ":\n" + a + "\n" + b);
            
            //Populate with dummy nodes
            Individual child = new Individual(population);
            for (int i = 0; i < bigger.getSize(); i++) {
                child.getGenes().add(new DummyNode());
            }
            
            //Perform initial crossover
            for (int i = crossoverPos1; i <= crossoverPos2; i++) {
                child.getGenes().set(i, smaller.getGenes().get(i));
            }

            //Fill in the rest
            int biggerPos = 0;
            for (Node gene : child.getGenes()) {
                if (gene instanceof DummyNode) {
                    int index = child.getGenes().indexOf(gene);
                    
                    //Get a gene from the smaller individual for as long as its already contained within the child
                    Node geneFromBigger = null;
                    do {
                        if (biggerPos >= bigger.getSize()) break;
                        
                        geneFromBigger = bigger.getGenes().get(biggerPos); 
                        biggerPos++;
                    } while (child.getGenes().contains(geneFromBigger));
                    
                    //When a valid one is found, replace it in the child
                    if (geneFromBigger != null) {
                        child.getGenes().set(index, geneFromBigger);
                    }
                }
            }
            
            //Remove all remaining dummy nodes
            Iterator<Node> iter = child.getGenes().iterator();
            while (iter.hasNext()) {
                if (iter.next() instanceof DummyNode) iter.remove();
            }
            
            children.add(child);
            child.checkValidity();
            //System.out.println("Children : " + children);
            return children;
        }
    }
    
    //Single-point crossover
    public static class SinglePoint extends CrossoverTechnique {
        
        public SinglePoint(Population population) {
            super(population);
        }

        @Override
        public ArrayList<Individual> crossover(Individual a, Individual b) {
            ArrayList<Individual> children = new ArrayList<Individual>();
            
            Individual child1 = new Individual(a.getParentPopulation());
            Individual child2 = new Individual(a.getParentPopulation());

            int crossoverPos = RandomUtil.between(1, getSmallerIndividual(a, b).getSize() - 1);
                        
            //System.out.println("Breeding at position " + crossoverPos + ":\n" + a + "\n" + b);

            Vector<Node> aHalf = new Vector<Node>(a.getGenes().subList(crossoverPos, a.getNumberOfGenes()));
            Vector<Node> bHalf = new Vector<Node>(b.getGenes().subList(crossoverPos, b.getNumberOfGenes()));
            
            child1.getGenes().addAll(a.getGenes().subList(0, crossoverPos));
            child1.getGenes().addAll(bHalf);
            child2.getGenes().addAll(b.getGenes().subList(0, crossoverPos));
            child2.getGenes().addAll(aHalf);
            
            children.add(child1);
            children.add(child2);
            
            //System.out.println("Children : " + children);
        
            return children;
        }
    }
}
