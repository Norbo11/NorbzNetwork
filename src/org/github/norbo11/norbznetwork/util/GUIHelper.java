package org.github.norbo11.norbznetwork.util;

import java.awt.Point;
import java.awt.geom.Line2D;


public class GUIHelper {

    public static boolean IsIntersecting(Point a, Point b, Point p, int minDist)
    {
        return (new Line2D.Double(a, b)).ptSegDist(p) <= minDist;
    }

}
