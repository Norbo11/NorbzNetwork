package org.github.norbo11.norbznetwork.algorithms.ga.techniques;

import org.github.norbo11.norbznetwork.algorithms.ga.Individual;
import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.frames.GASettingsPanel;
import org.github.norbo11.norbznetwork.util.RandomUtil;

public abstract class SelectionTechnique  {
    public abstract Individual select(Population population);
    
    public static class RouletteWheel extends SelectionTechnique {

        @Override
        public Individual select(Population population) {            
            int random = RandomUtil.from0(population.calculateTotalFitness());
            int total = 0;
                        
            Individual selected = null;
            for (Individual simpleIndividual : population.getIndividuals()) {
                total += simpleIndividual.getFitness();
                                
                if (total >= random) { 
                    selected = simpleIndividual;
                    break;
                }
            }
            
            return selected;
        }
    }

    public static class Tournament extends SelectionTechnique {

        @Override
        public Individual select(Population population) {            
            Population tournament = new Population();

            for (int i = 0; i < GASettingsPanel.getTournamentSize(); i++) {
                int randomId = RandomUtil.from0(population.getSize());
                tournament.getIndividuals().add(population.getIndividuals().get(randomId));
            }
            
            
            return tournament.getBestIndividual();
        }
    }
}
