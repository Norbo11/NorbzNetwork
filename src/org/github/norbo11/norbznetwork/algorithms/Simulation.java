package org.github.norbo11.norbznetwork.algorithms;

import org.github.norbo11.norbznetwork.algorithms.ga.Population;
import org.github.norbo11.norbznetwork.frames.Main;

public abstract class Simulation {
    private boolean running = false;
    private Population population;
    private int generations;
    
    public void start() {
        if (Main.getCurrentSimulation() != null) Main.getCurrentSimulation().setRunning(false);
        Main.setCurrentSimulation(this);
        setRunning(true);
        
        new Thread() {
            @Override
            public void run() {
                perform();
            }
        }.start();
    }
    
    public abstract void perform();

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
