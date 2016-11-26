/*
* Simple-ADB tool , a tool made to make the process of using adb/fastboot simpler, with GUI
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

import javax.swing.JTextArea;

import mhashem6.commander.Appender;

@SuppressWarnings("serial")
class OutputArea extends JTextArea implements Appender {

	public OutputArea() {
		setBackground(Color.decode("#1C2021"));
		setForeground(Color.WHITE);
		setLineWrap(true);
		setEditable(false);
		setCaretColor(Color.white);

	}

	@Override
	public void appendLine(String line) {
		append(line);
		line = null;
		setCaretPosition(getDocument().getLength());
	}

}
