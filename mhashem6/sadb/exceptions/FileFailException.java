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

package mhashem6.sadb.exceptions;

import java.io.IOException;

@SuppressWarnings("serial")
public class FileFailException extends IOException {
	private String fileName;

	public FileFailException(String fileName) {
		this.fileName = fileName;
	}

	public String toString() {
		return "could not cerate : " + fileName + " file";

	}

}
