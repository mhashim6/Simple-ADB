package mhashim6.sadb.ui;

import java.awt.Color;

import javax.swing.JTextArea;

import mhashim6.commander.main.Appender;
import mhashim6.sadb.SideKick;

/**
 * @author mhashim6
 */
@SuppressWarnings("serial")
class OutputArea extends JTextArea implements Appender {

    private StringBuilder buffer;
    private int appendsCount;
    private final static int APPENDS_LIMIT = 20;

    private static final String SEPARATOR = "===================" + SideKick.NEW_LINE + SideKick.NEW_LINE;

    public OutputArea() {
        setBackground(Color.decode("#1C2021"));
        setForeground(Color.WHITE);
        setLineWrap(true);
        setEditable(false);
        // setWrapStyleWord(true);

        buffer = new StringBuilder();
    }

    @Override
    public void appendStdText(String stdText) {
        appendText(stdText);
    }

    @Override
    public void appendErrText(String errText) {
        appendText(errText);
    }

    private synchronized void appendText(String text) {
        appendsCount++;
        buffer.append(text).append(SideKick.NEW_LINE);

        if (appendsCount >= APPENDS_LIMIT) {
            append(buffer.toString());
            setCaretPosition(getDocument().getLength());
            resetAppendsCount();
        }
    }

    public final void flush() {
        if (buffer.length() != 0) {
            append(buffer.toString());
            setCaretPosition(getDocument().getLength());
            resetAppendsCount();
        }
    }

    private void resetAppendsCount() {
        appendsCount = 0;
        buffer.setLength(0);
    }

    public final void addSeparator() {
        append(SEPARATOR);
    }

}
