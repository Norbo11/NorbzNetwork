package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import java.util.ArrayList;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;

public abstract class CrossoverTechnique {
    public abstract ArrayList<Individual> crossover(Individual a, Individual b);

    public static Individual getSmallerIndividual(Individual a, Individual b) {
        if (a.getSize() > b.getSize()) return b;
        else return a;
     }
    
    public static Individual getBiggerIndividual(Individual a, Individual b) {
        if (a.getSize() > b.getSize()) return a;
        else return b;
     }
}
