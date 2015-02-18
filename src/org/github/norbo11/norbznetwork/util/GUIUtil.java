package org.github.norbo11.norbznetwork.util;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;


public class GUIUtil {

    public static boolean IsIntersecting(Point a, Point b, Point p, int minDist)
    {
        return (new Line2D.Double(a, b)).ptSegDist(p) <= minDist;
    }

    public static String getSelectedRadio(ButtonGroup group) {
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    public static void setRadioSelected(ButtonGroup group, String string) {
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.getText().equals(string)) {
                button.setSelected(true);
            }
        }
    }
}
