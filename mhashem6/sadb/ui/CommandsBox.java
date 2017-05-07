/*
* Simple-tool , a tool made to make the process of using adb/simpler, with GUI
* Copyright (C) 2016 mhashem6 > (Muhammad Hashim)
* 
* This program is free software: you can redistribute it and/or modify it under
* the terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* 
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
* details.
* 
* You should have received a copy of the GNU General Public License along with
* this program. If not, see <http://www.gnu.org/licenses/>.
* 
* for additional informations you can visit the main thread of this program > http://forum.xda-developers.com/android/software/revive-simple-adb-tool-t3417155
* you can contact me @ abohashem.com@gmail.com
* Source : https://sourceforge.net/p/sadb/
*
*/

package mhashem6.sadb.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

@SuppressWarnings({ "serial", "rawtypes" })
class CommandsBox extends JComboBox {

	private DefaultComboBoxModel cbm;

	final static String[] ADB_ITEMS = { "devices -l", "usb", "connect", "disconnect", "logcat", "shell top", "shell free",
			"shell ps", "shell ip", "shell netsat", "shell pm list packages", "install", "uninstall", "push", "pull",
			"kill-all", "busybox df -h", "rm /data/system/gesture.key", "reboot", "reboot recovery", "reboot download",
			"reboot bootloader", "reboot fastboot", "reboot sideload", "root", "unroot", "help", "kill-server" };

	final static String[] FASTBOOT_ITEMS = { "devices -l", "reboot-bootloader", "erase system", "erase data",
			"erase cache", "flash", "flash recovery", "flash boot", "flash system", "flash data", "flash cache",
			"flash userdata", "flash bootloader", "flash radio", "help" };

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
