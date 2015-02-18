package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import java.util.Collections;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;
import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.frames.GASettingsPanel;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.RandomUtil;


public abstract class MutationTechnique extends GATechnique {
    public MutationTechnique(Population population) {
        super(population);
    }

    public abstract void mutate();
    
    public static class SingleBit extends MutationTechnique {

        public SingleBit(Population population) {
            super(population);
        }

        @Override
        public void mutate() {
            Population population = getPopulation();
            
            for (Individual individual : population.getNonEliteIndividuals()) {
                for (Node gene : individual.getGenes()) {
                    if (RandomUtil.chance((GASettingsPanel.getMutationChance()))) {                               
                        Node newGene = population.getParentNetwork().getRandomNode(individual.getGenes());
                        
                        if (newGene != null) {
                            individual.getGenes().set(individual.getGenes().indexOf(gene), newGene);
                        }                        
                    }
                }
            }
        }
    }
    
    public static class Swap extends MutationTechnique {

        public Swap(Population population) {
            super(population);
        }

        @Override
        public void mutate() {
            Population population = getPopulation();
            
            for (Individual individual : population.getNonEliteIndividuals()) {
                if (RandomUtil.chance((GASettingsPanel.getMutationChance()))) {     
                    int index1 = RandomUtil.from0(individual.getGenes().size());
                    int index2 = RandomUtil.from0(individual.getGenes().size());
                    
                    Collections.swap(individual.getGenes(), index1, index2);                  
                }
            }
        }
    }
}
