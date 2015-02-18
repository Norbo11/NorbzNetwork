package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;
import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public abstract class SelectionTechnique extends GATechnique {
    public SelectionTechnique(Population population) {
        super(population);
    }

    public abstract Individual select();
    
    public static class BestHalf extends SelectionTechnique {

        public BestHalf(Population population) {
            super(population);
        }

        @Override
        public Individual select() {
            /*Population population = getPopulation();
            ArrayList<Individual> individuals = population.getIndividuals();
            
            //Elitism        
            //Individual elite = getBestIndividual();
            //individuals.remove(elite);
            
            int popSize = individuals.size();
            if (popSize % 2 != 0) popSize += 1; //If odd number of individuals, make it even
            
            //System.out.println("Population:" + individuals.size());
            //System.out.println("Popsize: " + popSize);
            
            int startIndex = popSize / 2;
            if (startIndex % 2 != 0) startIndex += 1; //If there's an odd pair, ensure that there isn't
            
            //System.out.println("Start index: " + startIndex);
            
            ArrayList<Individual> bestHalf = new ArrayList<Individual>(individuals.subList(individuals.size() - startIndex, individuals.size()));
            ArrayList<Individual> bestHalfCopy = new ArrayList<Individual>(bestHalf);
            individuals.clear();
            
            //System.out.println("Best half: " + bestHalf.size());
            //System.out.println("Looping " + (startIndex / 2) + " times");
            
            for (int i = 0; i < startIndex / 2; i++) {
                int crossoverPos = RandomUtil.randomize(a.getNumberOfGenes());
                            
                //System.out.println("Breeding at position " + crossoverPos + ":\n" + a + "\n" + b);
    
                ArrayList<Node> aHalf = new ArrayList<Node>(a.getGenes().subList(crossoverPos, b.getNumberOfGenes()));
                ArrayList<Node> bHalf = new ArrayList<Node>(b.getGenes().subList(crossoverPos, b.getNumberOfGenes()));
                
                //System.out.println("A half: " + aHalf);
                //System.out.println("B half: " + bHalf);
                
                int j = crossoverPos;
                for (Node node : bHalf) {
                    a.getGenes().set(j, node);
                    j++;
                }
                
                j = crossoverPos;
                for (Node node : aHalf) {
                    b.getGenes().set(j, node);
                    j++;
                }
                                
                //System.out.println("Child 1: " + a);
                //System.out.println("Child 2: " + b);
                
                individuals.add(a);
                individuals.add(b);
            }
            individuals.addAll(bestHalfCopy);
            //individuals.add(elite);
            
            population.sort();*/
            return null;
        }
    }
    
    public static class RouletteWheel extends SelectionTechnique {

        public RouletteWheel(Population population) {
            super(population);
        }

        @Override
        public Individual select() {            
            int random = RandomUtil.from0(getPopulation().calculateTotalFitness());
            int total = 0;
                        
            Individual selected = null;
            for (Individual individual : getPopulation().getIndividuals()) {
                total += individual.getFitness();
                                
                if (total >= random) { 
                    selected = individual;
                    break;
                }
            }
            
            //System.out.println("Selected " + selected + " with fitness " + selected.getFitness());
            return selected;
        }
    }
}
