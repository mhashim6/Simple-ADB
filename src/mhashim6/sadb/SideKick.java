package mhashim6.sadb;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * @author mhashim6
 */
public final class SideKick {

    public final static String HELP_URL = "http://forum.xda-developers.com/android/software/revive-simple-adb-tool-t3417155";
    public final static String SOURCE_URL = "https://github.com/mhashim6/Simple-ADB";
    public final static String COMMAND_EXECUTOR_URL = "https://github.com/mhashim6/System-Command-Executor";
    public final static String DONATE_URL = "https://paypal.me/mhashim6";
    public final static String EMAIL = "m6hashim@gmail.com";
    public final static String NEW_LINE = System.getProperty("line.separator");

    public final static String ABOUT_STRING = "Simple-ADB program." + NEW_LINE
            + "developed to make the process of using ADB/Fastboot simpler, with a Graphical User Interface." + NEW_LINE
            + "This program is based on Commander library : " + COMMAND_EXECUTOR_URL + NEW_LINE + NEW_LINE
            + "* Copyright (C) 2017 mhashim6 > (Muhammad Hashim)." + NEW_LINE + NEW_LINE
            + "This program is free software: you can redistribute it and/or modify it under" + NEW_LINE
            + "the terms of the GNU General Public License as published by the Free Software" + NEW_LINE
            + "Foundation, either version 3 of the License, or (at your option) any later version." + NEW_LINE
            + "This program is distributed in the hope that it will be useful, but WITHOUT" + NEW_LINE
            + "ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS" + NEW_LINE
            + "FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details." + NEW_LINE
            + "You should have received a copy of the GNU General Public License along with this program," + NEW_LINE
            + "If not, see <http://www.gnu.org/licenses/>." + NEW_LINE + "you can contact me: " + EMAIL;
    // ============================================================

    /**
     * to visit a URL address
     */
    public static void visitAddress(String adress) {

        try {
            URI uri = new URI(adress);
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }

            if (desktop != null) desktop.browse(uri);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (URISyntaxException use) {
            use.printStackTrace();
        }

    }
    // ============================================================

    public static void showMessage(JFrame frame, String text, String title, int type) {

        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, text, title, type));
    }
    // ============================================================

}
