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
* this program is based on Commander library : https://github.com/mhashim6/Commander
*
*/

package mhashem6.sadb;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/** class that shall contain some helpful constants and methods */
public final class SideKick {

	public final static String HELP_URL = "http://forum.xda-developers.com/android/software/revive-simple-adb-tool-t3417155";
	public final static String SOURCE_URL = "https://github.com/mhashim6/Simple-ADB";
	public final static String DONATE_URL = "http://forum.xda-developers.com/donatetome.php?u=5157399";

	public final static String ABOUT_STRING = "Simple-ADB program," + System.getProperty("line.separator")
			+ "a program made to make the process of using adb/fastboot simpler, with GUI."
			+ System.getProperty("line.separator") 
			+ "This program is based on Commander library : https://github.com/mhashim6/Commander"
			+ System.getProperty("line.separator") + System.getProperty("line.separator")
			+ "* Copyright (C) 2016 mhashem6 > (Muhammad Hashim)."
			+ System.getProperty("line.separator") + System.getProperty("line.separator")
			+ "This program is free software: you can redistribute it and/or modify it under"
			+ System.getProperty("line.separator")
			+ "the terms of the GNU General Public License as published by the Free Software"
			+ System.getProperty("line.separator")
			+ "Foundation, either version 3 of the License, or (at your option) any later version."
			+ System.getProperty("line.separator")
			+ "This program is distributed in the hope that it will be useful, but WITHOUT"
			+ System.getProperty("line.separator")
			+ "ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS"
			+ System.getProperty("line.separator")
			+ "FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details."
			+ System.getProperty("line.separator")
			+ "You should have received a copy of the GNU General Public License along with this program,"
			+ System.getProperty("line.separator") + "If not, see <http://www.gnu.org/licenses/>."
			+ System.getProperty("line.separator") + "you can contact me @ abohashem.com@gmail.com";
	// ============================================================

	/** to visit a URL address */
	public static void visitAddress(String adress) {

		try {
			URI uri = new URI(adress);
			Desktop desktop = null;
			if (Desktop.isDesktopSupported()) {
				desktop = Desktop.getDesktop();
			}

			if (desktop != null)
				desktop.browse(uri);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (URISyntaxException use) {
			use.printStackTrace();
		}

	}
	// ============================================================

	public static void showMessage(JFrame frame, String text, String title, int type) {

		JOptionPane.showMessageDialog(frame, text, title, type);
	}
	// ============================================================

}
