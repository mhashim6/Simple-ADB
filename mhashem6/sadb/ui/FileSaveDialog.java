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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import mhashem6.sadb.exceptions.FileFailException;

@SuppressWarnings("serial")
class FileSaveDialog extends JFileChooser {

	public FileSaveDialog(MainUI frame, String whatToSave) throws FileFailException {

		setDialogTitle("Save AS");
		setFileFilter(new FileNameExtensionFilter(".log, .txt", "log", "txt"));

		int userSelection = showSaveDialog(frame);

		if (userSelection == JFileChooser.APPROVE_OPTION) {

			File file = getSelectedFile();

			try {
				file.createNewFile();
			} catch (IOException e1) {
				throw new FileFailException(file.getName());
			}

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				bw.write(whatToSave);
			} catch (IOException e) {
				throw new FileFailException(file.getName());

			}

		}

	}

}
