package mhashim6.sadb.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * @author mhashim6
 */
@SuppressWarnings({"serial", "rawtypes"})
class CommandsBox extends JComboBox {

    private DefaultComboBoxModel cbm;

    final static String[] ADB_ITEMS = {"devices -l", "usb", "connect", "disconnect", "logcat", "shell top", "shell free",
            "shell ps", "shell netstat", "shell pm list packages", "install", "uninstall", "push", "pull", "kill-all",
            "busybox df -h", "rm /data/system/gesture.key", "reboot", "reboot recovery", "reboot download",
            "reboot bootloader", "reboot fastboot", "reboot sideload", "root", "unroot", "kill-server", "help"};

    final static String[] FASTBOOT_ITEMS = {"devices -l", "reboot-bootloader", "erase system", "erase data",
            "erase cache", "flash", "flash recovery", "flash boot", "flash system", "flash data", "flash cache",
            "flash userdata", "flash bootloader", "flash radio", "help"};

    @SuppressWarnings("unchecked")
    CommandsBox() {

        super(ADB_ITEMS);
        setFocusable(false);
        clearSelectedElement();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        setPreferredSize(new Dimension(292, getPreferredSize().height));
        setBackground(Color.WHITE);
        setForeground(Color.decode("#263238"));
        setEditable(false);

        if (getSelectedItem() == null)
            g2.drawString("Commands List", 5, getHeight() / 2 + g2.getFontMetrics().getAscent() / 2);
    }

    @SuppressWarnings("unchecked")
    public void setComboElements(String[] combos) {
        removeAllItems();
        cbm = new DefaultComboBoxModel(combos);
        setModel(cbm);
        clearSelectedElement();

    }

    void clearSelectedElement() {

        try {
            setSelectedItem(null);
        } catch (NullPointerException ex) {

        }
    }

}
