package org.github.norbo11.norbznetwork.util;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.github.norbo11.norbznetwork.frames.Main;
import org.github.norbo11.norbznetwork.network.Arc;
import org.github.norbo11.norbznetwork.network.Graph;
import org.github.norbo11.norbznetwork.network.Node;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class GraphUtil {
    
    
    public static void loadAndSetGraph(String filename) {
        loadAndSetGraph(new File("networks/" + filename));
    }
    
    public static void loadAndSetGraph(File file) {
        Main.setCurrentNetwork(loadGraph(file));
        
        Main.getArcsTab().updateArcs();
        Main.getNodesTab().updateNodes();
    }
    
    public static void loadLastGraph() {        
        String lastGraph = ConfigUtil.get("lastGraph");
        
        if (lastGraph == null || lastGraph.equals("")) {
            Main.setCurrentNetwork(new Graph(""));
        } else loadAndSetGraph(lastGraph);
    }
    
    private static Graph loadGraph(File file) {      
        Graph graph = new Graph(file.getName());
        
        SAXBuilder builder = new SAXBuilder();
        
        Document doc = null;
        try {
            doc = builder.build(file);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        
        Element root = doc.getRootElement();
        
        for (Element node : root.getChild("nodes").getChildren()) {                
            int x = Integer.valueOf(node.getAttributeValue("x"));
            int y = Integer.valueOf(node.getAttributeValue("y"));
            graph.getNodes().add(new Node(graph.getNextId(), new Point(x, y), graph));
        }
        
        for (Element arc : root.getChild("arcs").getChildren()) {
            String from = arc.getAttributeValue("from");
            String to = arc.getAttributeValue("to");
            double weight = Double.valueOf(arc.getAttributeValue("weight"));
            graph.getArcs().add(new Arc(graph.getNodeById(from), graph.getNodeById(to), weight));
        }
                
        return graph;
    }

    public static void saveGraph(File file, Graph graph) {              
        Element root = new Element("network");        
        Element nodes = new Element("nodes");
        
        for (Node node : graph.getNodes()) {
            Element element = new Element(node.getId());
            
            element.setAttribute("x", ((int) node.getPoint().getX()) + "");
            element.setAttribute("y", ((int) node.getPoint().getY()) + "");
            
            nodes.addContent(element);
        }
        
        root.addContent(nodes);
        
        Element arcs = new Element("arcs");
        
        for (Arc arc : graph.getArcs()) {
            Element element = new Element("arc");
            
            element.setAttribute("from", arc.getStartNode().getId());
            element.setAttribute("to",  arc.getEndNode().getId());
            element.setAttribute("weight",  arc.getWeight() + "");
            
            arcs.addContent(element);
        }
        
        root.addContent(arcs);
        
        Document doc = new Document(root);
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        
        try {
            xmlOutput.output(doc, new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
