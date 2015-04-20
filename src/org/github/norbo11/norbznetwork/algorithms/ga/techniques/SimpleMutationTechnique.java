package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import java.util.Collections;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;
import org.github.norbo11.norbznetwork.algorithms.ga.SimpleIndividual;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.RandomUtil;


public abstract class SimpleMutationTechnique extends MutationTechnique {

    public static class SingleBit extends SimpleMutationTechnique {

        @Override
        public void mutate(Individual individual) {
            mutate((SimpleIndividual) individual);
        }
        
        public void mutate(SimpleIndividual individual) {            
            for (Node gene : individual.getGenes()) {
                int index = individual.getGenes().indexOf(gene);
                Node newGene = individual.getParentPopulation().getParentNetwork().getRandomNode(individual.getGenes());
                
                if (newGene != null) {
                    individual.getGenes().set(index, newGene);
                    
                    //If invalid reverse the mutation
                    if (!individual.isValidPath()) {
                        individual.getGenes().set(index, gene);
                    }
                }                        
            }
        }
    }
    
    public static class Swap extends SimpleMutationTechnique {
        @Override
        public void mutate(Individual individual) {
            mutate((SimpleIndividual) individual);
        }
        
        public void mutate(SimpleIndividual individual) {           
            int index1 = RandomUtil.from0(individual.getGenes().size());
            int index2 = RandomUtil.from0(individual.getGenes().size());
            
            Collections.swap(individual.getGenes(), index1, index2);     
            
            //If not a valid path, swap back
            if (!individual.isValidPath()) {
                Collections.swap(individual.getGenes(), index1, index2);  
                mutate(individual);
            }
        }
    }
    
    public static class Insert extends SimpleMutationTechnique {
        @Override
        public void mutate(Individual individual) {
            mutate((SimpleIndividual) individual);
        }
        
        public void mutate(SimpleIndividual individual) {     
            Node newGene = individual.getParentPopulation().getParentNetwork().getRandomNode(individual.getGenes());
            
            for (int i = 0; i <= individual.getSize(); i++) {
                individual.getGenes().add(i, newGene);
                
                if (!individual.isValidPath()) {
                    return;
                } else {
                    individual.getGenes().remove(i);
                }
            }
        }
    }
}
