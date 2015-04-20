package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import java.util.ArrayList;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;
import org.github.norbo11.norbznetwork.algorithms.ga.PriorityIndividual;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public abstract class PriorityCrossoverTechnique extends CrossoverTechnique {

    //Single-point crossover
    public static class SinglePoint extends PriorityCrossoverTechnique {

        @Override
        public ArrayList<Individual> crossover(Individual a, Individual b) {
            return crossover((PriorityIndividual) a, (PriorityIndividual) b);
        }
        
        public ArrayList<Individual> crossover(PriorityIndividual a, PriorityIndividual b) {
            ArrayList<Individual> children = new ArrayList<Individual>();
            
            PriorityIndividual child1 = new PriorityIndividual(a.getParentPopulation());
            PriorityIndividual child2 = new PriorityIndividual(a.getParentPopulation());

            int crossoverPos = RandomUtil.between(1, getSmallerIndividual(a, b).getSize() - 1);
                        
            ArrayList<Integer> aHalf = new ArrayList<Integer>(a.getGenes().subList(crossoverPos, a.getNumberOfGenes()));
            ArrayList<Integer> bHalf = new ArrayList<Integer>(b.getGenes().subList(crossoverPos, b.getNumberOfGenes()));
            
            child1.getGenes().addAll(a.getGenes().subList(0, crossoverPos));
            child1.getGenes().addAll(bHalf);
            child2.getGenes().addAll(b.getGenes().subList(0, crossoverPos));
            child2.getGenes().addAll(aHalf);
            
            children.add(child1);
            children.add(child2);
                    
            return children;
        }
    }
    
    //Double-point crossover
    public static class TwoPoint extends PriorityCrossoverTechnique {

        @Override
        public ArrayList<Individual> crossover(Individual a, Individual b) {
            return crossover((PriorityIndividual) a, (PriorityIndividual) b);
        }
        
        public ArrayList<Individual> crossover(PriorityIndividual a, PriorityIndividual b) {
            ArrayList<Individual> children = new ArrayList<Individual>();
            
            PriorityIndividual child1 = new PriorityIndividual(a.getParentPopulation());
            PriorityIndividual child2 = new PriorityIndividual(a.getParentPopulation());

            int pos1 = RandomUtil.from0(a.getSize());
            int pos2 = RandomUtil.from0(a.getSize());
                
            //Make sure that pos2 is always greater than pos1
            if (pos1 > pos2) {
                int temp = 0;
                
                temp = pos2;
                pos2 = pos1;
                pos1 = temp;
            }
            
            ArrayList<Integer> aSection = new ArrayList<Integer>(a.getGenes().subList(pos1, pos2 + 1));
            ArrayList<Integer> bSection = new ArrayList<Integer>(b.getGenes().subList(pos1, pos2 + 1));
            
            child1.getGenes().addAll(a.getGenes().subList(0, pos1));
            child1.getGenes().addAll(bSection);
            child1.getGenes().addAll(a.getGenes().subList(pos2 + 1, a.getSize()));
            
            child2.getGenes().addAll(b.getGenes().subList(0, pos1));
            child2.getGenes().addAll(aSection);
            child2.getGenes().addAll(b.getGenes().subList(pos2 + 1, b.getSize()));
            
            children.add(child1);
            children.add(child2);
        
            return children;
        }
    }
}
