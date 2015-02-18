package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import org.github.norbo11.norbznetwork.algorithms.ga.Population;


public abstract class GATechnique {
    private Population population;
    
    public GATechnique(Population population) {
        this.population = population;
    }
    
    public Population getPopulation() {
        return population;
    }
}
