package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import java.util.ArrayList;
import java.util.Iterator;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;
import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.algorithms.ga.SimpleIndividual;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public abstract class SimpleCrossoverTechnique extends CrossoverTechnique {    
    
    public static class OrderedCrossover extends SimpleCrossoverTechnique {
        public static class DummyNode extends Node {
            
        }

        @Override
        public ArrayList<Individual> crossover(Individual a, Individual b) {
            return crossover((SimpleIndividual) a, (SimpleIndividual) b);
        }
        
        public ArrayList<Individual> crossover(SimpleIndividual a, SimpleIndividual b) {
            Population population = a.getParentPopulation();
            ArrayList<Individual> children = new ArrayList<Individual>();
            
            SimpleIndividual bigger = (SimpleIndividual) getBiggerIndividual(a, b);
            SimpleIndividual smaller = (SimpleIndividual) getSmallerIndividual(a, b);
                        
            int crossoverPos1 = RandomUtil.from0(smaller.getSize());
            int crossoverPos2 = RandomUtil.from0(smaller.getSize());

            //Make sure that pos2 is always greater than pos1
            if (crossoverPos1 > crossoverPos2) {
                int temp = 0;
                
                temp = crossoverPos2;
                crossoverPos2 = crossoverPos1;
                crossoverPos1 = temp;
            }
                        
            //Populate with dummy nodes
            SimpleIndividual child = new SimpleIndividual(population);
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
            
            child.checkDuplicates();
            
            if (child.isValidPath()) {
                children.add(child);
            } 
                        
            return children;
        }
    }
    
    
}
