package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import java.util.ArrayList;

import org.github.norbo11.norbznetwork.algorithms.ga.SimpleIndividual;
import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public abstract class SimpleInitializationTechnique extends InitializationTechnique {

    public static class Random extends SimpleInitializationTechnique {
        @Override
        public void initialize(Population population, int popSize) {
            
            for (int i = 0; i < popSize; i++) {
                SimpleIndividual simpleIndividual = new SimpleIndividual(population);
                ArrayList<Node> nodes = simpleIndividual.getParentPopulation().getParentNetwork().getNodes();
                ArrayList<Node> genes = simpleIndividual.getGenes();
                
                Node firstGene = nodes.get(RandomUtil.from0(nodes.size()));
                genes.add(firstGene);
                
                Node previousGene = firstGene;
                ArrayList<Arc> neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(genes);
                do {
                    Arc random = neighbours.get(RandomUtil.from0(neighbours.size()));
                    
                    Node connectingNode = random.getNodeConnecting(previousGene);
                    genes.add(connectingNode);
                    previousGene = connectingNode;
                    
                    neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(genes);
                } while (neighbours.size() > 0);
                
                //System.out.println("generated " + individual);
                
                population.getIndividuals().add(simpleIndividual);
            }
        }
    }
    
    public static class Greedy extends SimpleInitializationTechnique {
        @Override
        public void initialize(Population population, int popSize) {
            
            for (int i = 0; i < popSize; i++) {
                SimpleIndividual simpleIndividual = new SimpleIndividual(population);
                ArrayList<Node> nodes = simpleIndividual.getParentPopulation().getParentNetwork().getNodes();
                ArrayList<Node> genes = simpleIndividual.getGenes();
                
                Node firstGene = nodes.get(RandomUtil.from0(nodes.size()));
                genes.add(firstGene);
                
                Node previousGene = firstGene;
                ArrayList<Arc> neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(genes);
                do {
                    Arc winner = null;
                    
                    for (Arc neighbour : neighbours) {
                        if (winner == null) winner = neighbour;
                        
                        if (neighbour.getWeight() > winner.getWeight()) winner = neighbour;
                    }
                    
                    Node connectingNode = winner.getNodeConnecting(previousGene);
                    genes.add(connectingNode);
                    previousGene = connectingNode;
                    
                    neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(genes);
                } while (neighbours.size() > 0);
                
                //System.out.println("generated " + individual);
                
                population.getIndividuals().add(simpleIndividual);
            }
        }
    }
    
    public static class GreedyRoulette extends SimpleInitializationTechnique {
        public static int sumArcWeights(ArrayList<Arc> arcs) {
            int total = 0;
            
            for (Arc arc : arcs) {
                total += arc.getWeight();
            }
                
            return total;
        }
        
        @Override
        public void initialize(Population population, int popSize) {
            
            for (int i = 0; i < popSize; i++) {
                SimpleIndividual simpleIndividual = new SimpleIndividual(population);
                ArrayList<Node> nodes = simpleIndividual.getParentPopulation().getParentNetwork().getNodes();
                ArrayList<Node> genes = simpleIndividual.getGenes();
                
                Node firstGene = nodes.get(RandomUtil.from0(nodes.size()));
                genes.add(firstGene);
                
                Node previousGene = firstGene;
                ArrayList<Arc> neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(genes);
                do {
                    Arc selected = null;
                    
                    int random = RandomUtil.from0(sumArcWeights(neighbours));
                    int total = 0;
                                
                    for (Arc neighbour : neighbours) {
                        total += neighbour.getWeight();
                                        
                        if (total >= random) { 
                            selected = neighbour;
                            break;
                        }
                    }
                    
                    Node connectingNode = selected.getNodeConnecting(previousGene);
                    genes.add(connectingNode);
                    previousGene = connectingNode;
                    
                    neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(genes);

                } while (neighbours.size() > 0);
                
                //System.out.println("generated " + individual);
                
                population.getIndividuals().add(simpleIndividual);
            }
        }
    }
}
