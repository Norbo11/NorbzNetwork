package org.github.norbo11.norbznetwork.frames;

import java.awt.Color;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.github.norbo11.norbznetwork.listeners.MenuBarListener;
import org.github.norbo11.norbznetwork.listeners.NetworkMouseListener;
import org.github.norbo11.norbznetwork.main.NetworkPanel;
import org.github.norbo11.norbznetwork.main.TabArcs;
import org.github.norbo11.norbznetwork.main.TabNodes;
import org.github.norbo11.norbznetwork.util.CustomOutputStream;

public class Main {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    private static JFrame frame;
    private static NetworkPanel networkPanel;
    private static JTabbedPane tabbedPane;
    private static TabArcs arcsTab;
    private static TabNodes nodesTab;
    private static JMenuItem itemClearAll;
    private static JMenuItem itemDijkstras;
    private static JFrame algorithmFrame;
    private static JTextArea textArea;
    
    
    public static JTextArea getTextPane() {
        return textArea;
    }

    public static void setTextArea(JTextArea textArea) {
        Main.textArea = textArea;
    }

    public static JFrame getAlgorithmFrame() {
        return algorithmFrame;
    }

    public static void setAlgorithmFrame(JFrame algorithmFrame) {
        Main.algorithmFrame = algorithmFrame;
    }

    public static JMenuItem getItemDijkstras() {
        return itemDijkstras;
    }

    public static void setItemDijkstras(JMenuItem itemDijkstras) {
        Main.itemDijkstras = itemDijkstras;
    }

    public static JMenuItem getItemClearAll() {
        return itemClearAll;
    }

    public static void setItemClearAll(JMenuItem itemClearAll) {
        Main.itemClearAll = itemClearAll;
    }

    public static JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public static TabArcs getArcsTab() {
        return arcsTab;
    }

    public static TabNodes getNodesTab() {
        return nodesTab;
    }

    public static void setTabbedPane(JTabbedPane tabbedPane) {
        Main.tabbedPane = tabbedPane;
    }

    public static void setArcsTab(TabArcs arcsTab) {
        Main.arcsTab = arcsTab;
    }

    public static void setNodesTab(TabNodes nodesTab) {
        Main.nodesTab = nodesTab;
    }

    public static void main(String[] args) {
        initialize();
        frame.setVisible(true);
    }
    
    public static JFrame getFrame() {
        return frame;
    }
    
    public static void setFrame(JFrame frame) {
        Main.frame = frame;
    }

    public static NetworkPanel getNetworkPanel() {
        return Main.networkPanel;
    }
  
    public static void setNetworkPanel(NetworkPanel networkPanel) {
        Main.networkPanel = networkPanel;
    }

    private static void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 100 + WINDOW_WIDTH, 100 + WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("Norbert's Networks");
        
        JMenuBar menuBar = new JMenuBar();
        MenuBarListener menuBarListener = new MenuBarListener();
        frame.setJMenuBar(menuBar);

        JMenu menuTools = new JMenu("Tools");
        itemClearAll = new JMenuItem("Clear All");
        itemClearAll.addActionListener(menuBarListener);
        menuTools.add(itemClearAll);
        menuBar.add(menuTools);
        
        JMenu menuAlgorithms = new JMenu("Algorithms");
        itemDijkstras = new JMenuItem("Dijkstra's Shortest Route");
        itemDijkstras.addActionListener(menuBarListener);
        menuAlgorithms.add(itemDijkstras);
        menuBar.add(menuAlgorithms);

        JMenu menuHelp = new JMenu("Help");
        menuBar.add(menuHelp);
        
        networkPanel = new NetworkPanel();
        networkPanel.setLayout(null);
        networkPanel.setBounds(10, 11, 1106, 539);
        networkPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        NetworkMouseListener mouseListener = new NetworkMouseListener();
        networkPanel.addMouseListener(mouseListener);
        networkPanel.addMouseMotionListener(mouseListener);
        frame.getContentPane().add(networkPanel);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(1129, 11, 225, 539);
        arcsTab = new TabArcs();
        nodesTab = new TabNodes();
        tabbedPane.addTab("Arcs", arcsTab);
        tabbedPane.addTab("Nodes", nodesTab);
        frame.getContentPane().add(tabbedPane);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 561, 1344, 190);
        frame.getContentPane().add(scrollPane);
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);
        
        //Redirect standard output to text pane
        PrintStream output = new PrintStream(new CustomOutputStream());
        System.setOut(output);
    }

    public static void setAlgorithmFrame(DijkstrasFrame algorithmFrame) {
        Main.algorithmFrame = algorithmFrame;
    }
}
