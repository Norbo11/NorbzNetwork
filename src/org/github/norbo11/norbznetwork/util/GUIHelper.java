package org.github.norbo11.norbznetwork.util;

import java.awt.Point;
import java.awt.geom.Line2D;

import javax.swing.JTextArea;

import org.github.norbo11.norbznetwork.frames.Main;

public class GUIHelper {

    public static boolean IsIntersecting(Point a, Point b, Point p, int minDist)
    {
        return (new Line2D.Double(a, b)).ptSegDist(p) <= minDist;
    }

    public static void displayMessage(String string)
    {
        JTextArea textPane = Main.getTextPane();
        textPane.append(string + "\n");
        textPane.setCaretPosition(textPane.getDocument().getLength());
    }

}
