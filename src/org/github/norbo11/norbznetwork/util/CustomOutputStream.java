package org.github.norbo11.norbznetwork.util;

import java.io.IOException;
import java.io.OutputStream;

import org.github.norbo11.norbznetwork.frames.Main;

public class CustomOutputStream extends OutputStream {

    @Override
    public void write(int b) throws IOException {
        Main.getTextPane().append((char) b + "");
        Main.getTextPane().setCaretPosition(Main.getTextPane().getDocument().getLength());
    }

}
