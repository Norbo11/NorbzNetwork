package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import java.util.Collections;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;
import org.github.norbo11.norbznetwork.algorithms.ga.PriorityIndividual;
import org.github.norbo11.norbznetwork.util.RandomUtil;


public abstract class PriorityMutationTechnique extends MutationTechnique {

    public static class SingleBit extends PriorityMutationTechnique {

        @Override
        public void mutate(Individual individual) {
            mutate((PriorityIndividual) individual);
        }
        
        public void mutate(PriorityIndividual individual) {            
            int from = RandomUtil.from0(individual.getSize());
            int to = RandomUtil.from0(individual.getSize());
            
            individual.getGenes().set(from, to);
        }
    }
    
    public static class Swap extends PriorityMutationTechnique {
        @Override
        public void mutate(Individual individual) {
            mutate((PriorityIndividual) individual);
        }
        
        public void mutate(PriorityIndividual individual) {           
            int index1 = RandomUtil.from0(individual.getGenes().size());
            int index2 = RandomUtil.from0(individual.getGenes().size());
            
            Collections.swap(individual.getGenes(), index1, index2);     
        }
    }
}
