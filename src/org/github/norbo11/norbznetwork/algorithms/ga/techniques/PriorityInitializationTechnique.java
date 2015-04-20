package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import java.util.ArrayList;
import java.util.Collections;

import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.algorithms.ga.PriorityIndividual;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Graph;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public abstract class PriorityInitializationTechnique extends InitializationTechnique {
        
    public static class Random extends PriorityInitializationTechnique {
        @Override
        public void initialize(Population population, int popSize) {
            
            for (int j = 0; j < popSize; j++) {
                ArrayList<Integer> randomPriorities = new ArrayList<Integer>();
                
                for (int i = 1; i <= population.getParentNetwork().getNodes().size(); i++) {
                    randomPriorities.add(i);
                }
                
                Collections.shuffle(randomPriorities);
                
                PriorityIndividual individual = new PriorityIndividual(population);
                individual.setGenes(randomPriorities);
                
                population.getIndividuals().add(individual);                
            }
        }
    }
    
    public static class Greedy extends PriorityInitializationTechnique {
        @Override
        public void initialize(Population population, int popSize) {            
            for (int i = 0; i < popSize; i++) {
                PriorityIndividual priorityIndividual = new PriorityIndividual(population);
                Graph graph = priorityIndividual.getParentPopulation().getParentNetwork();
                ArrayList<Node> nodes = graph.getNodes();
                ArrayList<Node> generated = new ArrayList<Node>();
                
                Node firstGene = nodes.get(RandomUtil.from0(nodes.size()));
                generated.add(firstGene);
                
                Node previousGene = firstGene;
                ArrayList<Arc> neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(generated);
                do {
                    Arc winner = null;
                    
                    for (Arc neighbour : neighbours) {
                        if (winner == null) winner = neighbour;
                        
                        if (neighbour.getWeight() > winner.getWeight()) winner = neighbour;
                    }
                    
                    Node connectingNode = winner.getNodeConnecting(previousGene);
                    generated.add(connectingNode);
                    previousGene = connectingNode;
                    
                    neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(generated);
                } while (neighbours.size() > 0);
                
                //System.out.println("generated " + individual);
                
                priorityIndividual.setGenes(graph.createPrioritiesFromTraversalPath(generated));
                population.getIndividuals().add(priorityIndividual);
            }
        }
    }
    
    public static class GreedyRoulette extends PriorityInitializationTechnique {
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
                PriorityIndividual priorityIndividual = new PriorityIndividual(population);
                Graph graph = priorityIndividual.getParentPopulation().getParentNetwork();
                ArrayList<Node> nodes = graph.getNodes();
                ArrayList<Node> generated = new ArrayList<Node>();
                
                Node firstGene = nodes.get(RandomUtil.from0(nodes.size()));
                generated.add(firstGene);
                
                Node previousGene = firstGene;
                ArrayList<Arc> neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(generated);
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
                    generated.add(connectingNode);
                    previousGene = connectingNode;
                    
                    neighbours = previousGene.getNeighbouringArcsWhichDontConnectGivenNodes(generated);

                } while (neighbours.size() > 0);
                
                //System.out.println("generated " + individual);
                
                priorityIndividual.setGenes(graph.createPrioritiesFromTraversalPath(generated));
                population.getIndividuals().add(priorityIndividual);
            }
        }
    }
}
