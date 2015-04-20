package org.github.norbo11.norbznetwork.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import org.github.norbo11.norbznetwork.algorithms.Simulation;
import org.github.norbo11.norbznetwork.listeners.MenuBarListener;
import org.github.norbo11.norbznetwork.listeners.NetworkMouseListener;
import org.github.norbo11.norbznetwork.listeners.WindowListener;
import org.github.norbo11.norbznetwork.main.NetworkPanel;
import org.github.norbo11.norbznetwork.main.TabArcs;
import org.github.norbo11.norbznetwork.main.TabNodes;
import org.github.norbo11.norbznetwork.network.Graph;
import org.github.norbo11.norbznetwork.network.Node;
import org.github.norbo11.norbznetwork.util.ConfigUtil;
import org.github.norbo11.norbznetwork.util.GraphUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Main {

    public static JMenuItem getItemRandomizeWeights() {
        return itemRandomizeWeights;
    }

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    private static JFrame frame;
    private static NetworkPanel networkPanel;
    private static JTabbedPane tabbedPane;
    private static TabArcs arcsTab;
    private static TabNodes nodesTab;
    private static JMenuItem itemNewGraph, itemDijkstras, itemPrims, itemLongest, itemSaveGraph, itemLoadGraph, itemClearColors, itemConnectAllNodes, itemViewPopulation, itemRandomizeWeights;
    private static JFrame algorithmFrame;
    private static JFrame editDistanceFrame;
    private static JTextArea textArea;
    private static JFreeChart chart;
    private static XYSeries averageFitnessData, bestIndividualData;
    private static Graph currentNetwork;
    private static Simulation currentSimulation;
    
    public static JMenuItem getItemViewPopulation() {
        return itemViewPopulation;
    }

    public static JMenuItem getItemClearColors() {
        return itemClearColors;
    }

    public static Simulation getCurrentSimulation() {
        return currentSimulation;
    }

    public static void setCurrentSimulation(Simulation currentSimulation) {
        Main.currentSimulation = currentSimulation;
    }

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
    
    public static JMenuItem getItemConnectAllNodes() {
        return itemConnectAllNodes;
    }

    public static JMenuItem getItemNewGraph() {
        return itemNewGraph;
    }

    public static void setItemClearAll(JMenuItem itemClearAll) {
        Main.itemNewGraph = itemClearAll;
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

        /* Menu File */

        JMenu menuFile = new JMenu("File");
        
        itemNewGraph = new JMenuItem("New Graph");
        itemNewGraph.addActionListener(menuBarListener);
        menuFile.add(itemNewGraph);
        
        itemLoadGraph = new JMenuItem("Load graph");
        itemLoadGraph.addActionListener(menuBarListener);
        menuFile.add(itemLoadGraph);
        
        itemSaveGraph = new JMenuItem("Save graph");
        itemSaveGraph.addActionListener(menuBarListener);
        menuFile.add(itemSaveGraph);
        
        menuFile.addSeparator();
        
        itemClearColors = new JMenuItem("Clear color");
        itemClearColors.addActionListener(menuBarListener);
        menuFile.add(itemClearColors);
        
        menuBar.add(menuFile);
        
        /* Menu Tools */
        
        JMenu menuTools = new JMenu("Tools");
        
        itemConnectAllNodes = new JMenuItem("Connect all nodes");
        itemConnectAllNodes.addActionListener(menuBarListener);
        menuTools.add(itemConnectAllNodes);
        
        itemRandomizeWeights = new JMenuItem("Randomize all arc weights");
        itemRandomizeWeights.addActionListener(menuBarListener);
        menuTools.add(itemRandomizeWeights);
        
        itemViewPopulation = new JMenuItem("View population");
        itemViewPopulation.addActionListener(menuBarListener);
        menuTools.add(itemViewPopulation);
        
        menuBar.add(menuTools);
        
        /* Menu Algorithms */
        
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
        networkPanel.addMouseListener(mouseListener);
        networkPanel.addMouseMotionListener(mouseListener);
        networkPanel.setPreferredSize(new Dimension(9999, 9999));
        
        leftPanel.add(networkPanel, BorderLayout.LINE_START);
        
        /* Tabbed Pane */
        
        tabbedPane = new JTabbedPane();  
        tabbedPane.setPreferredSize(new Dimension(110, 0));
        
        arcsTab = new TabArcs();
        nodesTab = new TabNodes();
        
        tabbedPane.addTab("Arcs", arcsTab);
        tabbedPane.addTab("Nodes", nodesTab);

        /* GA Settings panel */
        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("GA Control Panel"));
        rightPanel.add(createChart());
        rightPanel.add(GASettingsPanel.getPanel());
        
        /* Info box on the button */   
        
        textArea = new JTextArea();
        textArea.setEditable(false);
        
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        /* Scroll panes */
        
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        textAreaScrollPane.setMinimumSize(new Dimension(0, 100));
        
        JScrollPane networkPanelScrollPane = new JScrollPane(leftPanel);
        networkPanelScrollPane.setMinimumSize(new Dimension(100, 100));
        
        JScrollPane rightScrollPane = new JScrollPane(rightPanel);
        rightScrollPane.setMinimumSize(new Dimension(100, 100));
        
        /* Split panes */
        
        JSplitPane horizontalSplit1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplit1.setResizeWeight(0.5d);
        
        JSplitPane horizontalSplit2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        horizontalSplit2.setResizeWeight(0.2d);
        
        JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        verticalSplit.setResizeWeight(1d);

        horizontalSplit2.setLeftComponent(tabbedPane);
        horizontalSplit2.setRightComponent(rightScrollPane);
        
        horizontalSplit1.setLeftComponent(networkPanelScrollPane);
        horizontalSplit1.setRightComponent(horizontalSplit2);
        
        verticalSplit.setTopComponent(horizontalSplit1);
        verticalSplit.setBottomComponent(textAreaScrollPane);

        frame.getContentPane().add(verticalSplit);
        
        ConfigUtil.load();
        GraphUtil.loadLastGraph();
        GASettingsPanel.restoreSettings();
        
        frame.setVisible(true);
                
        /* This must be done after the frame is made visible */
        horizontalSplit1.setDividerLocation(0.5d);
        horizontalSplit2.setDividerLocation(0.15d);
        verticalSplit.setDividerLocation(0.9d);
        
        ArrayList<Node> list = new ArrayList<Node>();
        ArrayList<Node> nodes = Main.getCurrentNetwork().getNodes();

        list.add(nodes.get(2));
        list.add(nodes.get(4));
        list.add(nodes.get(1));
        list.add(nodes.get(6));
        list.add(nodes.get(3));
        
        Main.getCurrentNetwork().createPrioritiesFromTraversalPath(new ArrayList<Node>(list));
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

    public static Graph getCurrentNetwork() {
        return currentNetwork;
    }
    
    public static void setCurrentNetwork(Graph currentNetwork) {
        Main.currentNetwork = currentNetwork;
    }
}
