package org.github.norbo11.norbznetwork.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.github.norbo11.norbznetwork.frames.GASettingsPanel;
import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.util.ConfigUtil;
import org.github.norbo11.norbznetwork.util.GUIUtil;

public class WindowListener extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent evt) {
        ConfigUtil.set("lastGraph", Main.getCurrentNetwork().getFilename());
        
        ConfigUtil.set("selectionTechnique", GUIUtil.getSelectedRadio(GASettingsPanel.getSelectionGroup()));
        ConfigUtil.set("crossoverTechnique", GUIUtil.getSelectedRadio(GASettingsPanel.getCrossoverGroup()));
        ConfigUtil.set("mutationTechnique", GUIUtil.getSelectedRadio(GASettingsPanel.getMutationGroup()));
        ConfigUtil.set("initializationTechnique", GUIUtil.getSelectedRadio(GASettingsPanel.getInitializationGroup()));
        ConfigUtil.set("encodingTechnique", GUIUtil.getSelectedRadio(GASettingsPanel.getEncodingGroup()));
        
        ConfigUtil.set("simulationSpeed", GASettingsPanel.getSimulationSpeed().getValue() + "");
        ConfigUtil.set("elitismOffset", GASettingsPanel.getSelectionElitismOffsetField().getText());
        ConfigUtil.set("mutationChance", GASettingsPanel.getMutationChanceField().getText());
        ConfigUtil.set("randomSeed", GASettingsPanel.getGeneralRandomSeed().getText());
        ConfigUtil.set("populationSize", GASettingsPanel.getGeneralPopulationSize().getText());
        ConfigUtil.set("mutateElite", GASettingsPanel.getMutationMutateElite().isSelected() + "");
        ConfigUtil.set("tournamentSize", GASettingsPanel.getSelectionTournamentSize().getText());

        ConfigUtil.save();
    }
}
