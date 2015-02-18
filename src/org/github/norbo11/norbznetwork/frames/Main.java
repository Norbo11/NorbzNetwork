package org.github.norbo11.norbznetwork.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import org.github.norbo11.norbznetwork.listeners.MenuBarListener;
import org.github.norbo11.norbznetwork.listeners.NetworkMouseListener;
import org.github.norbo11.norbznetwork.listeners.WindowListener;
import org.github.norbo11.norbznetwork.main.NetworkPanel;
import org.github.norbo11.norbznetwork.main.TabArcs;
import org.github.norbo11.norbznetwork.main.TabNodes;
import org.github.norbo11.norbznetwork.network.Network;
import org.github.norbo11.norbznetwork.util.ConfigUtil;
import org.github.norbo11.norbznetwork.util.GraphUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    private static JFrame frame;
    private static NetworkPanel networkPanel;
    private static JTabbedPane tabbedPane;
    private static TabArcs arcsTab;
    private static TabNodes nodesTab;
    private static JMenuItem itemClearAll, itemDijkstras, itemPrims, itemLongest, itemSaveGraph, itemLoadGraph;
    private static JFrame algorithmFrame;
    private static JFrame editDistanceFrame;
    private static JTextArea textArea;
    private static JFreeChart chart;
    private static XYSeries averageFitnessData, bestIndividualData;
    private static Network currentNetwork;
    
    public static JMenuItem getItemSaveGraph() {
        return itemSaveGraph;
    }

    public static JMenuItem getItemLoadGraph() {
        return itemLoadGraph;
    }

    public static XYSeries getBestIndividualData() {
        return bestIndividualData;
    }
    
    public static JTextArea getTextArea() {
        return textArea;
    }
    
    public static JMenuItem getItemLongest() {
        return itemLongest;
    }

    public static JFrame getEditDistanceFrame() {
        return editDistanceFrame;
    }

    public static void setEditDistanceFrame(JFrame editDistanceFrame) {
        Main.editDistanceFrame = editDistanceFrame;
    }

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
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } Uncomment for windows look and feel */
        
        /* Main window */
        
        frame = new JFrame();
        frame.setBounds(100, 100, 100 + WINDOW_WIDTH, 100 + WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setTitle("Norbert's Genetic Algorithms");
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH); //Maximise
        frame.addWindowListener(new WindowListener());
        
        /* Menu Bar */
        
        JMenuBar menuBar = new JMenuBar();
        MenuBarListener menuBarListener = new MenuBarListener();
        frame.setJMenuBar(menuBar);

        JMenu menuFile = new JMenu("File");
        itemLoadGraph = new JMenuItem("Load graph");
        itemLoadGraph.addActionListener(menuBarListener);
        menuFile.add(itemLoadGraph);
        
        itemSaveGraph = new JMenuItem("Save graph");
        itemSaveGraph.addActionListener(menuBarListener);
        menuFile.add(itemSaveGraph);
        menuBar.add(menuFile);
        
        JMenu menuTools = new JMenu("Tools");
        itemClearAll = new JMenuItem("Clear All");
        itemClearAll.addActionListener(menuBarListener);
        menuTools.add(itemClearAll);
        menuBar.add(menuTools);
        
        JMenu menuAlgorithms = new JMenu("Algorithms");
        itemDijkstras = new JMenuItem("Dijkstra's Shortest Route");
        itemDijkstras.addActionListener(menuBarListener);
        menuAlgorithms.add(itemDijkstras);
        
        itemPrims = new JMenuItem("Prim's Minimal Spanning Tree");
        itemPrims.addActionListener(menuBarListener);
        menuAlgorithms.add(itemPrims);
        
        itemLongest = new JMenuItem("Longest Path GA");
        itemLongest.addActionListener(menuBarListener);
        menuAlgorithms.add(itemLongest);        
        menuBar.add(menuAlgorithms);
        
        /* Network panel */

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Graph"));
        
        networkPanel = new NetworkPanel();
        NetworkMouseListener mouseListener = new NetworkMouseListener();

        networkPanel.setPreferredSize(new Dimension(800, 0));
        networkPanel.addMouseListener(mouseListener);
        networkPanel.addMouseMotionListener(mouseListener);
        leftPanel.add(networkPanel, BorderLayout.LINE_START);
        
        tabbedPane = new JTabbedPane();  
        tabbedPane.setPreferredSize(new Dimension(160, 0));
        arcsTab = new TabArcs();
        nodesTab = new TabNodes();
        tabbedPane.addTab("Arcs", arcsTab);
        tabbedPane.addTab("Nodes", nodesTab);
        leftPanel.add(tabbedPane, BorderLayout.LINE_END);
        
        frame.getContentPane().add(leftPanel, BorderLayout.LINE_START);

        /* GA controls panel */
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("GA Control Panel"));
        rightPanel.add(createChart());
        
        /* GA Settings */
        
        rightPanel.add(GASettingsPanel.getPanel());
        
        frame.getContentPane().add(rightPanel, BorderLayout.LINE_END);
        
        /* Info box on the button */   
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(0, 100));

        frame.getContentPane().add(scrollPane, BorderLayout.PAGE_END);
        
        ConfigUtil.load();
        GraphUtil.loadLastGraph();
        GASettingsPanel.restoreSettings();
        
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

    public static void setAlgorithmFrame(DijkstrasFrame algorithmFrame) {
        Main.algorithmFrame = algorithmFrame;
    }

    public static void writeText(String text) {
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getText().length());
    }
    
    public static JMenuItem getItemPrims()
    {
        return itemPrims;
    }

    public static void setItemPrims(JMenuItem itemPrims)
    {
        Main.itemPrims = itemPrims;
    }
    
    public static JFreeChart getChart() {
        return chart;
    }
    
    public static ChartPanel createChart() {
        averageFitnessData = new XYSeries("Average fitness");
        bestIndividualData = new XYSeries("Best individual");
        
        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(bestIndividualData);
        collection.addSeries(averageFitnessData);
        
        chart = ChartFactory.createXYLineChart("Fitness data over time", "Generation", "Fitness", collection, PlotOrientation.VERTICAL, true, false, false);
        chart.setBackgroundPaint(frame.getBackground());
        
        return new ChartPanel(chart);
    }

    public static XYSeries getAverageFitnessData() {
        return averageFitnessData;
    }

    public static Network getCurrentNetwork() {
        return currentNetwork;
    }
    
    public static void setCurrentNetwork(Network currentNetwork) {
        Main.currentNetwork = currentNetwork;
    }
}
